package com.example.ksentini.projectandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
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
import android.widget.Spinner;

import static com.example.ksentini.projectandroid.R.id.inputDesc;
import static com.example.ksentini.projectandroid.R.id.inputPrice;


/**
 * Created by ksentini on 08/05/17.
 */

public class PizzaCommande extends AppCompatActivity {
    private ProgressDialog pDialog;
    public static final String USER_NAME = "USERNAME";

    JSONParser jsonParser = new JSONParser();

    EditText username ;
    Spinner gout ;
    Spinner sauce ;
    Spinner patte ;


    // url to create new pizza
    private static String url_create_pizza = "http://192.168.1.151/test_android/create_newpizza.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pizza_commande);
        username = (EditText) findViewById(R.id.username);

        gout   = (Spinner) findViewById(R.id.gout);
        sauce   = (Spinner) findViewById(R.id.sauce);
        patte   = (Spinner) findViewById(R.id.patte);
        //boutton commande
        ImageButton commander = (ImageButton) findViewById(R.id.commander);
        ImageButton btn_cancel    = (ImageButton)findViewById (R.id.pizza_select_cancel);

        Intent intent = getIntent();
        final String usern = intent.getStringExtra(MainActivity.USER_NAME);
        // button click event
        commander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewPizza attemptPizza = new CreateNewPizza();
                attemptPizza.execute(
                        gout.getSelectedItem().toString(),
                        sauce.getSelectedItem().toString(),
                        patte.getSelectedItem().toString(),
                        usern);
            }

        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), PizzaMenu.class);

                startActivity(i);

            }
        });



    }//fin onclick

    /**
     * Background Async Task to Create new pizza
     * */
    class CreateNewPizza extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PizzaCommande.this);
            pDialog.setMessage("Creating pizza..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


    /**
     * Creating pizza
     * */
    protected String doInBackground(String... args) {

        String gout =  args[0];
        String sauce = args[1];
        String patte = args[2];

        String username = args[3];


        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("nom_pizza", gout));
        params.add(new BasicNameValuePair("type_pate", patte));
        params.add(new BasicNameValuePair("Sauce", sauce));
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
                //finish();
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
    }//fin create new pizza

}
