package com.washpost.games.rt.server.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.washpost.games.rt.common.User;
import com.washpost.games.rt.common.request.HeartBeat;
import com.washpost.games.rt.common.request.RosterRequest;
import com.washpost.games.rt.common.response.HeartBeatResponse;
import com.washpost.games.rt.common.UserHeartBeat;
import com.washpost.games.rt.common.response.RosterResponse;
import com.washpost.games.rt.server.OnlineRoster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shahb on 3/5/14.
 */
public class KryoServer {
    private static Server server = null;

    public static void main(String[] args) throws IOException {
        server = init();
    }

    public static Server init() throws IOException {
        server = new Server();
        server.start();
        server.bind(54555, 54777);
        registerTypes();
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof UserHeartBeat) {
                    UserHeartBeat request = (UserHeartBeat)object;
                    System.out.println(request.lastHeartbeat.text);

                    if(!OnlineRoster.isAlive(request.user))
                    {
                        OnlineRoster.enqueue(request.user);
                    }

                    HeartBeatResponse response = new HeartBeatResponse();
                    response.text = "Welcome";
                    connection.sendTCP(response);
                }
                if(object instanceof RosterRequest){
                    List<User> onlineUsers = OnlineRoster.getOnlineUsers();
                    RosterResponse response = new RosterResponse();
                    response.onlineOnes =  onlineUsers;
                    connection.sendTCP(response);
                }
            }
        });
        return server;
    }

    private static void registerTypes() {
        Kryo kryo = (Kryo) server.getKryo();
        kryo.register(HeartBeat.class);
        kryo.register(HeartBeatResponse.class);
        kryo.register(User.class);
        kryo.register(UserHeartBeat.class);
        kryo.register(Date.class);
        kryo.register(RosterRequest.class);
        kryo.register(RosterResponse.class);
        kryo.register(ArrayList.class);
    }

    public static void shutdown(){
        if(server!=null) {
            server.stop();
        }
    }
}
