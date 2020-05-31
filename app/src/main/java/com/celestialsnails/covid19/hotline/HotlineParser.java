package com.celestialsnails.covid19.hotline;

import android.content.Context;
import android.util.Log;

import com.celestialsnails.covid19.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class HotlineParser {

    private HashMap<String, String> suicideHotline;
    private HashMap<String, String> emergencyHotline;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void initialize() {
        suicideHotline = new HashMap<>();
        emergencyHotline = new HashMap<>();
        String message1 = "", message2 = "";
        InputStream countryFileForSuicidal, countryFileForEmergency, suicideHotlineFile, emergencyHotlineFile;
        countryFileForSuicidal = context.getResources().openRawResource(R.raw.countries);
        countryFileForEmergency = context.getResources().openRawResource(R.raw.countries);
        suicideHotlineFile = context.getResources().openRawResource(R.raw.suicidehotline);
        emergencyHotlineFile = context.getResources().openRawResource(R.raw.emergencyhotline);
        BufferedReader countryFileReaderForSuicidal = new BufferedReader(
                new InputStreamReader(countryFileForSuicidal));
        BufferedReader suicideHotlineFileReader =
                new BufferedReader(new InputStreamReader(suicideHotlineFile));
        while (true) {
            try {
                if ((message1 = countryFileReaderForSuicidal.readLine()) == null) break;
                if ((message2 = suicideHotlineFileReader.readLine()) == null) break;
            }
            catch (IOException exc) {
                Log.d(this.getClass().getName(), "initialize: " + exc.getMessage());
            }
            suicideHotline.put(message1, message2);
        }
        try {
            countryFileReaderForSuicidal.close();
            suicideHotlineFileReader.close();
        }
        catch (IOException exc) {
            Log.d(this.getClass().getName(), "initialize: " + exc.getMessage());
        }
        message1 = "";
        message2 = "";
        BufferedReader countryFileReaderForEmergency = new BufferedReader(
                new InputStreamReader(countryFileForEmergency));
        BufferedReader emergencyHotlineFileReader =
                new BufferedReader(new InputStreamReader(emergencyHotlineFile));
        while (true) {
            try {
                if ((message1 = countryFileReaderForEmergency.readLine()) == null) break;
                if ((message2 = emergencyHotlineFileReader.readLine()) == null) break;
            }
            catch (IOException exc) {
                Log.d(this.getClass().getName(), "initialize: " + exc.getMessage());
            }
            emergencyHotline.put(message1, message2);
        }
    }

    public String getSuicideHotline(String country) {
        return suicideHotline.get(country);
    }

    public String getEmergencyHotline(String country) {
        return emergencyHotline.get(country);
    }
}
