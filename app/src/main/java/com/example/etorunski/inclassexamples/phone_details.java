package com.example.etorunski.inclassexamples;

import android.app.Activity;
import android.os.Bundle;

public class phone_details extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_details);


        final info_fragment inf = new info_fragment();
        inf.setMessage("Hi phone");
        getFragmentManager().beginTransaction()
                .add(R.id.fragmentspace, inf).commit();

    }
}
