package com.salieri.drawengine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.salieri.baselib.core.EngineHolder;

@Deprecated
public class CanvasView extends View implements ICanvas{
    private Canvas canvas;
    private Bitmap bitmap;
    private Bitmap turtleBmp;
    private Canvas turtleCanvas;
    private Paint paint;
    private Paint turtlePaint;
    private SubsamplingScaleImageView imageView;
    private boolean init = false;
    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private int viewWidth, viewHeight;

    public void setImageView(SubsamplingScaleImageView imageView) {
        this.imageView = imageView;
    }

    //大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //宽
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        //高
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (measureWidth != 0 && measureHeight != 0) {
            viewWidth = measureWidth;
            viewHeight = measureHeight;
            initCanvas();
        }
    }

    private void initCanvas() {
        if (init) return;
        init = true;
        paint = new Paint(Paint.DITHER_FLAG);//创建一个画笔
        bitmap = Bitmap.createBitmap(3000, 3000, Bitmap.Config.ARGB_8888);//设置位图的宽高
        canvas = new Canvas(bitmap);
        paint.setStyle(Paint.Style.STROKE);//设置非填充
        paint.setStrokeWidth(5);//笔宽5像素
        paint.setColor(Color.RED);//设置为红笔
        paint.setAntiAlias(true);//锯齿不显示
        paint.setDither(true);//设置图像抖动处理
        paint.setStrokeJoin(Paint.Join.ROUND);//设置图像的结合方式
        paint.setStrokeCap(Paint.Cap.ROUND);//设置画笔为圆形样式

        turtlePaint = new Paint(Paint.DITHER_FLAG);
        turtlePaint.setStyle(Paint.Style.FILL);//设置非填充
        turtlePaint.setStrokeWidth(3);//笔宽5像素
        turtlePaint.setColor(Color.WHITE);//设置为红笔

        turtleBmp = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);//设置位图的宽高
        turtleCanvas = new Canvas(turtleBmp);


        EngineHolder.get().setEngine(new AndroidEngine(this));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (bitmap != null) canvas.drawBitmap(bitmap, 0, 0, paint);
//        if (turtleBmp != null) canvas.drawBitmap(turtleBmp, 0, 0, paint);
    }

    @Override
    public void drawLine(float fromX, float fromY, float toX, float toY) {
        canvas.drawLine(fromX, fromY, toX, toY, paint);
        invalidate();
    }

    @Override
    public void drawTurtle(float x, float y, float angle) {
        float length = 20;
        float height = 40;
        float x1 = (float) (x - Math.cos(angle) * length / 2f);
        float y1 = (float) (y - Math.sin(angle) * length / 2f);

        float x2 = (float) (x + Math.cos(angle) * length / 2f);
        float y2 = (float) (y + Math.sin(angle) * length / 2f);

        float x3 = (float) (x + Math.sin(angle) * height);
        float y3 = (float) (y - Math.cos(angle) * height);

        turtleCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        turtleCanvas.drawLine(x1, y1, x2, y2, turtlePaint);
        turtleCanvas.drawLine(x1, y1, x3, y3, turtlePaint);
        turtleCanvas.drawLine(x2, y2, x3, y3, turtlePaint);

        Bitmap bp = Bitmap.createBitmap(3000, 3000, Bitmap.Config.ARGB_4444);//设置位图的宽高
        Canvas cv = new Canvas(bp);
        cv.drawBitmap(bitmap, 0, 0, paint);
        if (imageView != null && bitmap != null) imageView.setImage(ImageSource.bitmap(bp));

    }

    @Override
    public float getDefaultX() {
        return 0;
    }

    @Override
    public float getDefaultY() {
        return 0;
    }

    @Override
    public float getDefaultAngle() {
        return 0;
    }

}
