package com.example.etorunski.inclassexamples;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class info_fragment extends Fragment {

    Context mController;
    private String myMessage;

    public info_fragment() {
        // Required empty public constructor
    }

    //Set the message to show in the textview
    public void setMessage(String s) { myMessage = s; }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //Create the layout of the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_info_fragment, container, false);

        //find the text view from the layout
        TextView tv = (TextView)v.findViewById(R.id.messagefield);

        //set the message to the TextView
        tv.setText(myMessage);

        return v;
    }

    //Get a reference to the parent activity (Controller)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mController = context;
    }


    // When the fragment is removed, delete the reference to parent
    @Override
    public void onDetach() {
        super.onDetach();
        mController = null;
    }
}
