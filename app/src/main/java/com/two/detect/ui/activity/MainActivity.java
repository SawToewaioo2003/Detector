package com.two.detect.ui.activity;

import android.os.Bundle;
import com.two.detect.R;
import com.two.detect.common.activity.BaseActivity;
import com.two.detect.ui.activity.UI.AutoFitTextureView;
import android.widget.RelativeLayout;
import com.two.detect.ui.activity.UI.CameraView;
import com.two.detect.ui.activity.UI.DrawView;
import android.view.TextureView;
import android.graphics.SurfaceTexture;
import android.graphics.Bitmap;
import com.two.detect.ui.activity.UI.GlobalUsage;
import android.os.Handler;
import com.two.detect.ui.activity.Detect.DetectTask;
import android.os.Looper;
import com.two.detect.ui.activity.Detect.DetectFaceTask;
import com.two.detect.ui.activity.Detect.FaceView;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends BaseActivity { 

	private static final int PERMISSION_REQUEST_CODE = 200;
	Button greendetect,facedetect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		greendetect = findViewById(R.id.activitymainButton1);
		facedetect = findViewById(R.id.activitymainButton2);
		greendetect.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1) {
					if (checkPermission()) {
						Intent i=new Intent(getApplicationContext(), GreenDetect.class);
						startActivity(i);
					} else {
						requestPermission();
					}
				}


			});
		facedetect.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1) {
					if (checkPermission()) {
						Intent i=new Intent(getApplicationContext(), FaceDetect.class);
						startActivity(i);
					} else {
						requestPermission();
					}
				}


			});

		if (!checkPermission()) {
			requestPermission();
		}

    }

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
					//  boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted) {
						Toast.makeText(getApplicationContext(),"Permission Granted, Now you can access Camera..",Toast.LENGTH_LONG).show();
						
					} else {
						Toast.makeText(getApplicationContext(),"Permission Denied, Now you can't access Camera..",Toast.LENGTH_LONG).show();
						requestPermission();
					}
				}
		}
	}

	private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
		// int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;//&& result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

}
