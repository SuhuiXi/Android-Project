package com.example.etorunski.inclassexamples;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml_parsing);

        first = (TextView)findViewById(R.id.first_text_field);
        second = (TextView)findViewById(R.id.second_text_field);
        third = (TextView)findViewById(R.id.third_text_field);

        try {
            URL url = new URL("http://torunski.ca/CST2335_XML.xml");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream istream = urlConnection.getInputStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(istream, "UTF8");
            boolean finished = false;

            while(!finished) {
                int type = xpp.getEventType();
                switch (type) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        finished = true;
                        break;
                    case XmlPullParser.START_TAG:
                        if (xpp.getName().equals("HelloWorld")) {
                            xpp.getAttributeValue(null, "AnAttribute");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        break;
                }
                xpp.next();
            }
        }
        catch(Exception e)
        {
            Log.e("XML PARSING", e.getMessage());
        }
    }
}
