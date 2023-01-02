package com.two.detect.ui.activity.Detect;
import android.view.View;
import android.graphics.Paint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.media.FaceDetector.Face;
import android.graphics.Canvas;
import java.util.ArrayList;
import android.graphics.PointF;
import com.two.detect.ui.activity.UI.AutoFitTextureView;
import android.graphics.Rect;

public class FaceView extends View {
  //  Face[] faces =new Face[0];
	public AutoFitTextureView textureView;
	public ArrayList<Face> faces=new ArrayList<>();
	int size=0;
    Paint myPaint = new Paint();
	Paint eyePaint = new Paint();
    Context context;
    int biggestX = 0, smallestX = 0, biggestY = 0, smallestY = 0;
    boolean processing = false;

    private void init() {
        myPaint.setColor(Color.GREEN);
		myPaint.setStyle(Paint.Style.STROKE);
		myPaint.setStrokeWidth(5);
		eyePaint.setColor(Color.BLUE);
		eyePaint.setStyle(Paint.Style.STROKE);
		eyePaint.setStrokeWidth(1);
    }

    public FaceView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

	@Override
	protected void onDraw(Canvas canvas) {
		//super.onDraw(canvas);
		try{
		for(Face f:faces){
			canvas.drawRect(getFaceRect(f),myPaint);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private static final float GOLDEN_RATIO = 1.61803399f;

    public static Rect getFaceRect(Face face) {
        // GOLDEN_RATIO
        // float xRatio = ((float) getWidth()) / mBitmap.getWidth();
        // float yRatio = ((float) getHeight()) / mBitmap.getHeight();
        // TODO improve scaling by looking at pose and euler y
        PointF midPoint = new PointF();
        face.getMidPoint(midPoint);//w ww .  j a va2 s . c  o m
        int x = (int) (midPoint.x - (face.eyesDistance()));
        int y = (int) (midPoint.y - (face.eyesDistance()));
        int width = x + (int) (face.eyesDistance() * 2);
        int height = y + (int) (face.eyesDistance() * 2 * GOLDEN_RATIO);
        return new Rect(x, y, width, height);
    }
    
    
    
}
