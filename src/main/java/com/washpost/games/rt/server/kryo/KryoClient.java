package com.washpost.games.rt.server.kryo;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.washpost.games.rt.common.User;
import com.washpost.games.rt.common.UserHeartBeat;
import com.washpost.games.rt.common.request.RosterRequest;
import com.washpost.games.rt.common.response.RosterResponse;

import java.io.IOException;

/**
 * Created by shahb on 3/6/14.
 */
public class KryoClient extends Listener {
    public Client client;
    public RosterResponse currentRoster;
    public User           currentUser;
    public UserHeartBeat lastHeartBeat;

    public KryoClient(User newUsr) {
        client = new Client();
        client.start();
        KryoNetwork.registerTypes(client);
        client.addListener(new Listener(){
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof RosterResponse) {
                    System.out.println("Received Roster Response");
                    currentRoster = (RosterResponse)object;
                }
            }

            @Override
            public void connected(Connection connection) {
                System.out.println("Successfully Connected ");
            }

            @Override
            public void idle(Connection connection) {
                super.idle(connection);
            }
        });
        try {
            client.connect(5000, KryoNetwork.HOST, KryoNetwork.TCP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        enrollInRoster(newUsr,false);
    }

    public void requestRoster(){
        new Thread(){
            @Override
            public void run(){
            RosterRequest roster = new RosterRequest();
            roster.user = currentUser;
            client.sendTCP(roster);
        }
        }.start();
    }

    public void enrollInRoster(User currUser, boolean async){
        currentUser = currUser;
        lastHeartBeat = new UserHeartBeat(currentUser);
        client.sendTCP(lastHeartBeat);
    }


}
