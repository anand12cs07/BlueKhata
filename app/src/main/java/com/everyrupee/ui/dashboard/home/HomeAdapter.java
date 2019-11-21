package com.everyrupee.ui.dashboard.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.everyrupee.R;
import com.everyrupee.data.model.db.custom.TransactionWithCategory;
import com.everyrupee.databinding.ItemHomeTransactionBinding;
import com.everyrupee.ui.base.BaseViewHolder;
import com.everyrupee.ui.dashboard.RefreshListOnDismiss;
import com.everyrupee.ui.dashboard.home.homedetails.HomeDetailFragment;
import com.everyrupee.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aman on 05-08-2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private FragmentActivity context;
    private RefreshListOnDismiss listener;
    private long selectedStartDate, selectedEndDate;
    private ArrayList<TransactionWithCategory> list;

    public HomeAdapter(FragmentActivity context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHomeTransactionBinding binding = ItemHomeTransactionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void refreshList(List<TransactionWithCategory> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setSelectedDate(long startDate, long endDate) {
        this.selectedStartDate = startDate;
        this.selectedEndDate = endDate;
    }

    public class ViewHolder extends BaseViewHolder implements View.OnClickListener {
        private ItemHomeTransactionBinding binding;
        private HomeItemViewModel viewModel;

        public ViewHolder(ItemHomeTransactionBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            itemView.getRoot().setOnClickListener(this);
        }

        @Override
        public void onBind(int position) {
            TransactionWithCategory transaction = list.get(position);
            viewModel = new HomeItemViewModel(transaction);

            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            long categoryId = list.get(getAdapterPosition()).getTransaction().getCategoryId();
            HomeDetailFragment homeDetailFragment = HomeDetailFragment.getInstance(selectedStartDate, selectedEndDate, categoryId);
            AppUtils.setFragment(homeDetailFragment,true, context, R.id.container, HomeDetailFragment.TAG);

        }
    }
}

