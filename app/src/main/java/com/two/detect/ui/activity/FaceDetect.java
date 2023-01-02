package com.two.detect.ui.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.two.detect.R;
import com.two.detect.ui.activity.Detect.FaceView;
import com.two.detect.ui.activity.UI.CameraView;
import android.widget.RelativeLayout;
import com.two.detect.ui.activity.UI.AutoFitTextureView;
import android.view.TextureView;
import android.graphics.SurfaceTexture;
import com.two.detect.ui.activity.Detect.DetectFaceTask;
import android.graphics.Bitmap;
import com.two.detect.common.activity.BaseActivity;
public class FaceDetect extends BaseActivity {
	private AutoFitTextureView mTextureView;
    private RelativeLayout mainView;

	CameraView cameraView;
	FaceView drawView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.face_detect);
		mTextureView = findViewById(R.id.texture);
		mainView = findViewById(R.id.facedetectLinearLayout1);
		cameraView = new CameraView(this, mTextureView);
        drawView = new FaceView(this);
        drawView.textureView = mTextureView;
		mainView.addView(drawView);
		
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
			try{
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
		
			}}catch(Exception e){
				
			}
		}

    };

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	
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
