package com.celestialsnails.covid19.models;

/**
 * Created by aerozero on 12/23/17.
 */

public interface TrackDelegate {
    public void trackTimeRemainingUpdated(int timeRemaining);
    public void trackEnded();
}
