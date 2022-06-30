package com.salieri.logo.workspace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.salieri.baselib.core.CoreManager;
import com.salieri.baselib.utils.PrefFuncUtil;
import com.salieri.logo.R;

public class FunctionActivity extends AppCompatActivity implements FuncAdapter.Callback {

    private RecyclerView curRv;
    private RecyclerView diskRv;
    private FuncAdapter curAdapter;
    private FuncAdapter diskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workspace_activity);
        curRv = findViewById(R.id.cur_rv);
        diskRv = findViewById(R.id.disk_rv);
        curAdapter = new FuncAdapter(false, this);
        diskAdapter = new FuncAdapter(true, this);
        curRv.setLayoutManager(new LinearLayoutManager(this));
        diskRv.setLayoutManager(new LinearLayoutManager(this));
        curRv.setAdapter(curAdapter);
        diskRv.setAdapter(diskAdapter);

        findViewById(R.id.back_iv).setOnClickListener(v -> {
            finish();
        });
        refresh();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {
        curAdapter.refresh(CoreManager.getInstance().getFuncMap());
        diskAdapter.refresh(PrefFuncUtil.getInstance().getAllFunc()); //todo: use interface instead of call directly
    }

    public static void go(Context context) {
        context.startActivity(new Intent(context, FunctionActivity.class));
    }
}
