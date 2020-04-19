package com.bluekhata.ui.addcategory.colors;

import android.content.res.ColorStateList;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluekhata.R;
import com.bluekhata.ui.addcategory.OnIconListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aman on 05-08-2018.
 */

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    private List<Integer> colorList;
    private OnIconListener listener;

    public ColorAdapter(int[] arr, OnIconListener listener) {
        this.listener = listener;
        this.colorList = new ArrayList<>(arr.length);
        for (int i : arr) {
            colorList.add(i);
        }
    }

    @NonNull
    @Override
    public ColorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorAdapter.ViewHolder holder, int position) {
        holder.fab.setBackgroundTintList(ColorStateList.valueOf(colorList.get(position)));
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FloatingActionButton fab;

        public ViewHolder(View itemView) {
            super(itemView);
            fab = (FloatingActionButton) itemView.findViewById(R.id.fab);
            fab.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.getFabColor(colorList.get(getAdapterPosition()));
        }
    }
}

