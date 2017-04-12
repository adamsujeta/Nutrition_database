package com.example.adams.gymh;

import android.content.Context;
import android.provider.Settings;
import android.widget.Chronometer;

/**
 * Created by adams on 07.01.2017.
 */

public class MyChronometer implements Runnable {

    public static final long MILLIS_TO_MINUTES = 60000;


    private Context context;
    private long startTime;
    private boolean isOn;


    public MyChronometer(Context _context) {
        context = _context;
    }

    public void start() {
        isOn = true;
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        isOn = false;
    }

    @Override
    public void run() {

        while (isOn) {
            long since = System.currentTimeMillis() - startTime;

            int mill = (int) ((since) % 1000);
            int sec = (int) ((since / 1000) % 60);
            int min = (int) ((since / MILLIS_TO_MINUTES) % 60);
            ((MainActivity) context).stopper(String.format("%02d:%02d:%02d", min, sec, (mill / 10)));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        ((MainActivity) context).stopper("00:00:00");
    }
}
