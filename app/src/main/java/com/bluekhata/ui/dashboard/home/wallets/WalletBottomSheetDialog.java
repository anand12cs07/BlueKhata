package com.bluekhata.ui.dashboard.home.wallets;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluekhata.BR;
import com.bluekhata.R;
import com.bluekhata.ViewModelProviderFactory;
import com.bluekhata.data.model.db.Wallet;
import com.bluekhata.databinding.BottomsheetWalletBinding;
import com.bluekhata.ui.base.BaseBottomSheetDialog;
import com.bluekhata.ui.dashboard.home.wallets.settings.WalletSettingActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import javax.inject.Inject;

public class WalletBottomSheetDialog extends BaseBottomSheetDialog<BottomsheetWalletBinding,WalletViewModal>
implements View.OnClickListener, IWalletChangeListener {
    public static final String TAG = "WalletBottomSheetDialog";

    @Inject
    ViewModelProviderFactory factory;

    private WalletViewModal viewModal;
    private BottomsheetWalletBinding walletBinding;

    private WalletAdapter adapter;
    private OnWalletClickListener clickListener;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.bottomsheet_wallet;
    }

    @Override
    public WalletViewModal getViewModel() {
        viewModal = ViewModelProviders.of(this,factory).get(WalletViewModal.class);
        return viewModal;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        walletBinding = getViewDataBinding();

        showExpandedBottomSheet(dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);

        walletBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WalletAdapter();
        walletBinding.recyclerView.setAdapter(adapter);
        adapter.refresh(Wallet.getList());
        adapter.setWalletClickListener(this);

        walletBinding.btnWalletSetting.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), WalletSettingActivity.class);
        startActivity(intent);
        dismiss();
    }

    @Override
    public void onWalletModeChange(Wallet wallet) {
        if (clickListener != null) {
            clickListener.walletClickListener(wallet);
            dismiss();
        }
    }

    public void setOnWalletClickListener(OnWalletClickListener clickListener){
        this.clickListener = clickListener;
    }

    private void showExpandedBottomSheet(Dialog dialog) {
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = (FrameLayout) d.findViewById(R.id.design_bottom_sheet);

                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }
}
