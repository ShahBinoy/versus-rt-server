package com.washpost.games.rt.common;

/**
 * Created by shahb on 3/5/14.
 */
public class User {
    public String deviceId;
    public String uuId;

    public User(String deviceId, String uuId) {
        this.deviceId = deviceId;
        this.uuId = uuId;
    }

    public User(String uuId) {
        this.uuId = uuId;
    }

    public User() {
    }
}
