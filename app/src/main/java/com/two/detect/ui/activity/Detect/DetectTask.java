package com.two.detect.ui.activity.Detect;
import com.two.detect.ui.activity.UI.DrawView;
import android.graphics.Bitmap;

public class DetectTask implements Runnable {
	DrawView drawView;
    DetectGreen detectGreen = new DetectGreen();
	private Bitmap photo;
    private ImageResponse imageResponse;
	public interface ImageResponse {
        void processFinished();
    }
	public DetectTask(Bitmap photo, ImageResponse imageResponse, DrawView drawView) {
        this.drawView = drawView;
        this.photo = photo;
        this.imageResponse = imageResponse;
    }
	@Override
	public void run() {
		drawView.detectedGreenSectors = detectGreen.startDetection(photo);
        imageResponse.processFinished();
	
	}

    
    
    
}
