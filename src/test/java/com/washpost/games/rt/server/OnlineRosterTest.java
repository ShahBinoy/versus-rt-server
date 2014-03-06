package com.washpost.games.rt.server;

import com.esotericsoftware.kryonet.Client;
import com.washpost.games.rt.common.User;
import com.washpost.games.rt.server.kryo.KryoClient;
import com.washpost.games.rt.server.kryo.KryoServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by shahb on 3/5/14.
 */

public class OnlineRosterTest {

    KryoServer server;

    @Before public void setup() throws IOException {
        server = new KryoServer();
    }
    @Test
    public void testUserEnqueue() throws InterruptedException {
        User aUser = null;
        KryoClient client1 = null;
        for(int i=0;i<20;i++){
        aUser= new User("player"+i);
            client1 = new KryoClient(aUser);
        }

        client1.requestRoster();

        try {
            client1.client.update(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(20,client1.currentRoster.onlineOnes.size());

    }
    @After
    public void destroy(){
        server.shutdown();
    }
}
