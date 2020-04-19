package com.bluekhata.ui.dashboard.category;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bluekhata.data.model.db.Category;
import com.bluekhata.databinding.ItemCategoriesBinding;
import com.bluekhata.ui.addcategory.AddCategoryActivity;
import com.bluekhata.ui.base.BaseViewHolder;
import com.bluekhata.utils.recyclerviewdrager.ItemTouchHelperAdapter;
import com.bluekhata.utils.recyclerviewdrager.ItemTouchHelperViewHolder;
import com.bluekhata.utils.recyclerviewdrager.OnCustomerListChangedListener;
import com.bluekhata.utils.recyclerviewdrager.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by aman on 05-08-2018.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    public static final int EXPENSE_CATEGORY = 0;
    public static final int INCOME_CATEGORY = 1;

    private int tag;
    private Context context;
    private ArrayList<Category> list;
    private OnStartDragListener mDragStartListener;
    private OnCustomerListChangedListener mListChangedListener;

    public CategoryListAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ItemCategoriesBinding itemCategoriesBinding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemCategoriesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryListAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
        holder.dragger.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
//        Collections.swap(list, fromPosition, toPosition);
//        mListChangedListener.onCategoryListChanged(list);

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(list, i, i + 1);

                int order1 = list.get(i).getCatShortIndex();
                int order2 = list.get(i + 1).getCatShortIndex();
                list.get(i).setCatShortIndex(order2);
                list.get(i + 1).setCatShortIndex(order1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(list, i, i - 1);

                int order1 = list.get(i).getCatShortIndex();
                int order2 = list.get(i - 1).getCatShortIndex();
                list.get(i).setCatShortIndex(order2);
                list.get(i - 1).setCatShortIndex(order1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemMoveEnd(int fromPosition, int toPosition) {
//        mListChangedListener.onCategoryMove(fromPosition, toPosition, list.get(toPosition).getCatId());
        mListChangedListener.onCategoryListUpdate(list);
    }

    @Override
    public void onItemDismiss(int position) {

    }

    public void setListChangedListener(OnCustomerListChangedListener listChangedListener) {
        this.mListChangedListener = listChangedListener;
    }

    public void setDragStartListener(OnStartDragListener dragStartListener) {
        this.mDragStartListener = dragStartListener;
    }

    public void setList(List<Category> list) {
        if (list == null) {
            return;
        }
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public class ViewHolder extends BaseViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {
        private AppCompatImageView dragger;

        private CategoryItemViewModel viewModel;
        private ItemCategoriesBinding categoriesBinding;

        public ViewHolder(ItemCategoriesBinding categoriesBinding) {
            super(categoriesBinding.getRoot());
            this.categoriesBinding = categoriesBinding;
            dragger = categoriesBinding.imgDragger;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onBind(int position) {
            Category category = list.get(position);
            viewModel = new CategoryItemViewModel(category);
            categoriesBinding.setViewModel(viewModel);

            categoriesBinding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            String categoryType = tag == EXPENSE_CATEGORY ? "Expense" : "Income";
            context.startActivity(AddCategoryActivity.newIntent(
                    context, categoryType, list.get(getAdapterPosition()).getCatId()));
        }

        @Override
        public void onItemSelected() {

        }

        @Override
        public void onItemClear() {

        }
    }
}

