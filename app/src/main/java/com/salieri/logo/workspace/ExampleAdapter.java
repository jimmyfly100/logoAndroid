package com.salieri.logo.workspace;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salieri.logo.R;

import java.util.LinkedList;
import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ViewHolder>{
    private List<ExampleItem> itemList = new LinkedList<>();
    private Callback callback;

    public ExampleAdapter(Callback callback) {
        this.callback = callback;
    }

    public void refresh(List<ExampleItem> list) {
        itemList.clear();
        itemList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.func_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ExampleItem item = itemList.get(position);
        holder.nameTv.setText(item.name);
        holder.codeTv.setText(item.content);
        holder.button.setOnClickListener(v -> {
            if (callback != null) callback.onLoadClick(item.content);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTv;
        public TextView codeTv;
        public TextView button;
        public TextView paramTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name_tv);
            codeTv = itemView.findViewById(R.id.code_tv);
            button = itemView.findViewById(R.id.button);
            paramTv = itemView.findViewById(R.id.param_tv);
        }
    }

    static public class ExampleItem {
        public String name = "";
        public String content = "";

        public ExampleItem(String name, String content) {
            this.name = name;
            this.content = content;
        }
    }


    public interface Callback {
        void onLoadClick(String code);
    }
}
