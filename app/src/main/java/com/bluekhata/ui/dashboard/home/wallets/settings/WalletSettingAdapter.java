package com.bluekhata.ui.dashboard.home.wallets.settings;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bluekhata.R;
import com.bluekhata.data.model.db.Wallet;
import com.bluekhata.databinding.ItemWalletSettingBinding;
import com.bluekhata.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aman on 05-08-2018.
 */

public class WalletSettingAdapter extends RecyclerView.Adapter<WalletSettingAdapter.ViewHolder> {

    private WalletSettingActivity context;
    private WalletSettingItemListener listener;
    private int oldEditablePosition = -1;
    private int editablePosition = -1;
    private ArrayList<Wallet> list;

    public WalletSettingAdapter(WalletSettingActivity context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public WalletSettingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWalletSettingBinding itemTagBinding = DataBindingUtil.bind(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallet_setting, parent, false));
        return new ViewHolder(itemTagBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletSettingAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setList(List<Wallet> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addTag(Wallet wallet) {
        this.list.add(0, wallet);
        notifyItemInserted(0);
    }

    public void resetEdit() {
        oldEditablePosition = editablePosition;
        editablePosition = -1;
        notifyItemChanged(oldEditablePosition);
    }

    public void setListener(WalletSettingItemListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends BaseViewHolder implements View.OnClickListener, View.OnFocusChangeListener {
        private WalletSettingItemViewModel viewModel;
        private ItemWalletSettingBinding itemTagBinding;

        public ViewHolder(ItemWalletSettingBinding itemView) {
            super(itemView.getRoot());
            itemTagBinding = itemView;

            itemTagBinding.tvWallet.setOnFocusChangeListener(this);
            itemTagBinding.okTag.setOnClickListener(this);
            itemTagBinding.deleteTag.setOnClickListener(this);
        }

        @Override
        public void onBind(int position) {
            Wallet wallet = list.get(position);
            viewModel = new WalletSettingItemViewModel(wallet);

            itemTagBinding.setViewModel(viewModel);

            viewModel.setIsWAlletEditable(oldEditablePosition != editablePosition
                    ? position == editablePosition
                    : viewModel.getIsWalletEditable1().get());

            setUpFocus();
            itemTagBinding.executePendingBindings();

        }

        @Override
        public void onClick(View view) {
            if (listener == null) {
                Log.e("Wallet Setting Adapter", "Wallet ItemListener can't be null");
                return;
            }
            switch (view.getId()) {
                case R.id.delete_tag:
                    listener.onDelete(list.get(getAdapterPosition()));
                    break;
                case R.id.ok_tag:
                    setUpPosition(getAdapterPosition());
                    if (viewModel.getIsWalletEditable().get()) {
                        Wallet wallet = list.get(getAdapterPosition());
                        Wallet newWallet = new Wallet(wallet.getId(),viewModel.getWalletTitle().get());
                        listener.onEdit(newWallet);
                    }
                    break;
            }
        }

        @Override
        public void onFocusChange(View view, boolean b) {
            viewModel.setIsWalletEditable1(!viewModel.getIsWalletEditable1().get());
            viewModel.setIsWAlletEditable(viewModel.getIsWalletEditable1().get());
        }

        private void setUpPosition(int position) {
            oldEditablePosition = oldEditablePosition == editablePosition ? -1 : editablePosition;
            editablePosition = position;
            viewModel.onEditClick();
            notifyItemChanged(oldEditablePosition);
            notifyItemChanged(editablePosition);
        }

        private void setUpFocus() {
            final InputMethodManager inputMethodManager = (InputMethodManager) itemTagBinding.tvWallet.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            if (viewModel.getIsWalletEditable().get()) {
                itemTagBinding.tvWallet.post(new Runnable() {
                    @Override
                    public void run() {
                        if (itemTagBinding.tvWallet.requestFocus()) {
                            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                            inputMethodManager.showSoftInput(itemTagBinding.tvWallet, InputMethodManager.SHOW_IMPLICIT);
                        }
                    }
                });
            } else {
                itemTagBinding.tvWallet.post(new Runnable() {
                    @Override
                    public void run() {
                        itemTagBinding.tvWallet.clearFocus();
                        inputMethodManager.hideSoftInputFromWindow(itemTagBinding.tvWallet.getWindowToken(), 0);
                    }
                });
            }

        }
    }
}

