package com.washpost.games.rt.common.request;

import java.util.Date;

/**
 * Created by shahb on 3/5/14.
 */
public class HeartBeat {
    public boolean text;
    public Date latestTS;

    public HeartBeat() {
        latestTS = new Date();
    }
}
