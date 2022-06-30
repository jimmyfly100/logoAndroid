package com.salieri.drawengine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.salieri.baselib.core.EngineHolder;
import com.salieri.baselib.utils.ThreadUtil;

public class CanvasHelper implements ICanvas{
    private Canvas canvas;
    private Bitmap bitmap;
    private Bitmap turtleBmp;
    private Canvas turtleCanvas;
    private Paint paint;
    private Paint turtlePaint;
    private SubsamplingScaleImageView imageView;
    private boolean init = false;

    private float scale = 1;
    private PointF center = null;

    private static final int HEIGHT = 6000;
    private static final int WIDTH = 3000;

    public CanvasHelper(SubsamplingScaleImageView imageView) {
        this.imageView = imageView;
        initCanvas();
    }

    private void initCanvas() {
        paint = new Paint(Paint.DITHER_FLAG);
        bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint.setStyle(Paint.Style.STROKE);//设置非填充
        paint.setStrokeWidth(5);//笔宽5像素
        paint.setColor(Color.RED);//设置为红笔
        paint.setAntiAlias(true);//锯齿不显示
        paint.setDither(true);//设置图像抖动处理
        paint.setStrokeJoin(Paint.Join.ROUND);//设置图像的结合方式
        paint.setStrokeCap(Paint.Cap.ROUND);//设置画笔为圆形样式

        turtlePaint = new Paint(Paint.DITHER_FLAG);
        turtlePaint.setStyle(Paint.Style.FILL);
        turtlePaint.setStrokeWidth(3);
        turtlePaint.setColor(Color.WHITE);

        turtleBmp = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
        turtleCanvas = new Canvas(turtleBmp);
    }

    @Override
    public void drawLine(float fromX, float fromY, float toX, float toY) {
        canvas.drawLine(fromX, fromY, toX, toY, paint);
    }

    @Override
    public void drawTurtle(float x, float y, float angle) {
        float length = 20;
        float height = 40;

        //三角形底部中心作为当前坐标
//        float x1 = (float) (x - Math.cos(angle) * length / 2f);
//        float y1 = (float) (y - Math.sin(angle) * length / 2f);
//
//        float x2 = (float) (x + Math.cos(angle) * length / 2f);
//        float y2 = (float) (y + Math.sin(angle) * length / 2f);
//
//        float x3 = (float) (x + Math.sin(angle) * height);
//        float y3 = (float) (y - Math.cos(angle) * height);


        //三角形中心作为当前坐标
        float x1 = (float) (x - Math.cos(angle) * length / 2f - Math.sin(angle) * height / 2f);
        float y1 = (float) (y - Math.sin(angle) * length / 2f + Math.cos(angle) * height / 2f);

        float x2 = (float) (x + Math.cos(angle) * length / 2f - Math.sin(angle) * height / 2f);
        float y2 = (float) (y + Math.sin(angle) * length / 2f + Math.cos(angle) * height / 2f);

        float x3 = (float) (x + Math.sin(angle) * height / 2f);
        float y3 = (float) (y - Math.cos(angle) * height / 2f);

        turtleCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        turtleCanvas.drawLine(x1, y1, x2, y2, turtlePaint);
        turtleCanvas.drawLine(x1, y1, x3, y3, turtlePaint);
        turtleCanvas.drawLine(x2, y2, x3, y3, turtlePaint);
        draw();

    }

    @Override
    public float getDefaultX() {
        return WIDTH / 2f;
    }

    @Override
    public float getDefaultY() {
        return HEIGHT / 2f;
    }

    @Override
    public float getDefaultAngle() {
        return 0;
    }

    public void draw() {
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scale = imageView.getScale();
                center = imageView.getCenter();
                if (imageView == null || bitmap == null || turtleBmp == null) return;
                Bitmap bp = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_4444);//设置位图的宽高
                Canvas cv = new Canvas(bp);
                cv.drawBitmap(bitmap, 0, 0, paint);
                cv.drawBitmap(turtleBmp, 0, 0, paint);
                imageView.setImage(ImageSource.bitmap(bp));
                if (!init) {
                    init = true;
                    Log.d("salieriiii", "init");
                    imageView.setScaleAndCenter(1, new PointF(WIDTH / 2f, HEIGHT / 2f));
                } else {
                    imageView.setScaleAndCenter(scale, center);
                }
            }
        });
    }

}
