package com.example.etorunski.inclassexamples;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by etorunski on 2016-10-04.
 */

public class SensorActivity extends Activity {


    //Sensor manager has a list of all the sensors
    private SensorManager mSensorManager;


    private EditText editLight, editAccel, editMagnet;

    //want to get access to one sensor:
    private Sensor mSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sensor_layout);

        editLight = (EditText)findViewById(R.id.editLight);
        editAccel = (EditText)findViewById(R.id.editAccel);
        editMagnet= (EditText)findViewById(R.id.editMagnet);

// Reading sensors
        //Get a reference to the phone's "Sensor Catalog"
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //See if the Sensor type is on your phone: Change what comes after Sensor. to get other sensors
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(mSensor != null) {
            //This function will call onSensorChanged whenever the sensor values have changed
            mSensorManager.registerListener(new AccelerometerListener(),
                    mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if(mSensor != null) {
            //This function will call onSensorChanged whenever the sensor values have changed
            mSensorManager.registerListener(new LightListener(),
                    mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }


        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if(mSensor != null) {
            //This function will call onSensorChanged whenever the sensor values have changed
            mSensorManager.registerListener(new CompassListener(),
                    mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

//end reading sensors
    }





    private class AccelerometerListener  implements SensorEventListener {
        //SensorListener interface:
        //On sensor changed will pass in an array of float values. Depending on the sensor type,
        //This will be an array of size 1 for the light sensor, or size 3 for the accelerometer,
        //orientation sensor, etc.
        public void onSensorChanged(SensorEvent event) {

            float info[] = event.values;
            try {
                editAccel.setText(String.format("X:%1.4f  Y:%1.4f  Z:%1.4f", info[0], info[1], info[2]));
            }catch(Exception e)
            {
                Log.e("Crash:", e.getMessage());
            }
        }

        //I don't care about this function, only the sensor changed function
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        //end of required interface functions
        //End of sensor listener interface
    }


    private class CompassListener  implements SensorEventListener {
        //SensorListener interface:
        //On sensor changed will pass in an array of float values. Depending on the sensor type,
        //This will be an array of size 1 for the light sensor, or size 3 for the accelerometer,
        //orientation sensor, etc.
        public void onSensorChanged(SensorEvent event) {

            float info[] = event.values;
            try {
                editMagnet.setText(String.format("X:%1.4f  Y:%1.4f  Z:%1.4f", info[0], info[1], info[2]));
            }catch(Exception e)
            {
                Log.e("Crash:", e.getMessage());
            }
        }

        //I don't care about this function, only the sensor changed function
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        //end of required interface functions
        //End of sensor listener interface
    }


    private class LightListener  implements SensorEventListener {
        //SensorListener interface:
        //On sensor changed will pass in an array of float values. Depending on the sensor type,
        //This will be an array of size 1 for the light sensor, or size 3 for the accelerometer,
        //orientation sensor, etc.
        public void onSensorChanged(SensorEvent event) {

            editLight.setText("Lux:"+ event.values[0]);
        }

        //I don't care about this function, only the sensor changed function
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        //end of required interface functions
        //End of sensor listener interface
    }
}
