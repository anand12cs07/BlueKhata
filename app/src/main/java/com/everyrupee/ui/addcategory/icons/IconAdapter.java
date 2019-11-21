package com.everyrupee.ui.addcategory.icons;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.everyrupee.ui.addcategory.OnIconListener;
import com.everyrupee.R;

/**
 * Created by aman on 05-08-2018.
 */

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ViewHolder> {
    private TypedArray iconList;
    private OnIconListener listener;
    private Context context;

    public IconAdapter(TypedArray iconList, OnIconListener listener){
        this.listener = listener;
        this.iconList = iconList;
    }

    @NonNull
    @Override
    public IconAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_color,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IconAdapter.ViewHolder holder, int position) {
        holder.fab.setImageDrawable(AppCompatResources.getDrawable(context, iconList.getResourceId(position,0)));
    }

    @Override
    public int getItemCount() {
        return iconList.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private FloatingActionButton fab;
        public ViewHolder(View itemView) {
            super(itemView);
            fab = (FloatingActionButton)itemView.findViewById(R.id.fab);
            fab.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.getFabDrawable(iconList.getResourceId(getAdapterPosition(),0));
        }
    }
}

