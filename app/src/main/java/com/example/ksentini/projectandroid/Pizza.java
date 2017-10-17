package com.example.ksentini.projectandroid;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by ksentini on 07/05/17.
 */

public class Pizza  extends AppCompatActivity {
    Button btnViewPizza;
    Button btnViewMyPizza;

    Button btnViewCommandes;
    public static final String USER_NAME = "USERNAME";
    String username;

    ImageButton menu_add_pizza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pizza);

        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.USER_NAME);

        TextView textView = (TextView) findViewById(R.id.textView3);
        ImageButton btn_cancel    = (ImageButton)findViewById (R.id.pizza_select_cancel);

        textView.setText("Welcome "+username);
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        });

        menu_add_pizza = (ImageButton) findViewById(R.id.menu_add_pizza);



           menu_add_pizza.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intentt = new Intent(Pizza.this, PizzaMenu.class);
            intentt.putExtra(USER_NAME, username);
           // finish();
            startActivity(intentt);
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

        }
    });

        // Buttons
        btnViewPizza = (Button) findViewById(R.id.btnViewPizza);
        btnViewMyPizza = (Button) findViewById(R.id.btnViewMyPizza);


        // view products click event
        btnViewPizza.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), AllPizzaActivity.class);
                i.putExtra(USER_NAME, username);

                startActivity(i);

            }
        });
        btnViewMyPizza.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), MyPizzaActivity.class);
                i.putExtra("username",username);
                startActivity(i);

            }
        });

        // view products click event


/*
    public static void ShowMenu(final Context context){
        final LayoutInflater factory = LayoutInflater.from(context);
        View view = factory.inflate(R.layout.pizza, null);

        ImageButton showpizza  = (ImageButton) view.findViewById (R.id.menu_add_pizza);

        final AlertDialog alertview = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
                .create();

        /*btn_cancel.setOnClickListener(new OnClickListener(){
            public void onClick(View arg0) { alertview.dismiss(); }
        });
        showpizza.setOnClickListener(new OnClickListener(){
            public void onClick(View arg0) { alertview.dismiss(); NewPizza(context); }
        });



        alertview.show();
    }*/

}
}