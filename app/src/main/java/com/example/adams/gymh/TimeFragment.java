package com.example.adams.gymh;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeFragment extends Fragment {
    private Button btnStop, btnStart;
    private TextView TimeView;

    private MyChronometer mChronometer;
    private Thread mThread;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_time, container, false);
        btnStart = (Button) mView.findViewById(R.id.StartButton);
        btnStop = (Button) mView.findViewById(R.id.StopButton);
        TimeView = (TextView) mView.findViewById(R.id.tView);

        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mChronometer == null) {

                    mChronometer = new MyChronometer(getContext());
                    mThread = new Thread(mChronometer);
                    mThread.start();
                    mChronometer.start();
                    btnStart.setText("Restart");
                } else {
                    mChronometer.start();
                }
            }

        });
        btnStop.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {
                if (mChronometer != null) {

                    mChronometer.stop();
                    mChronometer = null;
                    mThread.interrupt();
                    mThread = null;

                    btnStart.setText("Start");
                }
            }

        });
        return mView;
    }

    public void setTextTimeView(String value) {
        TimeView.setText(value);
    }


}
