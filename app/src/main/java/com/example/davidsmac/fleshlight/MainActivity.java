package com.example.davidsmac.fleshlight;

import android.graphics.Camera;
import android.hardware.camera2.CameraDevice;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.security.Policy;
import android.os.Vibrator;
import android.content.Context;

public class MainActivity extends AppCompatActivity {
    MediaPlayer m;
    Vibrator v;
    private boolean flashLightStatus = false;
    Button fleshLightButton;
    //CameraDevice cameraDevice;
    private static final int CAMERA_REQUEST = 50;
    public boolean hasCameraFlash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v = (Vibrator)  this.getSystemService(Context.VIBRATOR_SERVICE);
        fleshLightButton = (Button) findViewById(R.id.button);


        hasCameraFlash = getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        boolean isEnabled = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;




        fleshLightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST);

            }
        });


    }
    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            fleshLightButton.setText("TURN OFF FLESHLIGHT");
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
            flashLightStatus = true;
            v.vibrate(1000);
            m = MediaPlayer.create(this, R.raw.alsosprachzarathustra);
            m.start();
            //imageFlashlight.setImageResource(R.drawable.btn_switch_on);
        } catch (CameraAccessException e) {
        }
    }

    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            fleshLightButton.setText("TURN ON FLESHLIGHT");
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
            flashLightStatus = false;
            long[] pattern = {0, 500,100,500,100,500,100,500,100,500,100};
            v.vibrate(pattern, -1);
            m.stop();
            m.release();
            m = null;
            //imageFlashlight.setImageResource(R.drawable.btn_switch_off);
        } catch (CameraAccessException e) {
        }
    }

    private void dimFlash(){
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            //cameraManager.setTorchMode();
            //flashLightStatus = false;
            //imageFlashlight.setImageResource(R.drawable.btn_switch_off);
        } catch (CameraAccessException e) {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case CAMERA_REQUEST :
                if (grantResults.length > 0  &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //buttonEnable.setEnabled(false);
                    //buttonEnable.setText("Camera Enabled");
                    //imageFlashlight.setEnabled(true);
                    if (hasCameraFlash) {
                        if (flashLightStatus)
                            flashLightOff();
                        else
                            flashLightOn();
                    } else {
                        Toast.makeText(MainActivity.this, "No flash available on your device",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied for the Camera", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
