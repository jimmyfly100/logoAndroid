package com.salieri.logo.workspace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.salieri.baselib.core.CoreManager;
import com.salieri.logo.MainActivity;
import com.salieri.logo.R;

import java.util.LinkedList;
import java.util.List;

public class ExampleActivity extends AppCompatActivity implements ExampleAdapter.Callback{
    private RecyclerView exampleRv;
    private ExampleAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_activity);
        exampleRv = findViewById(R.id.example_rv);
        exampleRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExampleAdapter(this);
        exampleRv.setAdapter(adapter);

        findViewById(R.id.back_iv).setOnClickListener(v -> {
            finish();
        });

        init();
    }

    private void init() {
        List<ExampleAdapter.ExampleItem> itemList = new LinkedList<>();

        String star = "REPEAT 5[FD 200 RT 144]";
        itemList.add(new ExampleAdapter.ExampleItem("Star", star));

        String polygon = "TO polygon :length :angles REPEAT num[FD length RT 360/angles] END polygon 200 8";
        itemList.add(new ExampleAdapter.ExampleItem("Polygon", polygon));

        String multiFlowers = "TO flower :num REPEAT 8 [RT 45 REPEAT num [REPEAT 90 [FD 2 RT 2] RT 90]] end i = 1 PU BK 1000 REPEAT 7[flower i PU FD 300 i=i+1 PD]";
        itemList.add(new ExampleAdapter.ExampleItem("MultiFlowers", multiFlowers));

        String chaos = "px i=0 repeat 420 [seth i repeat i [fd 2 rt 1] SETXY 0 0 i = i+1]";
        itemList.add(new ExampleAdapter.ExampleItem("Chaos", chaos));
        adapter.refresh(itemList);
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, ExampleActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onLoadClick(String code) {
        MainActivity.goWithCode(this, code);
    }
}
