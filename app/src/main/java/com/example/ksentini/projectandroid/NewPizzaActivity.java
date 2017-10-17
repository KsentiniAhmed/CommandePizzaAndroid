package com.example.ksentini.projectandroid;

/**
 * Created by ksentini on 09/05/17.
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewPizzaActivity extends Activity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText inputName;
    EditText inputPrice;
    EditText inputDesc;
    EditText sauce;
    EditText type_pate;
    EditText username;


    // url to create new pizza
    private static String url_create_pizza = "http://192.168.1.11/test_android/create_pizza.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pizza);

        // Edit Text
        inputName = (EditText) findViewById(R.id.inputName);
        inputPrice = (EditText) findViewById(R.id.inputPrice);
        inputDesc = (EditText) findViewById(R.id.inputDesc);
        username = (EditText) findViewById(R.id.username);
        sauce = (EditText) findViewById(R.id.sauce);
        type_pate = (EditText) findViewById(R.id.type_pate);

        // Create button
        Button btnCreatePizza = (Button) findViewById(R.id.btnCreatePizza);

        // button click event
        btnCreatePizza.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   CreateNewPizza attemptPizza = new CreateNewPizza();
                                                   attemptPizza.execute(inputName.getText().toString(),
                                                           inputPrice.getText().toString(),
                                                           inputDesc.getText().toString(),
                                                           username.getText().toString(),
                                                           type_pate.getText().toString(),
                                                           sauce.getText().toString());
                                               }

        });


    }

    /**
     * Background Async Task to Create new pizza
     * */
    class CreateNewPizza extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewPizzaActivity.this);
            pDialog.setMessage("Creating pizza..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating pizza
         * */
        protected String doInBackground(String... args) {

            String name =  args[0];
            String price = args[1];
            String description = args[2];

            String username = args[3];
            String type_pate = args[4];
            String sauce = args[5];

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("nom_pizza", name));
            params.add(new BasicNameValuePair("prix_pizza", price));
            params.add(new BasicNameValuePair("description_pizza", description));
            params.add(new BasicNameValuePair("Sauce", sauce));
            params.add(new BasicNameValuePair("type_pate", type_pate));
            params.add(new BasicNameValuePair("username", username));

            // getting JSON Object
            // Note that create pizza url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_pizza,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created pizza
                    Intent i = new Intent(getApplicationContext(), AllPizzaActivity.class);
                    startActivity(i);

                    // closing this screen
                  //  finish();
                } else {
                    Intent i = new Intent(getApplicationContext(), Pizza.class);
                    startActivity(i);
                    // failed to create pizza
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
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}