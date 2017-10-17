package com.example.ksentini.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by ksentini on 07/05/17.
 */

public class PizzaMenu extends AppCompatActivity {
    ImageButton order_add_pizza;
    ImageButton pizza_select_full;
    public static final String USER_NAME = "USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_add_pizza);
        ImageButton btn_cancel    = (ImageButton)findViewById (R.id.pizza_select_cancel);

        pizza_select_full = (ImageButton) findViewById(R.id.pizza_select_full);


        pizza_select_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentt = getIntent();
                Intent intent = new Intent(PizzaMenu.this, PizzaCommande.class);
                intent.putExtra(USER_NAME, intentt.getStringExtra(MainActivity.USER_NAME));
               // finish();
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent intentt = getIntent();

                Intent i = new Intent(getApplicationContext(), Pizza.class);
                i.putExtra(USER_NAME, intentt.getStringExtra(MainActivity.USER_NAME));
                // finish();
                startActivity(i);

            }
        });


    }

}
