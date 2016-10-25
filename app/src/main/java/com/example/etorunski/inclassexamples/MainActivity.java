package com.example.etorunski.inclassexamples;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Vibrator;
import android.app.Activity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends Activity {

    final static int REQUEST_SENSORS = 1;
    final static int REQUEST_SECOND = 2;
    final static int REQUEST_EMAIL=3;
    final static int REQUEST_LIST = 4;

    //An array of strings for the listview.
    String listItems [] = { "Vibrate phone", "Show Sensors", "Second Activity", "List Activity", "Send Email", "Toggle flash", "Cursor database", "AsyncTask & XML"};


    //the flash. You have to give your app permissions to use the camera through settings:
    //http://stackoverflow.com/questions/23904459/android-java-lang-runtimeexception-fail-to-connect-to-camera-service
    private Camera camera;


    //Get a reference to the vibration motor. It vibrates for 500 ms in the button click
    protected Vibrator vb;


    private boolean isFlashOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isFlashOff = true;

        //This line makes the app fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //if the bundle is not null (if this isn't the first time this activity starts)
        if(savedInstanceState!= null)
        {
            int passedMessage = savedInstanceState.getInt("GreetingMessage");
            Log.d("In MainActivity", "Passed:"+passedMessage);
        }

        vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //This commented out code shows that I can dynamically load a layout by code.
        // if(Math.random() < 0.5)
            setContentView(R.layout.activity_main);
      //  else  setContentView(R.layout.new_layout);


        //Get a reference to the list view:
        ListView lv = (ListView)findViewById(R.id.activityList);

        //Create an array adapter with the layout ID, and the array of strings
        ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(this, R.layout.array_list_layout, listItems);


        //Set the data for the list:
        lv.setAdapter(stringAdapter);


        //Listen for clicks on the ListView:
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0: // Vibrate the phone
                        vb.vibrate(500);
                        break;


                    case 1: //Show sensors
                        MainActivity.this.startActivityForResult( new Intent(MainActivity.this, SensorActivity.class ), REQUEST_SENSORS );

                        break;


                    case 2: //second activity
                        //6 is my request code:
                        MainActivity.this.startActivityForResult( new Intent(MainActivity.this, SecondActivity.class ),  REQUEST_SECOND );
                        break;


                    case 3: //List Activity
                        //4 is my request code:
                        MainActivity.this.startActivityForResult( new Intent(MainActivity.this, ListActivity.class ) , 4 );
                        break;


                    case 4: //Send an email
                        sendEmail();
                        break;

                    case 5: //Turn on flash
                        toggleFlash();
                        break;
                    case 6:
                        MainActivity.this.startActivityForResult( new Intent(MainActivity.this, CursorListActivity.class ) , 5 );
                        break;
                    case 7:
                        MainActivity.this.startActivity(new Intent(MainActivity.this, XMLParsing.class));
                        break;
                }
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        String s = "";
        if((requestCode == REQUEST_SECOND)&& (resultCode == RESULT_OK))
        {
            Log.i("Activity result", "request code is 6, meaning the SecondActivity has finished");
            Log.w("The fox said:", s = data.getStringExtra("MyAnswer"));
            Toast.makeText(this, s, Toast.LENGTH_LONG);
        }


        else if(requestCode == REQUEST_EMAIL)
        {
            Log.i("Activity result", "request code is 3, meaning the email Activity has finished");
        }

    }


    private void sendEmail()
    {
        //Send an email:
        Intent sendEmailIntent = new Intent(Intent.ACTION_SENDTO );
        sendEmailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this

        //A string array of all "To" recipient email addresses.
        String toAddresses [] = {"torunse@algonquincollege.com", "billg@microsoft.com", "andy_Rubin@google.com"};
        sendEmailIntent.putExtra(Intent.EXTRA_EMAIL,  toAddresses);

        //A string array of all "CC" recipient email addresses.
        String ccAddresses [] = {"copy1@algonquincollege.com", "copy2@microsoft.com"};
        sendEmailIntent.putExtra(Intent.EXTRA_CC,  ccAddresses);

        //A string array of all "BCC" recipient email addresses.
        String bccAddresses [] = {"bcopy1@algonquincollege.com", "bcopy2@microsoft.com"};
        sendEmailIntent.putExtra(Intent.EXTRA_BCC, bccAddresses);

        //set the subject:
        sendEmailIntent.putExtra(Intent.EXTRA_SUBJECT, "Good demo");

        //set the body:
        sendEmailIntent.putExtra(Intent.EXTRA_TEXT, "this is the body of the email");

        //Start the email activity with the data I've prepared. When it returns, request code will be 3:
        MainActivity.this.startActivityForResult( sendEmailIntent , 3 );
    }


    private void toggleFlash()
    {
        try
        {
            if (isFlashOff) {
                camera = Camera.open();
                SurfaceTexture dummy = new SurfaceTexture(0);
                camera.setPreviewTexture(dummy);
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
            } else {

                Camera.Parameters parameters2 = camera.getParameters();
                parameters2.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters2);
                camera.stopPreview();
                camera.release();
                camera = null;
            }


            isFlashOff = ! isFlashOff;
        }
        catch (Exception e)
        {
            Log.d("Crash!!!", e.getMessage());
        }
    }
}
