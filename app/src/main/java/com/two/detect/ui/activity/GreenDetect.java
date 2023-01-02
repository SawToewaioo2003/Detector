package com.two.detect.ui.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.PersistableBundle;
import android.os.Bundle;
import com.two.detect.R;
import com.two.detect.ui.activity.UI.AutoFitTextureView;
import android.widget.RelativeLayout;
import com.two.detect.ui.activity.UI.CameraView;
import com.two.detect.ui.activity.UI.DrawView;
import android.view.TextureView;
import android.graphics.SurfaceTexture;
import com.two.detect.ui.activity.Detect.DetectTask;
import android.graphics.Bitmap;
import com.two.detect.common.activity.BaseActivity;
public class GreenDetect extends BaseActivity {
	private AutoFitTextureView mTextureView;
    private RelativeLayout mainView;

	CameraView cameraView;
	DrawView drawView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.green_detect);
		mTextureView = findViewById(R.id.texture);
		mainView = findViewById(R.id.greendetectLinearLayout1);
		cameraView = new CameraView(this, mTextureView);
        drawView = new DrawView(this);
        drawView.textureView = mTextureView;
		mainView.addView(drawView);
	}

	boolean processing=false;
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
			if (processing) {
                return;
            } else {


				Bitmap photo=mTextureView.getBitmap(mTextureView.getWidth() / 10, mTextureView.getHeight() / 10);

				Thread t=new Thread(new DetectTask(photo, new DetectTask.ImageResponse(){

											@Override
											public void processFinished() {
												processing = false;
												drawView.invalidate();
											}


										}, drawView));

				t.start();




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
