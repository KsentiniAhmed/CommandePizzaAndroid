package com.example.ksentini.projectandroid;

/**
 * Created by ksentini on 10/05/17.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AllPizzaActivity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> pizzaList;

    // url to get all pizza list
    private static String url_all_pizza = "http://192.168.1.151/test_android/get_all_pizza.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PIZZA = "pizza";
    private static final String TAG_PID = "id_pizza";
    private static final String TAG_NAME = "nom_pizza";
    private static final String TAG_USER = "username";
    private static final String TAG_TIME = "time_pizza";



    // pizza JSONArray
    JSONArray pizza = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_pizza);

        // Hashmap for ListView
        pizzaList = new ArrayList<HashMap<String, String>>();

        // Loading pizza in Background Thread
        new LoadAllPizza().execute();

        // Get listview
        ListView lv = getListView();

        // on seleting single product
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String pid = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();

            }
        });

    }



    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllPizza extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllPizzaActivity.this);
            pDialog.setMessage("Loading pizza. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All pizza from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_pizza, "GET", params);

            // Check your log cat for JSON reponse
            //Log.d("All pizza: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // pizza found
                    // Getting Array of pizza
                    pizza = json.getJSONArray(TAG_PIZZA);

                    // looping through All pizza
                    for (int i = 0; i < pizza.length(); i++) {
                        JSONObject c = pizza.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_PID);
                        String name = c.getString(TAG_NAME);
                        String username = c.getString(TAG_USER);
                        String timecommande = c.getString(TAG_TIME);
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);
                        map.put(TAG_USER, username);
                        map.put(TAG_TIME, timecommande);

                        // adding HashList to ArrayList
                        pizzaList.add(map);
                    }
                } else {
                    // no pizza found
                    // Launch Add New product Activity
                    Intent i = new Intent(getApplicationContext(),
                            NewPizzaActivity.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all pizza
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            AllPizzaActivity.this, pizzaList,
                            R.layout.list_item, new String[] { TAG_PID,
                            TAG_NAME, TAG_USER, TAG_TIME},
                            new int[] { R.id.pid, R.id.name, R.id.username, R.id.time_pizza });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}