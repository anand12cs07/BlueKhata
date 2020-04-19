package com.bluekhata.ui.base;

import android.app.Dialog;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.bluekhata.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import dagger.android.support.AndroidSupportInjection;

public abstract class BaseBottomSheetDialog<T extends ViewDataBinding, V extends BaseViewModel> extends BottomSheetDialogFragment {

    private T mViewDataBinding;
    private V mViewModel;

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View touchOutsideView = getDialog().getWindow()
                .getDecorView()
                .findViewById(R.id.touch_outside);
        touchOutsideView.setOnClickListener(null);
    }

    @Override
    public void onAttach(Context context) {
        performDependencyInjection();
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        performDataBinding();

        dialog.setContentView(mViewDataBinding.getRoot());
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    public View getBindingView() {
        return mViewDataBinding.getRoot();
    }

    private void performDependencyInjection() {
        AndroidSupportInjection.inject(this);
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getLayoutId(), null, false);
        ;
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.setLifecycleOwner(this);
        mViewDataBinding.executePendingBindings();
    }
}
