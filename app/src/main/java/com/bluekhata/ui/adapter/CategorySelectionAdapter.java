package com.bluekhata.ui.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluekhata.data.model.db.Category;
import com.bluekhata.ui.base.BaseViewHolder;
import com.bluekhata.ui.dashboard.category.CategoryItemViewModel;
import com.bluekhata.databinding.ItemCategoryBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aman on 05-08-2018.
 */

public class CategorySelectionAdapter extends RecyclerView.Adapter<CategorySelectionAdapter.ViewHolder> {
    private ArrayList<Category> list;
    private OnItemClickListener listener;

    public CategorySelectionAdapter(OnItemClickListener listener) {
        this.list = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategorySelectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding itemCategoryBinding = ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new CategorySelectionAdapter.ViewHolder(itemCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorySelectionAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void updateList(List<Category> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        public void onItemClick(Category category);
    }

    public class ViewHolder extends BaseViewHolder implements View.OnClickListener {
        private CategoryItemViewModel viewModel;
        private ItemCategoryBinding itemCategoryBinding;

        public ViewHolder(ItemCategoryBinding itemView) {
            super(itemView.getRoot());
            itemCategoryBinding = itemView;

            itemView.getRoot().setOnClickListener(this);
        }

        @Override
        public void onBind(int position) {
            Category category = list.get(position);
            viewModel = new CategoryItemViewModel(category);
            itemCategoryBinding.setViewModel(viewModel);

            itemCategoryBinding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(list.get(getAdapterPosition()));
        }
    }
}

