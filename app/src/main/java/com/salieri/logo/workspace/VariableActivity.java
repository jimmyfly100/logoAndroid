package com.salieri.logo.workspace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.salieri.baselib.core.CoreManager;
import com.salieri.logo.R;

public class VariableActivity extends AppCompatActivity {
    private RecyclerView variableRv;
    private VariableAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.variable_activity);
        variableRv = findViewById(R.id.variable_rv);
        variableRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VariableAdapter();
        variableRv.setAdapter(adapter);
        adapter.refresh(CoreManager.getInstance().getFuncVarMap());

        findViewById(R.id.back_iv).setOnClickListener(v -> {
            finish();
        });
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, VariableActivity.class);
        context.startActivity(intent);
    }
}
