package com.everyrupee.ui.dashboard.history.filter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.everyrupee.data.model.db.Category;
import com.everyrupee.ui.dashboard.home.homedetails.HomeDetailItemViewModel;
import com.everyrupee.databinding.ItemHistoryFilterBinding;
import com.everyrupee.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aman on 05-08-2018.
 */

public class HistoryFilterAdapter extends RecyclerView.Adapter<HistoryFilterAdapter.ViewHolder> {

    private Context context;
    private List<Category> list;
    private SparseBooleanArray sparseBooleanArray;
    private HistoryFilterListener.OnAllCheckedListener listener;

    public HistoryFilterAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        sparseBooleanArray = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public HistoryFilterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryFilterBinding view = ItemHistoryFilterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryFilterAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void refreshList(List<Category> list, long[] categoryId, boolean isCleared) {
        this.list.clear();
        this.list.addAll(list);
        sparseBooleanArray.clear();

        if (!isCleared) {
            List<Long> longList = new ArrayList<>();
            for (int i = 0; i < categoryId.length; i++) {
                longList.add(categoryId[i]);
            }

            for (int i = 0; i < list.size(); i++) {
                boolean isSelected = longList.contains(this.list.get(i).getCatId());
                sparseBooleanArray.put(i, categoryId.length == 0 || isSelected);
            }
        }else {
            for (int i = 0; i < list.size(); i++) {
                sparseBooleanArray.put(i, true);
            }
        }
        notifyDataSetChanged();
    }

    public long[] getCategoryIdList() {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (sparseBooleanArray.get(i)) {
                ids.add(list.get(i).getCatId());
            }
        }
        long[] id = new long[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            id[i] = ids.get(i);
        }
        return id;
    }

    public void setAllCategory(boolean isSelected) {
        for (int i = 0; i < list.size(); i++) {
            sparseBooleanArray.put(i, isSelected);
        }
        notifyDataSetChanged();
    }

    public void setAllCheckedListener(HistoryFilterListener.OnAllCheckedListener listener){
        this.listener  = listener;
    }

    public class ViewHolder extends BaseViewHolder implements View.OnClickListener {
        private ItemHistoryFilterBinding binding;
        private HomeDetailItemViewModel viewModel;

        public ViewHolder(ItemHistoryFilterBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @Override
        public void onBind(int position) {
            binding.title.setText(list.get(position).getCatTitle());
            binding.chk.setChecked(sparseBooleanArray.get(position));
            binding.chk.setOnClickListener(this);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            sparseBooleanArray.put(getAdapterPosition(), !sparseBooleanArray.get(getAdapterPosition()));
            listener.onAllCheckedItem(isAllCategorySelected());
        }

        private boolean isAllCategorySelected() {
            int count = 0;
            for (int i = 0; i < list.size(); i++) {
                if (sparseBooleanArray.get(i)) {
                    count++;
                }
            }
            return count == list.size() ;
        }
    }
}

