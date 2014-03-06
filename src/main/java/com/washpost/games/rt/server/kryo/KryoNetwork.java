package com.washpost.games.rt.server.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.washpost.games.rt.common.User;
import com.washpost.games.rt.common.UserHeartBeat;
import com.washpost.games.rt.common.request.EnrollmentRequest;
import com.washpost.games.rt.common.request.HeartBeat;
import com.washpost.games.rt.common.request.RosterRequest;
import com.washpost.games.rt.common.response.EnrollmentResponse;
import com.washpost.games.rt.common.response.HeartBeatResponse;
import com.washpost.games.rt.common.response.RosterResponse;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by shahb on 3/6/14.
 */
public class KryoNetwork {
    public static final int TCP_PORT = 54555;
    public static String HOST = "localhost";

    public static void registerTypes(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(HeartBeat.class);
        kryo.register(HeartBeatResponse.class);
        kryo.register(User.class);
        kryo.register(UserHeartBeat.class);
        kryo.register(Date.class);
        kryo.register(RosterRequest.class);
        kryo.register(RosterResponse.class);
        kryo.register(ArrayList.class);
        kryo.register(EnrollmentRequest.class);
        kryo.register(EnrollmentResponse.class);
    }
}
