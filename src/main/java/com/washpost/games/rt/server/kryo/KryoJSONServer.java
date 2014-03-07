package com.washpost.games.rt.server.kryo;

import com.esotericsoftware.kryonet.JsonSerialization;
import com.esotericsoftware.kryonet.Server;

/**
 * Created by shahb on 3/6/14.
 */
public class KryoJSONServer extends KryoServer {
    public KryoJSONServer(){
        super();
    }
    @Override
    public Server init(){
        server = new Server(16384, 8192, new JsonSerialization());
        KryoNetwork.registerTypes(server);
        server.addListener(buildListener());
        return  server;

    }
}
