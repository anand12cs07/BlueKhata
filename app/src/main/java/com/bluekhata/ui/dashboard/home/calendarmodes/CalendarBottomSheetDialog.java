package com.bluekhata.ui.dashboard.home.calendarmodes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import com.bluekhata.databinding.BottomsheetCalanderModeBinding;
import com.bluekhata.ui.base.BaseBottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import javax.inject.Inject;

public class CalendarBottomSheetDialog extends BaseBottomSheetDialog<BottomsheetCalanderModeBinding, CalendarModeViewModel>
implements OnCalendarModeClickListener{
    public static final String TAG = "CalendarBottomSheetDialog";

    @Inject
    ViewModelProviderFactory factory;

    private CalendarModeViewModel viewModel;
    private BottomsheetCalanderModeBinding calanderModeBinding;

    private ICalendarModeChangeListener modeChangeListener;
    private CalendarModeAdapter adapter;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.bottomsheet_calander_mode;
    }

    @Override
    public CalendarModeViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(CalendarModeViewModel.class);
        return viewModel;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        calanderModeBinding = getViewDataBinding();

        showExpandedBottomSheet(dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);

        adapter = new CalendarModeAdapter();
        adapter.setOnCalendarModeClickListener(this);

        calanderModeBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        calanderModeBinding.recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void calendarModeClickListener(String selectedMode) {
        if (modeChangeListener != null) {
            modeChangeListener.onCalendarModeChange(selectedMode);
            dismiss();
        }else {
            Log.e("Exception>","walletClickListener is Null");
        }
    }

    public void setModeChangeListener(ICalendarModeChangeListener modeChangeListener) {
        this.modeChangeListener = modeChangeListener;
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
