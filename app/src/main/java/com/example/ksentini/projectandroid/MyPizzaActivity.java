package com.example.ksentini.projectandroid;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ksentini on 11/05/17.
 */

public class MyPizzaActivity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> pizzaList;

    // url to get all pizza list
    private static String url_my_pizza = "http://192.168.1.151/test_android/get_my_pizza.php?username=";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PIZZA = "pizza";
    private static final String TAG_PID = "id_pizza";
    private static final String TAG_NAME = "nom_pizza";
    private static final String TAG_USER = "username";
    private static final String TAG_TIME = "time_pizza";
     String username;


    // pizza JSONArray
    JSONArray pizza = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_pizza);


        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        // Hashmap for ListView
        pizzaList = new ArrayList<HashMap<String, String>>();

        // Loading pizza in Background Thread
        new MyPizzaActivity.LoadAllPizza().execute();

        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
            pDialog = new ProgressDialog(MyPizzaActivity.this);
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
            //params.add(new BasicNameValuePair("username", username));
            // getting JSON string from URL
            String url = "http://192.168.1.151/test_android/get_my_pizza.php?username="+username;
            JSONObject json = jParser.makeHttpRequest(url, "POST", params);
            Log.d("URL", "doInBackground: "+url);
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
                        String usernam = c.getString(TAG_USER);
                        String timecommande = c.getString(TAG_TIME);
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);
                        map.put(TAG_USER, usernam);
                        map.put(TAG_TIME, timecommande);

                        // adding HashList to ArrayList
                        pizzaList.add(map);
                    }
                } else {
                    // no pizza found
                    // Launch Add New product Activity
                    /*Intent i = new Intent(getApplicationContext(),
                            NewPizzaActivity.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);*/
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
                            MyPizzaActivity.this, pizzaList,
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