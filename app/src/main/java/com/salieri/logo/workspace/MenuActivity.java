package com.salieri.logo.workspace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.salieri.logo.R;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        findViewById(R.id.function_bt).setOnClickListener(v -> {
            FunctionActivity.go(this);
        });

        findViewById(R.id.variable_bt).setOnClickListener(v -> {
            VariableActivity.go(this);
        });

        findViewById(R.id.example_bt).setOnClickListener(v -> {
            ExampleActivity.go(this);
        });
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, MenuActivity.class);
        context.startActivity(intent);
    }
}
