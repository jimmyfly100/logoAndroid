package com.salieri.drawengine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.salieri.baselib.core.EngineHolder;

public class CanvasView extends View implements ICanvas{
    private Canvas canvas;
    private Bitmap bitmap;
    private Paint paint;
    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private int viewWidth, viewHeight;

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
        paint = new Paint(Paint.DITHER_FLAG);//创建一个画笔

        bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.RGB_565);//设置位图的宽高

        canvas = new Canvas(bitmap);

        paint.setStyle(Paint.Style.STROKE);//设置非填充

        paint.setStrokeWidth(5);//笔宽5像素

        paint.setColor(Color.RED);//设置为红笔

        paint.setAntiAlias(true);//锯齿不显示

        paint.setDither(true);//设置图像抖动处理

        paint.setStrokeJoin(Paint.Join.ROUND);//设置图像的结合方式

        paint.setStrokeCap(Paint.Cap.ROUND);//设置画笔为圆形样式

        EngineHolder.get().setEngine(new AndroidEngine(viewWidth / 2f, viewHeight / 2f, 0, this));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap != null) canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    @Override
    public void drawLine(float fromX, float fromY, float toX, float toY) {
        canvas.drawLine(fromX, fromY, toX, toY, paint);
        invalidate();
    }

}
