package com.washpost.games.rt.server.kryo;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.washpost.games.rt.common.Roster;
import com.washpost.games.rt.common.User;
import com.washpost.games.rt.common.request.EnrollmentRequest;
import com.washpost.games.rt.common.request.RosterRequest;
import com.washpost.games.rt.common.response.EnrollmentResponse;
import com.washpost.games.rt.common.response.HeartBeatResponse;
import com.washpost.games.rt.common.UserHeartBeat;
import com.washpost.games.rt.common.response.RosterResponse;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by shahb on 3/5/14.
 */
public class KryoServer {
    private Server server = null;
    Roster activeRoster;


    public KryoServer() {
        activeRoster = new Roster();
        server = init();
    }

    public static void main(String[] args) throws IOException {
        KryoServer kryoServer = new KryoServer();
    }

    public Server init() {
        server = new Server();

        KryoNetwork.registerTypes(server);

        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                System.out.println("Got New Connection");

                if (object instanceof UserHeartBeat) {
                    UserHeartBeat request = (UserHeartBeat) object;
                    System.out.println("HeartBeat Requested for " + request.lastHeartbeat.text);

                    activeRoster.enqueue(request.user);
                    HeartBeatResponse response = new HeartBeatResponse();
                    response.text = "Welcome";
                    connection.sendTCP(response);
                }
                if (object instanceof EnrollmentRequest) {
                    EnrollmentRequest request = (EnrollmentRequest) object;
                    System.out.println("Enrollment Requested for " + request.user.uuId);

                    if (!activeRoster.isAlive(request.user)) {
                        activeRoster.enqueue(request.user);
                    }
                    EnrollmentResponse response = new EnrollmentResponse("Enrolled Successfully at " + new Date().toString());
                    connection.sendTCP(response);
                }
                if (object instanceof RosterRequest) {
                    RosterRequest request = (RosterRequest) object;
                    System.out.println("Roster Requested by " + request.user.uuId);
                    List<User> onlineUsers = activeRoster.getOnlineUsers();
                    RosterResponse response = new RosterResponse();
                    response.onlineOnes = onlineUsers;
                    connection.sendTCP(response);
                }
            }
        });
        try {
            server.bind(KryoNetwork.TCP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.start();
        return server;
    }

    public void shutdown() {
        if (server != null) {
            server.stop();
        }
    }
}
