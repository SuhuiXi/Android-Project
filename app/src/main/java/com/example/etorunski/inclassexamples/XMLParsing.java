package com.example.etorunski.inclassexamples;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by etorunski on 2016-10-25.
 */

public class XMLParsing extends Activity {
    TextView first, second, third;
ProgressBar pBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml_parsing);

        first = (TextView)findViewById(R.id.first_text_field);
        second = (TextView)findViewById(R.id.second_text_field);
        third = (TextView)findViewById(R.id.third_text_field);


        pBar = (ProgressBar)findViewById(R.id.progressBar);
        pBar.setVisibility(View.INVISIBLE);


        try{

            URL url = new URL("http://torunski.ca/CST2335_XML.xml");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream istream = urlConnection.getInputStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(istream, "UTF8");
            boolean finished = false;
            int type = XmlPullParser.START_DOCUMENT;

            while(type != XmlPullParser.END_DOCUMENT) {

                switch (type) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        finished = true;
                        break;
                    case XmlPullParser.START_TAG:
                        String name = xpp.getName();
                        if (name.equals("AMessage")) {
                            String message = xpp.getAttributeValue(null, "message");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        break;
                }
                type = xpp.next();
            }
        }
        catch(Exception e)
        {
            Log.e("XML PARSING", e.getMessage());
        }
    }


}




























/*
    private class AsyncXML extends AsyncTask<String, String, Void>
    {
        private int state ;

        public Void doInBackground(String ...params)
        {
        }

        public void onProgressUpdate(String ... args)
        {
        }

        public void onPostExecute(Void vd)
        {

        }
    }
 */