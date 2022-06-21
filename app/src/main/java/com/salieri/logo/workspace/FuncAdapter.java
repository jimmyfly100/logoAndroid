package com.salieri.logo.workspace;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salieri.baselib.core.CoreManager;
import com.salieri.baselib.task.logotask.FUNC;
import com.salieri.logo.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FuncAdapter extends RecyclerView.Adapter<FuncAdapter.ViewHolder>{
    private boolean isFromDisk = false;
    private List<FuncItem> itemList = new LinkedList<>();
    private Callback callback;

    public FuncAdapter(boolean isFromDisk, Callback callback) {
        this.isFromDisk = isFromDisk;
        this.callback = callback;
    }

    public void refresh(Map<String, FUNC.Content> map) {
        itemList.clear();
        itemList.addAll(generateList(map));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.func_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final FuncItem item = itemList.get(position);
        holder.button.setVisibility(isFromDisk ? View.VISIBLE : View.GONE);
        holder.nameTv.setText(item.name);
        holder.codeTv.setText(item.content.code.value);
        holder.button.setOnClickListener(v -> {
            CoreManager.getInstance().registerFunc(item.name, item.content);
            if (callback != null) callback.onRefresh();
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name_tv);
            codeTv = itemView.findViewById(R.id.code_tv);
            button = itemView.findViewById(R.id.button);
        }
    }

    static public class FuncItem {
        public String name = "";
        public FUNC.Content content;
    }

    private List<FuncItem> generateList(Map<String, FUNC.Content> map) {
        List<FuncItem> list = new LinkedList<>();
        for (Map.Entry<String, FUNC.Content> entry : map.entrySet()) {
            FuncItem item = new FuncItem();
            item.name = entry.getKey();
            item.content = entry.getValue();
            list.add(item);
        }
        return list;
    }

    public interface Callback {
        void onRefresh();
    }
}
