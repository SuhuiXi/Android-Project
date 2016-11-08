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

    protected TextView first, second, third;
    protected ProgressBar pBar ;
    protected final static String ACTIVITY_NAME = "XMLParsing";
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml_parsing);

        first = (TextView)findViewById(R.id.first_text_field);
        second = (TextView)findViewById(R.id.second_text_field);
        third = (TextView)findViewById(R.id.third_text_field);


        pBar = (ProgressBar)findViewById(R.id.progressBar);

        pBar.setMax(3);
        pBar.setVisibility(View.VISIBLE);
        new NetworkAsyncTask() .execute("http://torunski.ca/CST2335_XML.xml");

    }


    public class NetworkAsyncTask extends AsyncTask<String, String, Void >
    {
        public Void doInBackground(String ... args)
        {
            //State is used for counting how many times we update the GUI
            state = 0;

            try{
                //Create a URL object to download
                URL url = new URL(args[0]);

                //Connect to a server and get an inputStream to read data
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream istream = urlConnection.getInputStream();

                //Create an XMLPullParser object:
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                //Give the inputStream to the pull parser to read the XML document
                xpp.setInput(istream, "UTF8");

                //Setting up a while loop to iterate over the XML tags. The
                //parser is initially at the start of the document:
                int type = XmlPullParser.START_DOCUMENT;

                while(type != XmlPullParser.END_DOCUMENT) {

                    switch (type) {
                        //If the current XML event is the start of the document
                        case XmlPullParser.START_DOCUMENT:
                            Log.d(ACTIVITY_NAME, "XML Start document");
                            break;

                        //If the current XML event is the end of the document
                        case XmlPullParser.END_DOCUMENT:
                            Log.d(ACTIVITY_NAME, "XML End document");
                            break;

                        //If the current XML event is an opening tag
                        case XmlPullParser.START_TAG:
                            String name = xpp.getName();
                            Log.d(ACTIVITY_NAME, "XML Start tag:" + name);

                            if (name.equals("AMessage")) {
                                String message = xpp.getAttributeValue(null, "message");
                                publishProgress( message );
                            }
                            break;

                        //If the current XML event is an closing tag
                        case XmlPullParser.END_TAG:
                            Log.d(ACTIVITY_NAME, "XML End tag:"+xpp.getName());
                            break;

                        //If the current XML event text between opening and closing tags
                        case XmlPullParser.TEXT:
                            Log.d(ACTIVITY_NAME, "XML text:" + xpp.getText());
                            break;
                    }
                    type = xpp.next(); //advances to next xml event
                }
            }
            catch(Exception e)
            {
                Log.e("XML PARSING Exception:", e.getMessage());
            }

            return null;
        }


        public void onProgressUpdate(String ...updateInfo)
        {
            //Update different parts of the GUI depending on the current state
            //This is the state pattern:
            switch(state ++)
            {
                case 0:
                    first.setText(updateInfo[0]);
                    break;
                case 1:
                    second.setText(updateInfo[0]);
                    break;
                case 2:
                    third.setText(updateInfo[0]);
                    break;
            }
            pBar.setProgress( state );
        }


        //After parsing the XML, hide the progress bar
        public void onPostExecute(Void vd)
        {
             pBar.setVisibility(View.INVISIBLE);
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