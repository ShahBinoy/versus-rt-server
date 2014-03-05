package com.washpost.games.rt.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.washpost.games.rt.common.User;
import com.washpost.games.rt.common.UserHeartBeat;
import com.washpost.games.rt.common.request.HeartBeat;
import com.washpost.games.rt.common.request.RosterRequest;
import com.washpost.games.rt.common.response.HeartBeatResponse;
import com.washpost.games.rt.common.response.RosterResponse;
import com.washpost.games.rt.server.kryo.KryoServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * Created by shahb on 3/5/14.
 */

public class OnlineRosterTest {
    Client client = null;
    @Before public void setup() throws IOException {


        client = new Client();
        client.start();
        Kryo kryo = (Kryo) client.getKryo();
        kryo.register(HeartBeat.class);
        kryo.register(HeartBeatResponse.class);
        kryo.register(User.class);
        kryo.register(UserHeartBeat.class);
        kryo.register(Date.class);
        kryo.register(RosterRequest.class);
        kryo.register(RosterResponse.class);
        kryo.register(ArrayList.class);
        client.connect(50000, "localhost", 54555, 54777);
    }
    @Test
    public void testUserEnqueue(){
        User testUser = null;
        UserHeartBeat uhb = null;

        testUser = new User();
        testUser.uuId="tBone";
        uhb = new UserHeartBeat(testUser);
        client.sendTCP(uhb);
        testUser = new User();
        testUser.uuId="zBone";
        uhb = new UserHeartBeat(testUser);
        client.sendTCP(uhb);
        testUser = new User();
        testUser.uuId="vBone";
        uhb = new UserHeartBeat(testUser);
        client.sendTCP(uhb);
        RosterRequest roster = new RosterRequest();
        roster.user = testUser;
        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof RosterResponse) {
                    RosterResponse response = (RosterResponse)object;
                    assertTrue(response.onlineOnes.size()>2);
                }
            }
        });
        client.sendTCP(roster);

    }
    @After
    public void destroy(){
        KryoServer.shutdown();
    }
}
