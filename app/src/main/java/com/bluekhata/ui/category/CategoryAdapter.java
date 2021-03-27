package com.bluekhata.ui.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bluekhata.R;
import com.bluekhata.data.model.db.Category;
import com.bluekhata.data.model.db.custom.TransactionWithCategory;
import com.bluekhata.databinding.ItemCategoryBinding;
import com.bluekhata.databinding.ItemHomeTransactionBinding;
import com.bluekhata.ui.base.BaseViewHolder;
import com.bluekhata.ui.dashboard.category.CategoryItemViewModel;
import com.bluekhata.ui.dashboard.home.HomeAdapter;
import com.bluekhata.ui.dashboard.home.HomeItemViewModel;
import com.bluekhata.ui.dashboard.home.homedetails.HomeDetailFragment;
import com.bluekhata.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> list;
    private FragmentActivity context;

    public CategoryAdapter(FragmentActivity context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding itemCategoryBinding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void refreshList(List<Category> categories){
        this.list = categories;
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseViewHolder implements View.OnClickListener {
        private ItemCategoryBinding binding;
        private CategoryItemViewModel viewModel;

        public ViewHolder(ItemCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            itemView.getRoot().setOnClickListener(this);
        }

        @Override
        public void onBind(int position) {
            Category category = list.get(position);
            viewModel = new CategoryItemViewModel(category);

            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
//            long categoryId = list.get(getAdapterPosition()).getTransaction().getCategoryId();
//            HomeDetailFragment homeDetailFragment = HomeDetailFragment.getInstance(selectedStartDate, selectedEndDate, categoryId);
//            AppUtils.setFragment(homeDetailFragment, true, context, R.id.container, HomeDetailFragment.TAG);

        }
    }
}
