package com.two.detect.ui.activity.Detect;
import android.graphics.Bitmap;

public class DetectFaceTask implements Runnable {
	FaceView drawView;
    DetectFace df=new DetectFace();
	private Bitmap photo;
    private ImageResponse imageResponse;
	public interface ImageResponse {
        void processFinished();
    }
	public DetectFaceTask(Bitmap photo, ImageResponse imageResponse, FaceView drawView) {
        this.drawView = drawView;
        this.photo = photo;
        this.imageResponse = imageResponse;
    }
	@Override
	public void run() {
		drawView.faces = df.findFaces(photo);
		drawView.invalidate();
        imageResponse.processFinished();
		

	}


    
    
    
    
}
