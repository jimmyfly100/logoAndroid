package com.salieri.logo.workspace;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salieri.baselib.type.NUM;
import com.salieri.logo.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class VariableAdapter extends RecyclerView.Adapter<VariableAdapter.ViewHolder> {
    private List<FieldVariableItem> itemList = new LinkedList<>();

    public void refresh(Map<String, Map<String, NUM>> map) {
        itemList.clear();
        for (Map.Entry<String, Map<String, NUM>> entry : map.entrySet()) {
            itemList.add(new FieldVariableItem(entry.getKey(), entry.getValue()));
        }
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.variable_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final FieldVariableItem item = itemList.get(position);
        holder.fieldTv.setText(item.field);
        StringBuilder text = new StringBuilder();
        for (Map.Entry<String, NUM> entry : item.varMap.entrySet()) {
            String tmp = entry.getKey() + " = " + entry.getValue().value;
            text.append(tmp).append("  ");
        }
        holder.variableTv.setText(text.toString());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView fieldTv;
        public TextView variableTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fieldTv = itemView.findViewById(R.id.field_tv);
            variableTv = itemView.findViewById(R.id.variable_tv);
        }
    }

    public static class FieldVariableItem {
        public String field = "";
        public Map<String, NUM> varMap = new HashMap<>();

        public FieldVariableItem() {
        }

        public FieldVariableItem(String field, Map<String, NUM> varMap) {
            this.field = field;
            this.varMap = varMap;
        }
    }
}
