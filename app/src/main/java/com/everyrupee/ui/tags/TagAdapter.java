package com.everyrupee.ui.tags;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.everyrupee.data.model.db.Tag;
import com.everyrupee.ui.base.BaseViewHolder;
import com.everyrupee.R;
import com.everyrupee.databinding.ItemTagBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aman on 05-08-2018.
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private TagActivity context;
    private TagItemListener listener;
    private int oldEditablePosition = -1;
    private int editablePosition = -1;
    private ArrayList<Tag> list;

    public TagAdapter(TagActivity context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public TagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTagBinding itemTagBinding = DataBindingUtil.bind(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false));
        return new ViewHolder(itemTagBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TagAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setList(List<Tag> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addTag(Tag tag) {
        this.list.add(0, tag);
        notifyItemInserted(0);
    }

    public void resetEdit() {
        oldEditablePosition = editablePosition;
        editablePosition = -1;
        notifyItemChanged(oldEditablePosition);
    }

    public void setListener(TagItemListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends BaseViewHolder implements View.OnClickListener, View.OnFocusChangeListener {
        private TagItemViewModel viewModel;
        private ItemTagBinding itemTagBinding;

        public ViewHolder(ItemTagBinding itemView) {
            super(itemView.getRoot());
            itemTagBinding = itemView;

            itemTagBinding.tvTag.setOnFocusChangeListener(this);
            itemTagBinding.okTag.setOnClickListener(this);
            itemTagBinding.deleteTag.setOnClickListener(this);
        }

        @Override
        public void onBind(int position) {
            Tag tag = list.get(position);
            viewModel = new TagItemViewModel(tag);

            itemTagBinding.setViewModel(viewModel);

            viewModel.setIsTagEditable(oldEditablePosition != editablePosition
                    ? position == editablePosition
                    : viewModel.getIsTagEditable1().get());

            setUpFocus();
            itemTagBinding.executePendingBindings();

        }

        @Override
        public void onClick(View view) {
            if (listener == null) {
                Log.e("Tag Adapter", "TagItemListener can't be null");
                return;
            }
            switch (view.getId()) {
                case R.id.delete_tag:
                    listener.onDelete(list.get(getAdapterPosition()));
                    break;
                case R.id.ok_tag:
                    setUpPosition(getAdapterPosition());
                    if (viewModel.getIsTagEditable().get()) {
                        Tag tag = list.get(getAdapterPosition());
                        Tag newTag = new Tag(tag.getTagId(),viewModel.getTagTitle().get());
                        listener.onEdit(newTag);
                    }
                    break;
            }
        }

        @Override
        public void onFocusChange(View view, boolean b) {
            viewModel.setIsTagEditable1(!viewModel.getIsTagEditable1().get());
            viewModel.setIsTagEditable(viewModel.getIsTagEditable1().get());
        }

        private void setUpPosition(int position) {
            oldEditablePosition = oldEditablePosition == editablePosition ? -1 : editablePosition;
            editablePosition = position;
            viewModel.onEditClick();
            notifyItemChanged(oldEditablePosition);
            notifyItemChanged(editablePosition);
        }

        private void setUpFocus() {
            final InputMethodManager inputMethodManager = (InputMethodManager) itemTagBinding.tvTag.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            if (viewModel.getIsTagEditable().get()) {
                itemTagBinding.tvTag.post(new Runnable() {
                    @Override
                    public void run() {
                        if (itemTagBinding.tvTag.requestFocus()) {
                            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                            inputMethodManager.showSoftInput(itemTagBinding.tvTag, InputMethodManager.SHOW_IMPLICIT);
                        }
                    }
                });
            } else {
                itemTagBinding.tvTag.post(new Runnable() {
                    @Override
                    public void run() {
                        itemTagBinding.tvTag.clearFocus();
                        inputMethodManager.hideSoftInputFromWindow(itemTagBinding.tvTag.getWindowToken(), 0);
                    }
                });
            }

        }
    }
}

