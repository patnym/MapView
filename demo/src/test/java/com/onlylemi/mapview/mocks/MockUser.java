package com.onlylemi.mapview.mocks;

import android.graphics.PointF;

import com.onlylemi.mapview.library.graphics.implementation.LocationUser;

/**
 * Created by patnym on 2018-04-15.
 */

public class MockUser extends LocationUser {

    public MockUser() {
        super();
    }

    public MockUser(PointF position) {
        this.position = position;
    }
}
