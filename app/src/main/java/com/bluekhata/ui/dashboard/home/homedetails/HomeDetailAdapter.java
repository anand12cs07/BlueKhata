package com.bluekhata.ui.dashboard.home.homedetails;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bluekhata.data.model.db.custom.TransactionWithTag;
import com.bluekhata.databinding.ItemTransactionDetailBinding;
import com.bluekhata.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aman on 05-08-2018.
 */

public class HomeDetailAdapter extends RecyclerView.Adapter<HomeDetailAdapter.ViewHolder> {

    private List<TransactionWithTag> list;
    public HomeDetailAdapter(){
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public HomeDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTransactionDetailBinding view = ItemTransactionDetailBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeDetailAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void refreshList(List<TransactionWithTag> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<TransactionWithTag> getList(){
        return list;
    }

    public void deleteTransaction(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends BaseViewHolder {
        private ItemTransactionDetailBinding binding;
        private HomeDetailItemViewModel viewModel;
        public ViewHolder(ItemTransactionDetailBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }

        @Override
        public void onBind(int position) {
            TransactionWithTag item = list.get(position);
            viewModel = new HomeDetailItemViewModel(item);
            binding.setViewModel(viewModel);

            binding.executePendingBindings();
        }
    }
}

