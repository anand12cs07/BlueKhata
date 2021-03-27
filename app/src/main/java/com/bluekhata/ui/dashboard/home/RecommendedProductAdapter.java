package com.bluekhata.ui.dashboard.home;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bluekhata.R;
import com.bluekhata.data.model.db.custom.TransactionWithCategory;
import com.bluekhata.data.model.other.Product;
import com.bluekhata.databinding.ItemHomeTransactionBinding;
import com.bluekhata.databinding.ItemRecommendedProductBinding;
import com.bluekhata.ui.base.BaseViewHolder;
import com.bluekhata.ui.category.ProductItemViewModel;
import com.bluekhata.ui.dashboard.home.homedetails.HomeDetailFragment;
import com.bluekhata.utils.AppUtils;
import com.bluekhata.utils.BindingUtils;
import com.bluekhata.utils.TextDrawable;

import java.util.ArrayList;
import java.util.List;

public class RecommendedProductAdapter extends RecyclerView.Adapter<RecommendedProductAdapter.ViewHolder>{
    private List<Product> list;
    private FragmentActivity context;

    public RecommendedProductAdapter(FragmentActivity context){
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecommendedProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecommendedProductBinding binding = ItemRecommendedProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedProductAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void refreshList(List<Product> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseViewHolder implements View.OnClickListener {
        private ItemRecommendedProductBinding binding;
        private ProductItemViewModel viewModel;

        public ViewHolder(ItemRecommendedProductBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            itemView.getRoot().setOnClickListener(this);
        }

        @Override
        public void onBind(int position) {
            Product product = list.get(position);
            viewModel = new ProductItemViewModel();

            binding.setRecommendedProductViewModel(viewModel);
            viewModel.setProduct(product);

            binding.tvMrpAmount.setPaintFlags(binding.tvMrpAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
//            long categoryId = list.get(getAdapterPosition()).getTransaction().getCategoryId();
//            HomeDetailFragment homeDetailFragment = HomeDetailFragment.getInstance(selectedStartDate, selectedEndDate, categoryId);
//            AppUtils.setFragment(homeDetailFragment,true, context, R.id.container, HomeDetailFragment.TAG);

        }
    }
}
