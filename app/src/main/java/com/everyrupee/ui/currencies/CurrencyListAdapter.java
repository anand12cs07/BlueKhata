package com.everyrupee.ui.currencies;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.everyrupee.R;
import com.everyrupee.data.model.other.CurrencyModel;

import java.util.ArrayList;

/**
 * Created by aman on 05-08-2018.
 */

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.ViewHolder> {
    private OnItemClickListener listener;
    private ArrayList<CurrencyModel> list;

    public CurrencyListAdapter(){
        this.list = new CurrencyModel().getAllCurrencies();
    }

    @NonNull
    @Override
    public CurrencyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyListAdapter.ViewHolder holder, int position) {
        CurrencyModel item = list.get(position);
        holder.tvTitle.setText(item.getCurrencySymbol());
        holder.tvCurrencySymbol.setText(item.getCurrencyName());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        public void onItemClick(String currencyCode);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        AppCompatTextView tvCurrencySymbol;
        AppCompatRadioButton tvTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (AppCompatRadioButton) itemView.findViewById(R.id.tv_currency_name);
            tvCurrencySymbol = (AppCompatTextView)itemView.findViewById(R.id.tv_currency_symbol);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(list.get(getAdapterPosition()).getCurrencyName());
        }
    }
}

