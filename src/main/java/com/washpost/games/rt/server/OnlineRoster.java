package com.washpost.games.rt.server;

import com.washpost.games.rt.common.User;
import com.washpost.games.rt.common.UserHeartBeat;

import java.util.*;

/**
 * Created by shahb on 3/5/14.
 */
public class OnlineRoster {
    protected static Map<String,UserHeartBeat> onlineUsers = new HashMap<String, UserHeartBeat>();
    public static List<User> activeRoster = new ArrayList<User>();

    public static boolean isAlive(User user) {
        return onlineUsers.containsKey(user.uuId) || onlineUsers.containsKey(user.deviceId);
    }

    public static void enqueue(User u ){
        if(!onlineUsers.containsKey(u.uuId)){
            onlineUsers.put(u.uuId,new UserHeartBeat(u));
        }else if(!onlineUsers.containsKey(u.deviceId)){
            onlineUsers.put(u.deviceId,new UserHeartBeat(u));
        }
        activeRoster.add(u);
    }

    public static List<User> getOnlineUsers(){
        Iterator<UserHeartBeat> onlineIter = onlineUsers.values().iterator();
        while(onlineIter.hasNext()){
            System.out.println(onlineIter.next().toString());
        }

        return activeRoster;
    }
}
