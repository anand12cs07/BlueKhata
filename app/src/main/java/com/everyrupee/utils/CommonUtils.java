
package com.everyrupee.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;


import com.everyrupee.data.model.db.Category;
import com.everyrupee.R;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;


public final class CommonUtils {

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static String getFormattedAmount(double sumOfAmount){
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setCurrency(Currency.getInstance("INR"));
        return "₹ " + decimalFormat.format(sumOfAmount);
    }

    public static String getFormattedTransactionNumber(int transactions){
        String postString = transactions > 1 ? " Transactions " : " Transaction ";
        return transactions + postString;
    }

    public static String getFormattedTransactionStatus(Date date){
        String postString = Calendar.getInstance().getTime().before(date) ? " Upcoming " : " Completed ";
        return postString;
    }

    public static String getCategoryType(Category category){
        return category.getCatType() == 0 ? "Expense" : "Income";
    }
}
