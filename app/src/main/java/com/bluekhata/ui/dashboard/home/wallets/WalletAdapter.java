package com.bluekhata.ui.dashboard.home.wallets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bluekhata.R;
import com.bluekhata.data.model.db.Wallet;
import com.bluekhata.databinding.ItemHistoryFilterBinding;
import com.bluekhata.utils.AppUtils;
import com.bluekhata.utils.TextDrawable;

import java.util.ArrayList;
import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {
    private int selectedPosition = -1;
    private IWalletChangeListener clickListener;
    private List<Wallet> wallets;

    public WalletAdapter() {
        wallets = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryFilterBinding view = ItemHistoryFilterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.title.setText(wallets.get(position).getTitle());
        holder.checkBox.setChecked(position == selectedPosition);
        holder.imgLogo.setVisibility(position >= 2 ? View.VISIBLE : View.INVISIBLE);

        ShapeDrawable bitmapDrawable = AppUtils.getDrawableBitmap(Color.parseColor("#075fcd"));
        holder.imgLogo.setBackground(bitmapDrawable);
        holder.imgLogo.setText("B");

        holder.itemView.setBackgroundColor(getColorFromAttr(
                holder.itemView.getContext(),
                position == selectedPosition ? R.attr.WindowBackGroundColor : R.attr.BackGroundColorWhite
        ));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();

                holder.checkBox.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (clickListener != null) {
                            clickListener.onWalletModeChange(wallets.get(holder.getAdapterPosition()));
                        }
                    }
                }, 250);

            }
        });
    }


    @Override
    public int getItemCount() {
        return wallets == null ? 0 : wallets.size();
    }

    public void refresh(List<Wallet> wallets) {
        this.wallets.clear();
        this.wallets.addAll(wallets);
        notifyDataSetChanged();
    }

    public void setWalletClickListener(IWalletChangeListener iWalletChangeListener) {
        this.clickListener = iWalletChangeListener;
    }

    private int getColorFromAttr(Context context, @AttrRes int attr) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView title;
        AppCompatCheckBox checkBox;
        AppCompatTextView imgLogo;

        public ViewHolder(@NonNull ItemHistoryFilterBinding itemView) {
            super(itemView.getRoot());
            title = itemView.title;
            checkBox = itemView.chk;
            imgLogo = itemView.logo;
        }
    }
}
