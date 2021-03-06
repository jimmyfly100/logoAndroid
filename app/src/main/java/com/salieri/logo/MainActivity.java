package com.salieri.logo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.salieri.baselib.core.CoreManager;
import com.salieri.baselib.core.EngineHolder;
import com.salieri.baselib.task.MAIN;
import com.salieri.baselib.type.CODE;
import com.salieri.drawengine.AndroidEngine;
import com.salieri.drawengine.CanvasHelper;
import com.salieri.logo.workspace.MenuActivity;

public class MainActivity extends AppCompatActivity {
//    private CanvasView canvasView;
    private SubsamplingScaleImageView imageView;
    private EditText codeTv;
    private static final String INTENT_CODE = "CODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CoreManager.getInstance().init();
//        canvasView = findViewById(R.id.canvas_view);
        imageView = findViewById(R.id.image);
        codeTv = findViewById(R.id.code_tv);
        String text = "FUNC Polygon length num [REPEAT num[FD length RT 360/num]] Polygon 100 8";
        String text2 = "BK -100 To AAAAA :A :B :C FD A RT B BK C/20*50 End TO SALIERI :A :B :C REPEAT C[fd A rt B+60] END a=1 b=0 REPEAT 3 [SALIERI a b-59 360 FD 200] AAAAA a + 100 b + 60 200";
        String text3 = "to STAR :length REPEAT 5[test 100 fd length RT 144] END TO test :length REPEAT 4[FD length RT 90] END STAR 500";
        String text4 = "TO flower :num repeat 8 [rt 45 repeat num [repeat 90 [fd 2 rt 2] rt 90]] end i = 1 bk 1000 repeat 7[flower i pu fd 300 i=i+1 pd]";
        String text5 = "a = 1 repeat 1800 [fd 10 rt a+1 a=a+1]";

        String text6 = "length= -(1+4)*100 TO STAR :length REPEAT 5[test 50-100 fd (length+100)*0.6 RT 144] END TO test :length REPEAT 4[FD length RT 90] END STAR length";
        String text7 = "repeat 10000[fd 1 rt 1 fd 1 rt 1 fd 1 rt 1]";
        String text8 = "px i=0 repeat 420 [seth i repeat i [fd 2 rt 1] SETXY 0 0 i = i+1]";
        String text9 = "px repeat 10000 [fd 200 rt 179]";
        String text10 = "x=1 repeat 1000[fd x rt 90 x=x+1]";
        codeTv.setText(text3);
        EngineHolder.get().setEngine(new AndroidEngine(new CanvasHelper(imageView)));

        findViewById(R.id.button).setOnClickListener(v -> {
            CODE code = new CODE(codeTv.getText().toString());
            MAIN main = new MAIN(code);
            main.run();
        });

        findViewById(R.id.save_bt).setOnClickListener(v -> {
            EngineHolder.getEngine().saveAllFunc();
        });

        findViewById(R.id.load_bt).setOnClickListener(v -> {
            EngineHolder.getEngine().loadAllFunc();
        });

        findViewById(R.id.workspace_bt).setOnClickListener(v -> {
            MenuActivity.go(this);
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String code = intent.getStringExtra(INTENT_CODE);
        if (!TextUtils.isEmpty(code)) {
            codeTv.setText(code);
        }
    }

    public static void goWithCode(Context context, String code) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(INTENT_CODE, code);
        context.startActivity(intent);
    }
}