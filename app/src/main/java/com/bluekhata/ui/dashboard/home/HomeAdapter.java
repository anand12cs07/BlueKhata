package com.bluekhata.ui.dashboard.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluekhata.R;
import com.bluekhata.data.model.db.custom.TransactionWithCategory;
import com.bluekhata.databinding.ItemHomeMoreBinding;
import com.bluekhata.databinding.ItemHomeTransactionBinding;
import com.bluekhata.ui.base.BaseViewHolder;
import com.bluekhata.ui.dashboard.RefreshListOnDismiss;
import com.bluekhata.ui.dashboard.home.homedetails.HomeDetailFragment;
import com.bluekhata.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aman on 05-08-2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private FragmentActivity context;
    private RefreshListOnDismiss listener;
    private long selectedStartDate, selectedEndDate;
    private ArrayList<TransactionWithCategory> list;

    private static final int ITEM_HOME = 1;
    private static final int ITEM_MORE = 2;
    private static final int ITEMS_COUNT = 5;

    public HomeAdapter(FragmentActivity context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_MORE) {
            ItemHomeMoreBinding bindingMore = ItemHomeMoreBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ButtonViewHolder(bindingMore);
        }
        ItemHomeTransactionBinding binding = ItemHomeTransactionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder)holder).onBind(position);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size() > ITEMS_COUNT ? ITEMS_COUNT + 1 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position >= ITEMS_COUNT ? ITEM_MORE : ITEM_HOME;
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

    public class ButtonViewHolder extends BaseViewHolder implements View.OnClickListener {
        private ItemHomeMoreBinding binding;

        public ButtonViewHolder(ItemHomeMoreBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            itemView.getRoot().setOnClickListener(this);
        }

        @Override
        public void onBind(int position) {
        }

        @Override
        public void onClick(View view) {

        }
    }
}

