package com.bawe.test_emerio.Activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bawe.test_emerio.Adapters.LocationAdapter;
import com.bawe.test_emerio.Models.LocationModel;
import com.bawe.test_emerio.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView locationListView;
    private LocationAdapter locationAdapter;
    private ArrayList<Object> locations;
    private String[] combinedLocations = {""};
    private LocationModel selected = null;
    private AutoCompleteTextView auto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Autocomplete
        setAutoComplete();

        //Listview
        locationAdapter = new LocationAdapter(this.getApplicationContext(),R.layout.li_location_item);

        locationListView = findViewById(R.id.location_list);
        locationListView.setAdapter(locationAdapter);
        locationListView.setOnItemClickListener(this);

        if (locations == null)
            new AsyncDownloadLocationTask().execute("http://52.76.85.10/test/location.json");

        //Filter
        auto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String filterStr = editable.toString().toLowerCase(Locale.getDefault());
                locationAdapter.filter(filterStr);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFindButton(View view) {
        if (selected != null) {
            Intent dokterIntent = new Intent(getBaseContext(), DokterListActivity.class);
            dokterIntent.putExtra("locationArea", selected.getArea());
            dokterIntent.putExtra("locationCity", selected.getCity());
            startActivity(dokterIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Must choose a location first!", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadData(String jsonStr) {
        try {
            JSONArray jArr = new JSONArray(jsonStr);
            locations = new ArrayList<>();
            ArrayList<String> combined = new ArrayList<>();

            for (int i = 0; i < jArr.length(); ++i)
            {
                JSONObject jObj = jArr.optJSONObject(i);
                LocationModel loc = new LocationModel(jObj.optString("area"), jObj.optString("city"));
                combined.add(loc.combine());
                locations.add(loc);
            }
            locationAdapter.populate(locations);
            locationListView.setAdapter(locationAdapter);
            combinedLocations = new String[combined.size()];
            combinedLocations = combined.toArray(combinedLocations);
            setAutoComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAutoComplete() {
        ArrayAdapter<String> autoAdapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, combinedLocations);

        if (auto == null) {
            auto = (AutoCompleteTextView) findViewById(R.id.auto_search);
            auto.setThreshold(1);
            auto.clearFocus();

            auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    auto.clearFocus();
                }
            });
        }

        auto.setAdapter(autoAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (locations != null)
            selected = (LocationModel) locations.get(position);
    }


    public class AsyncDownloadLocationTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);

                }

                return buffer.toString();


            } catch (Exception e) {
                e.printStackTrace();
            }  finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            loadData(s);
        }
    }

}
