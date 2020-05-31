package com.celestialsnails.covid19.managers;


import com.celestialsnails.covid19.R;
import com.celestialsnails.covid19.models.TrackTemplate;

/**
 * Created by aerozero on 12/22/17.
 */

public class TrackTemplateFactory {

    public static TrackTemplateFactory singleton = new TrackTemplateFactory();

    private TrackTemplate[] trackTemplates;

    private boolean requireMeditationsBeDoneInOrder = true;

    public TrackTemplateFactory() {

        trackTemplates = new TrackTemplate[2];

        trackTemplates[0] = new TrackTemplate( "Silent Meditation", "Silent Meditation", R.raw.ohm, 0);

        trackTemplates[1] = new TrackTemplate("Introduction", "Introduction", R.raw.runningwaters, 0);


    }

    public TrackTemplate getTrackTemplate(int index) {
        return trackTemplates[index];
    }
    public boolean getRequireMeditationsBeDoneInOrder() { return requireMeditationsBeDoneInOrder; }
    public int getTrackTemplateCount() { return trackTemplates.length; }
}
