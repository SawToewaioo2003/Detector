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

public class MainActivity extends BaseActivity { 
	private AutoFitTextureView mTextureView;
    private RelativeLayout mainView;
    CameraView cameraView;
    FaceView drawView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextureView =findViewById(R.id.texture);
		mainView=findViewById(R.id.activitymainRelativeLayout1);
        cameraView = new CameraView(MainActivity.this, mTextureView);
        drawView = new FaceView(MainActivity.this);
        drawView.textureView = mTextureView;
		mainView.addView(drawView);
		mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
    }
	boolean processing = false;
	boolean task2=false;
	boolean task3=false;
	boolean task4=false;
	boolean task5=false;
    private TextureView.SurfaceTextureListener mSurfaceTextureListener
	= new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture,
                                              int width, int height) {
            cameraView.openCamera(width, height);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture,
                                                int width, int height) {
            cameraView.configureTransform(width, height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
           if (processing&task2&task3&task4&task5) {
                return;
            }else{
				Bitmap photo = mTextureView.getBitmap(mTextureView.getWidth(), mTextureView.getHeight()).copy(Bitmap.Config.RGB_565, true); 

				
				if(!processing){
					processing=true;
					Thread t=new Thread(new DetectFaceTask(photo, new DetectFaceTask.ImageResponse(){

												@Override
												public void processFinished() {
													processing=false;

												}
											}, drawView));

					t.start();
				}
				if(!task2){
					task2=true;
					Thread t=new Thread(new DetectFaceTask(photo, new DetectFaceTask.ImageResponse(){

												@Override
												public void processFinished() {
													task2=false;

												}
											}, drawView));

					t.start();
				}
				if(!task3){
					task3=true;
					Thread t=new Thread(new DetectFaceTask(photo, new DetectFaceTask.ImageResponse(){

												@Override
												public void processFinished() {
													task3=false;

												}
											}, drawView));

					t.start();
				}
				
				if(!task4){
					task4=true;
					Thread t=new Thread(new DetectFaceTask(photo, new DetectFaceTask.ImageResponse(){

												@Override
												public void processFinished() {
													task4=false;

												}
											}, drawView));

					t.start();
				}
				if(!task5){
					task5=true;
					Thread t=new Thread(new DetectFaceTask(photo, new DetectFaceTask.ImageResponse(){

												@Override
												public void processFinished() {
													task5=false;

												}
											}, drawView));

					t.start();
				}
			//Bitmap photo=mTextureView.getBitmap(mTextureView.getWidth()/10,mTextureView.getHeight()/10);
            
		/*	Thread t=new Thread(new DetectTask(photo, new DetectTask.ImageResponse(){

															 @Override
															 public void processFinished() {
																 processing=false;
																 drawView.invalidate();
															 }

				
			},drawView));
			
			t.start();
			*/
			
			

        }
		}

    };

    @Override
    public void onResume() {
        super.onResume();
        cameraView.startBackgroundThread();
        if (mTextureView.isAvailable()) {
            cameraView.openCamera(mTextureView.getWidth(), mTextureView.getHeight());
        } else {
            mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }

    @Override
    public void onPause() {
        cameraView.closeCamera();
        cameraView.stopBackgroundThread();
        super.onPause();
    }
}
