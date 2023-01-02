package com.two.detect.ui.activity.Detect;
import java.util.ArrayList;
import android.graphics.Bitmap;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.util.Log;
import android.graphics.PointF;

public class DetectFace {
	FaceDetector mFacesDetector;
	public  ArrayList<Face> findFaces(Bitmap bitmap) {
		ArrayList<Face> facesList = new ArrayList<Face>();
		long startTime = System.currentTimeMillis();
		if (bitmap != null) {
			Face[] faces = new Face[5];
			mFacesDetector = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), 5);
			int numberOfFaces = mFacesDetector.findFaces(bitmap, faces);
			
			for (int i = 0; i < numberOfFaces; i++) {
				facesList.add(faces[i]);/*from   www.  j a v  a2s  .co  m*/
			}
			Log.d("DetectTask", "Finding faces took " + (System.currentTimeMillis() - startTime) + " ms.");
		}

		return facesList;
	}
	
}
