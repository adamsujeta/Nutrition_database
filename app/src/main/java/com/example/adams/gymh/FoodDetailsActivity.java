package com.example.adams.gymh;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.Food;
import com.fatsecret.platform.model.Serving;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class FoodDetailsActivity extends AppCompatActivity {

    FoodSearch foodSearch;
    TextView textView;
    Food food;
    List<Serving> fServing;
    Spinner spinner;
    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    View view;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        Intent intent = getIntent();
        foodSearch = new FoodSearch();
        Long id = intent.getLongExtra("id", 0);

        if (checkInternetConnection()) {
            new MyGetFood().execute(id);
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("FoodDetails Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class MyGetFood extends AsyncTask<Long, Void, Void> {

        protected void onPreExecute() {
            progressBar = new ProgressDialog(FoodDetailsActivity.this);
            progressBar.setCancelable(true);
            progressBar.setMessage("Searching ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();
            progressBarStatus = 0;
        }

        @Override
        protected Void doInBackground(Long... params) {
            food = foodSearch.getFood(params[0]);
            return null;
        }

        protected void onPostExecute(Void result) {
            fServing = food.getServings();

            spinner = (Spinner) findViewById(R.id.static_spinner);

            textView = (TextView) findViewById(R.id.textViewFoodDetails);


            StringBuilder foodName = new StringBuilder();

            foodName.append("Name: " + food.getName() + "\n");
            if (food.getType() == "Brand") {
                foodName.append("Brand: " + food.getBrandName() + "\n");
            }


            TextView tt = (TextView) findViewById(R.id.textViewFoodDname);
            tt.setText(foodName);
            final List<String> list = new ArrayList<String>();

            for (Serving s : fServing
                    ) {
                list.add(s.getServingDescription());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(FoodDetailsActivity.this, android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTextSize(20);
                    Serving serving = null;
                    for (Serving s : fServing
                            ) {
                        if (s.getServingDescription() == spinner.getSelectedItem()) {
                            serving = s;
                        }
                    }

                    if (serving != null) {

                        StringBuilder servingString = new StringBuilder();
                        servingString.append("\n");
                        if (serving.getCalories() != null) {
                            servingString.append("Calories: " + serving.getCalories() + "\n");
                        }
                        if (serving.getCarbohydrate() != null) {
                            servingString.append("Carbohydrate: " + serving.getCarbohydrate() + "\n");
                        }
                        if (serving.getSugar() != null) {
                            servingString.append("Sugar: " + serving.getSugar() + "\n");
                        }
                        if (serving.getProtein() != null) {
                            servingString.append("Protein: " + serving.getProtein() + "\n");
                        }
                        if (serving.getFat() != null) {
                            servingString.append("Fat: " + serving.getFat() + "\n");
                        }
                        if (serving.getSaturatedFat() != null) {
                            servingString.append("Saturated Fat: " + serving.getSaturatedFat() + "\n");
                        }
                        if (serving.getTransFat() != null) {
                            servingString.append("Trans Fat: " + serving.getTransFat() + "\n");
                        }
                        if (serving.getCholesterol() != null) {
                            servingString.append("Cholesterol: " + serving.getCholesterol() + "\n");
                        }
                        if (serving.getSodium() != null) {
                            servingString.append("Sodium: " + serving.getSodium() + "\n");
                        }
                        if (serving.getPotassium() != null) {
                            servingString.append("Potassium: " + serving.getPotassium() + "\n");
                        }
                        if (serving.getCalcium() != null) {
                            servingString.append("Calcium: " + serving.getCalcium() + "\n");
                        }


                        textView.setText(servingString);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            progressBar.dismiss();
        }
    }
    public boolean checkInternetConnection() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec
                = (ConnectivityManager) this.getSystemService(this.getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED) {
            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
}
