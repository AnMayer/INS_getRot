package come.example.ins_getrot;




import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import  androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class Data extends AppCompatActivity implements SensorEventListener
{
    private SensorManager sensorManager;
    private Sensor sensorRV;
    int w=0;


    public final static String model = "Acc5";
    private File rector = new File("/sdcard/INS" + model + ".txt");

    TextView Text1, Text2, Text3, Text4;

    float[] Qua;
    float[] RVect;

    FileOutputStream fOut;
    OutputStreamWriter myOutWriter;



  /*  float[] accelerometerData=new float[3];
    float[] gravityData=new float[3];
    float[] magneticData=new float[3];
    float[] gyroscopData=new float[3];
    float[] rotationData=new float[3];
    float[] inclinations=new float[3];*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorRV = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            try {
                fOut = new FileOutputStream(rector, false);
                myOutWriter = new OutputStreamWriter(fOut);
            } catch (Exception e) {
                Log.e("ERRR", "Could not create file", e);
            }
            ;
        }

    public static void GetQuaternionFromVector(float[] Q, float[] rv)
    {
        if (rv.length>=4)
        {
            Q[0]=rv[3];
        } else
            {
            Q[0]=1-rv[0]*rv[0]-rv[1]*rv[1]-rv[2]*rv[2];
            Q[0]=(Q[0]>0)? (float)Math.sqrt(Q[0]):0;
        }
        Q[1]=rv[0];
        Q[2]=rv[1];
        Q[3]=rv[2];
    }

    public void onSensorChanged(SensorEvent event) {
    // TODO Auto-generated method stub

    //sensor = event.sensor;
    int i = event.sensor.getType();
        Text1 = findViewById(R.id.tV1);
        Text2 = findViewById(R.id.tV2);
        Text3 = findViewById(R.id.tV3);
    if (i == Sensor.TYPE_ROTATION_VECTOR) {

        for (int k=0; k<4; k++){
            RVect[k] = event.values[k];
        }
        Text1.setText(String.valueOf(RVect[0]));
        Text2.setText(String.valueOf(RVect[1]));
        Text3.setText(String.valueOf(RVect[2]));
/*
    } else if (i == Sensor.TYPE_GRAVITY) {
        for (int k=0; k<3; k++){
            gravityData[k] = event.values[k];
        }

    } else if (i == Sensor.TYPE_MAGNETIC_FIELD) {
        for (int k=0; k<3; k++){
            magneticData[k] = event.values[k];
        }
    } else if (i == Sensor.TYPE_GYROSCOPE) {
        for (int k=0; k<3; k++){
            gyroscopData[k] = event.values[k];
            inclinations[k] = SensorManager.getInclination(gyroscopData);
        }
    }*/


       // Text4.setText(String.valueOf(event.values[3]));



        //GetQuaternionFromVector(Qua, RVect);
        if(w<100)
        {
            try {
                String str=Text1.getText().toString()+ " "  + Text2.getText().toString()+ " " + Text3.getText().toString();

                long TimeStamp = SystemClock.elapsedRealtimeNanos() - MainActivity.AppTime;

                myOutWriter.append(String.valueOf(TimeStamp) + " " + str).append("\n");

            }
            catch (Exception e) {
                Log.e("ERRR", "Could not create file",e);
            } ;
            w++;
        }
        else if(w==100)
        {
            Toast.makeText(this,"FileWrited",Toast.LENGTH_SHORT).show();
            try {
                myOutWriter.close();
                fOut.close();
            } catch (Exception e) {
                Log.e("ERRR", "Could not create file",e);
            } ;
            w++;
        }

    }

   // String.valueOf(SensorManager.getRotationMatrix(null, gyroscopData, gravityData, magneticData));

    //World coordinate system transformation for acceleration

    /*switch (event.sensor.getType()) {
    case Sensor.TYPE_LINEAR_ACCELERATION:
        for (int i = 0; i < 3; i++) {
            accelValues[i] = event.values[i];

        }
        if (compassValues[0] != 0)
            ready = true;

        break;

    case Sensor.TYPE_MAGNETIC_FIELD:
        for (int i = 0; i < 3; i++) {
            compassValues[i] = event.values[i];
        }
        if (accelValues[2] != 0)
            ready = true;

        break;
}

    if (!ready)
        return;
*/
    // SensorManager.getRotationMatrix(inR, inclineMatrix, accelValues, compassValues);


}

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public Sensor getSensorRV() {
        return sensorRV;
    }

    public void setSensorRV(Sensor sensorRV) {
        this.sensorRV = sensorRV;
    }
}