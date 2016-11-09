package com.example.etorunski.inclassexamples;

import android.app.FragmentTransaction;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class fragmentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);
        boolean isTablet = findViewById(R.id.fragmentspace) != null;


        //If this is a tablet, then the fragment is on the same screen so load it.
        if(isTablet)
        {
            final info_fragment inf = new info_fragment();
            inf.setMessage("Set the message for the fragment");

            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentspace, inf).commit();

            //Set button 1 to remove the fragment
            Button b = (Button)findViewById(R.id.button1);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Using the builder pattern to remove the loaded fragment
                    getFragmentManager().beginTransaction().remove( inf ).commit();
                }
            });

            //set button to add a fragment to the empty FrameLayout - id.fragmentspace
            // This is the builder pattern:
            b = (Button)findViewById(R.id.button2);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction()
                            .add(R.id.fragmentspace, inf).commit();
                }
            });

        }
        else    //This is running on a phone. This layout just has 3 buttons.
        {
            Button b = (Button)findViewById(R.id.button1);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //If the user clicked button 1, start a new activity with just the fragment
                    startActivity(new Intent(fragmentActivity.this, phone_details.class));
                }

            });

        }
        Log.d("Fragment activity:", ""+isTablet);
    }
}
