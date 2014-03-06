package com.washpost.games.rt.common;

import com.washpost.games.rt.common.User;
import com.washpost.games.rt.common.UserHeartBeat;

import java.util.*;

/**
 * Created by shahb on 3/5/14.
 */
public class Roster {
    protected Map<String,UserHeartBeat> onlineUsers = new HashMap<String, UserHeartBeat>();
    public  List<User> activeRoster = new ArrayList<User>();

    public boolean isAlive(User user) {
        return onlineUsers.containsKey(user.uuId) || onlineUsers.containsKey(user.deviceId);
    }

    public void enqueue(User u ){
        if(!onlineUsers.containsKey(u.uuId)){
            onlineUsers.put(u.uuId,new UserHeartBeat(u));
            activeRoster.add(u);
        }else if(!onlineUsers.containsKey(u.deviceId)){
            onlineUsers.put(u.deviceId,new UserHeartBeat(u));
        }
    }

    public List<User> getOnlineUsers(){
        //Iterator<UserHeartBeat> onlineIter = onlineUsers.values().iterator();
        return activeRoster;
    }
}
