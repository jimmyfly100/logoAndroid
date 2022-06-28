package com.salieri.logo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.salieri.baselib.core.CoreManager;
import com.salieri.baselib.core.EngineHolder;
import com.salieri.baselib.task.TaskSet;
import com.salieri.baselib.task.logotask.FUNC;
import com.salieri.baselib.task.logotask.MAIN;
import com.salieri.baselib.type.CODE;
import com.salieri.baselib.type.NAME;
import com.salieri.drawengine.AndroidEngine;
import com.salieri.drawengine.CanvasHelper;
import com.salieri.drawengine.CanvasView;
import com.salieri.logo.workspace.WorkspaceActivity;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    private CanvasView canvasView;
    private SubsamplingScaleImageView imageView;
    private EditText codeTv;
    private CanvasHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        canvasView = findViewById(R.id.canvas_view);
        imageView = findViewById(R.id.image);
        codeTv = findViewById(R.id.code_tv);
        String text = "FUNC Polygon length num [REPEAT num[FD length RT 360/num]] Polygon 100 8";
        String text2 = "BK -100 To AAAAA :A :B :C FD A RT B BK C/20*50 End TO SALIERI :A :B :C REPEAT C[fd A rt B+60] END a=1 b=0 REPEAT 3 [SALIERI a b-59 360 FD 200] AAAAA a + 100 b + 60 200";
        String text3 = "to STAR :length REPEAT 5[test 100 fd length RT 144] END TO test :length REPEAT 4[FD length RT 90] END STAR 500";
        String text4 = "TO flower :num repeat 8 [rt 45 repeat num [repeat 90 [fd 2 rt 2] rt 90]] end i = 1 bk 1000 repeat 7[flower i fd 300 i=i+1]";
        String text5 = "a = 1 repeat 1800 [fd 10 rt a+1 a=a+1]";

        String text6 = "length= -(1+4)*100 TO STAR :length REPEAT 5[test 50-100 fd (length+100)*0.6 RT 144] END TO test :length REPEAT 4[FD length RT 90] END STAR length";
        String text7 = "repeat 10000[fd 1 rt 1 fd 1 rt 1 fd 1 rt 1]";
        codeTv.setText(text4);
        helper = new CanvasHelper(imageView);

//        String codeStr = "REPEAT C[FD A RT B]";
//        List<NAME> paramList = new LinkedList<>();
//        paramList.add(new NAME("A"));
//        paramList.add(new NAME("B"));
//        paramList.add(new NAME("C"));
//        FUNC.Content content = new FUNC.Content(new CODE(codeStr), paramList);
//        CoreManager.getInstance().registerFunc("SALIERI", content);

//        String codeStr2 = "FD A RT B BK C";
//        List<NAME> paramList2 = new LinkedList<>();
//        paramList2.add(new NAME("A"));
//        paramList2.add(new NAME("B"));
//        paramList2.add(new NAME("C"));
//        FUNC.Content content2 = new FUNC.Content(new CODE(codeStr2), paramList2);
//        CoreManager.getInstance().registerFunc("AAAAA", content2);

        findViewById(R.id.button).setOnClickListener(v -> {
            CoreManager.getInstance().init();
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
            WorkspaceActivity.go(this);
        });

    }
}