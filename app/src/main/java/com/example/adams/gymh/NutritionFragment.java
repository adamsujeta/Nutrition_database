package com.example.adams.gymh;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.services.Response;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NutritionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NutritionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NutritionFragment extends Fragment {
    View view;

    FoodSearch fs;
    Button btnSearch;
    View linearLayout;
    TextView foodSerchText;
    List<CompactFood> compactFoodResponse;
    private  ProgressDialog progressBar;
    private int progressBarStatus = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_nutrition, container, false);
        fs = new FoodSearch();
        btnSearch = (Button) view.findViewById(R.id.foodSearch);
        foodSerchText = (TextView) view.findViewById(R.id.editTextFoodSearch);

        linearLayout = view.findViewById(R.id.mainLL);

        btnSearch.setOnClickListener(new View.OnClickListener(

                                     ) {
                                         @Override
                                         public void onClick(View v) {
                                             if (checkInternetConnection()) {
                                                 new getFoods().execute(foodSerchText.getText().toString());
                                             }
                                         }


                                     }

        );
        return view;
    }

    private class getFoods extends AsyncTask<String, Void, Void> {

        protected void onPreExecute() {
            progressBar = new ProgressDialog(view.getContext());
            progressBar.setCancelable(true);
            progressBar.setMessage("Searching ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();
            progressBarStatus = 0;
        }
        @Override
        protected Void doInBackground(String... params) {
                compactFoodResponse = fs.searchForFoods(params[0]);
            return null;
        }

        protected void onPostExecute(Void result) {
            if (((LinearLayout) linearLayout).getChildCount() > 0)
                ((LinearLayout) linearLayout).removeAllViews();



            if(compactFoodResponse.size()==0)
            {
                LinearLayout LL = new LinearLayout(getContext());
                LL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                LL.setMinimumHeight(80);
                TextView valueTV1 = new TextView(getContext());
                valueTV1.setText("Brak wyników");

                valueTV1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                valueTV1.setTextSize(20);
                LL.addView(valueTV1);
                ((LinearLayout) linearLayout).addView(LL);
            }else {
                for (final CompactFood cf : compactFoodResponse
                        ) {


                    //LinearLayout layout = (LinearLayout) findViewById(R.id.info);
                    LinearLayout LL = new LinearLayout(getContext());
                    LL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    LL.setMinimumHeight(80);
                    LL.setOrientation(LinearLayout.VERTICAL);

                    LL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (checkInternetConnection()) {
                                Intent myIntent = new Intent(getContext(), FoodDetailsActivity.class);
                                myIntent.putExtra("id", cf.getId());
                                startActivity(myIntent);
                            }

                        }
                    });

                    TextView valueTV = new TextView(getContext());
                    valueTV.setText(cf.getName());
                    valueTV.setTextColor(0xff000000);
                    valueTV.setId(cf.getId().intValue());
                    valueTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    valueTV.setTextSize(20);
                    valueTV.setTypeface(Typeface.DEFAULT_BOLD);
                    valueTV.setGravity(Gravity.LEFT| Gravity.CENTER);
                    LL.addView(valueTV);

                    TextView valueTV2 = new TextView(getContext());
                    valueTV2.setText(cf.getDescription());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.gravity = Gravity.RIGHT;

                    valueTV2.setLayoutParams(layoutParams);
                    valueTV2.setTextSize(14);
                    valueTV2.setPadding(20,10,20,20);
                    valueTV2.setTypeface(Typeface.DEFAULT_BOLD);
                    valueTV2.setGravity(Gravity.LEFT| Gravity.CENTER);
                    LL.addView(valueTV2);
                    ((LinearLayout) linearLayout).addView(LL);

                }
            }
            progressBar.dismiss();
        }
    }


    public boolean checkInternetConnection() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec
                = (ConnectivityManager) getContext().getSystemService(getActivity().getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            Toast.makeText(getContext(), " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED) {
            Toast.makeText(getContext(), " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

}

