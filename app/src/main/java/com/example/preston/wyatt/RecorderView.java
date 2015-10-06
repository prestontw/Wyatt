package com.example.preston.wyatt;
        import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.util.AttributeSet;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.Toast;

/**
 * Created by preston on 10/6/15.
 */
public class RecorderView extends View{


/**
 * Created by preston on 9/18/14.
 */
    private Paint paintBg;
    //visually represent whether we are recording

    public RecorderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBg = new Paint();
        paintBg.setStyle(Paint.Style.FILL);
        paintBg.setColor(Color.BLACK);
    }

    public void setDrawingColor(boolean recording){
        if (recording)
            paintBg.setColor(Color.RED);
        else
            paintBg.setColor(Color.BLACK);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setDrawingColor(RecordingModel.getInstance().isRecording());

        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);

    }

    //if touch, change recording state
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            RecordingModel.getInstance().changeState();
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }
}
