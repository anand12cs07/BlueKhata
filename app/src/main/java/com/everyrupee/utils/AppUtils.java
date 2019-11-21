package com.everyrupee.utils;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

public class AppUtils {

    public static void setFragment(Fragment fragment, boolean removeStack, boolean addToBackStack, FragmentActivity activity, int mContainer, String tag) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ftTransaction = fragmentManager.beginTransaction();
        if (removeStack && addToBackStack) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ftTransaction.replace(mContainer, fragment, tag);
            ftTransaction.addToBackStack(null);
        } else if (removeStack && !addToBackStack) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ftTransaction.replace(mContainer, fragment, tag);
        } else if (!removeStack && addToBackStack) {
            ftTransaction.replace(mContainer, fragment, tag);
            ftTransaction.addToBackStack(null);
        } else {
            ftTransaction.replace(mContainer, fragment, tag);
        }
        ftTransaction.commit();
    }

    public static void setFragment(Fragment fragment, boolean addToBackStack, FragmentActivity activity,int mContainer, String tag){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ftTransaction = fragmentManager.beginTransaction();
        if (addToBackStack){
            ftTransaction.add(mContainer, fragment, tag);
            ftTransaction.addToBackStack(null);
        }else {
            ftTransaction.add(mContainer, fragment, tag);
        }
        ftTransaction.commit();
    }

    public static ShapeDrawable getDrawableBitmap(int color){
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 90);
        return noOfColumns;
    }
    public static void disableEnableControls(boolean enable, ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                disableEnableControls(enable, (ViewGroup) child);
            }
        }
    }
}

