package com.washpost.games.rt.common.request;

import com.washpost.games.rt.common.User;

/**
 * Created by shahb on 3/6/14.
 */
public class EnrollmentRequest {
    public User user;

    public EnrollmentRequest(User user) {
        this.user = user;
    }

    public EnrollmentRequest() {
    }
}
