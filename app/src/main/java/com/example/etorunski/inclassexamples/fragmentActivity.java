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

        if(isTablet)
        {
            final info_fragment inf = new info_fragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentspace, inf).commit();

            Button b = (Button)findViewById(R.id.button1);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inf.setMessage(("New message here"));
                    getFragmentManager().beginTransaction()
                            .remove( inf ).commit();
                }
            });

            b = (Button)findViewById(R.id.button2);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction()
                            .add(R.id.fragmentspace, inf).commit();
                }
            });

        }
        else
        {
            Button b = (Button)findViewById(R.id.button1);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(fragmentActivity.this, phone_details.class));
                }

            });

        }
        Log.d("Fragment activity:", ""+isTablet);
    }
}
