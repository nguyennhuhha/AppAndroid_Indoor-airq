package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.MapFragment;
import com.example.myapplication.Model.Attribute;
import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.google.gson.JsonElement;

import java.util.List;
import java.util.stream.Collectors;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.AttrsViewHolder> {
    private final List<Attribute> attributes;
    private final List<String> attributeNames;
    private final List<JsonElement> attributeValues;
    public BottomSheetAdapter(List<Attribute> attributes) {
        this.attributes = attributes;
        this.attributeNames = attributes.stream().map(attribute -> attribute.name).collect(Collectors.toList());
        this.attributeValues = attributes.stream().map(attribute -> attribute.value).collect(Collectors.toList());
    }

    @NonNull
    @Override
    public AttrsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_attributes, parent, false);
        return new AttrsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttrsViewHolder holder, int position) {
        String label;
        if (attributes.get(position).meta != null && attributes.get(position).meta.get("label") != null) {
            label = attributes.get(position).meta.get("label").getAsString();
        } else {
            label = attributeNames.get(position);
        }

        String value;
        if (attributeValues.get(position).isJsonNull()) value = "-";
        else value = attributeValues.get(position).getAsString();

        holder.tvAttr.setText(Utils.formatString(label));
        holder.tvValue.setText(value);
    }

    @Override
    public int getItemCount() {
        return attributes == null ? 0 : attributes.size();
    }

    static class AttrsViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAttr;
        private final TextView tvValue;
        public AttrsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAttr = itemView.findViewById(R.id.tv_Attr);
            tvValue = itemView.findViewById(R.id.tv_Value);
        }
    }
}
