package com.bawe.test_emerio.Activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bawe.test_emerio.Adapters.DokterAdapter;
import com.bawe.test_emerio.Models.DokterModel;
import com.bawe.test_emerio.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DokterListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView dokterListView;
    private DokterAdapter dokterAdapter;
    private ArrayList<Object> dokters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter_list);

        //Listview
        dokterAdapter = new DokterAdapter(this.getApplicationContext(),R.layout.li_dokter_item);

        dokterListView = findViewById(R.id.dokter_list);
        dokterListView.setAdapter(dokterAdapter);
        dokterListView.setOnItemClickListener(this);

        if (dokters == null)
            new AsyncDownloadDokterListTask().execute("http://52.76.85.10/test/datalist.json");
    }

    public void loadData(String jsonStr) {
        try {
            JSONArray jArr = new JSONArray(jsonStr);
            dokters = new ArrayList<>();
            ArrayList<String> combined = new ArrayList<>();

            for (int i = 0; i < jArr.length(); ++i)
            {
                JSONObject jObj = jArr.optJSONObject(i);
                DokterModel doc = new DokterModel();
                doc.setId(jObj.optInt("id"));
                doc.setName(jObj.optString("name"));
                doc.setArea(jObj.optString("area"));
                doc.setCurrency(jObj.optString("currency"));
                doc.setPrice(jObj.optString("rate"));
                doc.setSpeciality(jObj.optString("speciality"));
                doc.setPhotoUrl(jObj.optString("photo"));
                dokters.add(doc);
            }
            dokterAdapter.populate(dokters);
            dokterListView.setAdapter(dokterAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "STUB! TODO: Implement activity 3.", Toast.LENGTH_SHORT).show();
    }

    public class AsyncDownloadDokterListTask extends AsyncTask<String, String, String> {
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
