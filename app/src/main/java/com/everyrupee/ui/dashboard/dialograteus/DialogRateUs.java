package com.everyrupee.ui.dashboard.dialograteus;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRatingBar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.everyrupee.R;

/**
 * Created by aman on 27-10-2018.
 */

public class DialogRateUs extends AlertDialog implements RatingBar.OnRatingBarChangeListener, View.OnClickListener {
    private LinearLayout layoutFeedback;
    private AppCompatRatingBar ratingBar;
    private AppCompatEditText edtFeedback;
    private AppCompatButton btnCancel, btnSubmit;

    public DialogRateUs(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_rate_us);

        layoutFeedback = (LinearLayout) findViewById(R.id.layoutFeedback);
        ratingBar = (AppCompatRatingBar) findViewById(R.id.rateBar);
        edtFeedback = (AppCompatEditText) findViewById(R.id.tvFeedback);
        btnCancel = (AppCompatButton) findViewById(R.id.btn_cancel);
        btnSubmit = (AppCompatButton) findViewById(R.id.btn_submit);

        ratingBar.setOnRatingBarChangeListener(this);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        if (ratingBar.getRating() >= 4) {
            layoutFeedback.setVisibility(View.GONE);
        } else {
            layoutFeedback.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        cancel();
    }
}
