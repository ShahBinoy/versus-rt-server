package com.washpost.games.rt.common;

import com.washpost.games.rt.common.request.HeartBeat;

/**
 * Created by shahb on 3/5/14.
 */
public class UserHeartBeat {
    public User user;

    public UserHeartBeat() {
    }

    public UserHeartBeat(User user) {
        this.user = user;
        lastHeartbeat = new HeartBeat();
    }

    public HeartBeat lastHeartbeat;
    @Override
    public String toString(){
        return user.uuId+":"+lastHeartbeat.latestTS.toString();
    }
}
