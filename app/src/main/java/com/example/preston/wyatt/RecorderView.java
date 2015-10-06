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
public class RecorderView {


/**
 * Created by preston on 9/18/14.
 */
    private Paint paintBg;
    private Paint paintLn;
    private int numLines;
    private int x, y;
    private boolean drawWithRed = false;

    public ViewTicTacToe(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBg = new Paint();
        paintBg.setStyle(Paint.Style.FILL);
        paintBg.setColor(Color.BLACK);

        paintLn = new Paint();
        paintLn.setStyle(Paint.Style.STROKE);
        paintLn.setColor(Color.WHITE);
        paintLn.setStrokeWidth(5);

        numLines = 4;
    }

    public void setDrawingColor(boolean toRed){
        drawWithRed = toRed;
        if(drawWithRed)
            paintLn.setColor(Color.RED);
        else
            paintLn.setColor(Color.WHITE);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);

        //drawLines(canvas);
        drawGameArea(canvas);


        drawPlayer(canvas);
    }

    private void drawLines(Canvas canvas){
        for(int i = 0; i < numLines; i ++){
            canvas.drawLine(i * canvas.getWidth() / numLines, 0, i * canvas.getWidth() / numLines, canvas.getHeight(), paintLn);
            canvas.drawLine(0, i * canvas.getHeight() / numLines, canvas.getWidth(), i * canvas.getHeight() / numLines,  paintLn);
        }
        canvas.drawLine(0, 0, getWidth(), 20, paintLn);
    }

    private void drawGameArea(Canvas canvas) {
        // border
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLn);
        // two horizontal lines
        canvas.drawLine(0, getHeight() / 3, getWidth(), getHeight() / 3,
                paintLn);
        canvas.drawLine(0, 2 * getHeight() / 3, getWidth(),
                2 * getHeight() / 3, paintLn);

        // two vertical lines
        canvas.drawLine(getWidth() / 3, 0, getWidth() / 3, getHeight(),
                paintLn);
        canvas.drawLine(2 * getWidth() / 3, 0, 2 * getWidth() / 3, getHeight(),
                paintLn);
    }

    private void drawPlayer(Canvas canvas) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (TicTacToeModel.getInstance().getFieldContent(i,j) == TicTacToeModel.CIRCLE) {

                    // draw a circle at the center of the field

                    // X coordinate: left side of the square + half width of the square
                    float centerX = i * getWidth() / 3 + getWidth() / 6;
                    float centerY = j * getHeight() / 3 + getHeight() / 6;
                    int radius = getHeight() / 6 - 2;

                    canvas.drawCircle(centerX, centerY, radius, paintLn);

                } else if (TicTacToeModel.getInstance().getFieldContent(i,j) == TicTacToeModel.CROSS) {
                    canvas.drawLine(i * getWidth() / 3, j * getHeight() / 3,
                            (i + 1) * getWidth() / 3,
                            (j + 1) * getHeight() / 3, paintLn);

                    canvas.drawLine((i + 1) * getWidth() / 3, j * getHeight() / 3,
                            i * getWidth() / 3, (j + 1) * getHeight() / 3, paintLn);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            int tX = ((int) event.getX()) / (getWidth() / 3);
            int tY = ((int) event.getY()) / (getHeight() / 3);

            if (tX < 3 && tY < 3 && TicTacToeModel.getInstance().getFieldContent(tX,tY) == TicTacToeModel.EMPTY) {
                TicTacToeModel.getInstance().setFieldContent(tX,tY,TicTacToeModel.getInstance().getNextPlayer());
                TicTacToeModel.getInstance().changeNextPlayer();
                invalidate();

                if(TicTacToeModel.getInstance().getWinner()){
                    Toast.makeText(getContext(),"Winner, please press to restart the game!",Toast.LENGTH_LONG).show();
                    TicTacToeModel.getInstance().resetModel();
                    //wait for 2 seconds
                    //invalidate();
                }
            }
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
