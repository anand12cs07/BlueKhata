package com.bluekhata.utils;

import android.content.Context;
import android.content.res.ColorStateList;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.BindingAdapter;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.widget.AppCompatImageView;

import com.bluekhata.data.model.db.Category;
import com.bluekhata.ui.dashboard.category.CategoryListAdapter;
import com.bluekhata.R;

import java.util.List;

public final class BindingUtils {

    private BindingUtils() {

    }

    @BindingAdapter({"categoryColor"})
    public static void setCategoryColor(FloatingActionButton fab, String color) {
        Context context = fab.getContext();
        int resColor = Color.parseColor(color);
        fab.setBackgroundTintList(ColorStateList.valueOf(resColor));
    }

    @BindingAdapter({"categoryDrawable"})
    public static void setCategoryDrawable(FloatingActionButton fab, String drawable) {
        Context context = fab.getContext();
        int resDrawable = resourceStringToResourceId(context, drawable, "drawable");
        fab.setImageDrawable(AppCompatResources.getDrawable(context, resDrawable));
    }

    @BindingAdapter({"adapter"})
    public static void setCategoryItems(RecyclerViewEmptySupport recyclerView, List<Category> list) {
        CategoryListAdapter adapter = (CategoryListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setList(list);
        }
    }

    @BindingAdapter({"iconBackGround"})
    public static void setCategoryIconBackGround(AppCompatImageView img, String color) {
        ShapeDrawable bitmapDrawable = AppUtils.getDrawableBitmap(Color.parseColor(color));
        img.setBackground(bitmapDrawable);
    }

    @BindingAdapter({"iconImage"})
    public static void setCategoryIconImage(AppCompatImageView img, String icon) {
        Context context = img.getContext();
        img.setImageResource(resourceStringToResourceId(context, icon, "drawable"));
    }

    @BindingAdapter({"okImage"})
    public static void setOkIconImage(AppCompatImageView img, boolean isEqual) {
        img.setImageDrawable(AppCompatResources.getDrawable(img.getContext(), isEqual ? R.drawable.ic_eqaul : R.drawable.ic_done));
    }

    @BindingAdapter({"tagEditImage"})
    public static void setTagEditImage(AppCompatImageView img, boolean isEqual) {
        img.setImageResource(isEqual ? R.drawable.ic_done : R.drawable.ic_edit);
    }

    private static int resourceStringToResourceId(Context context, String resInString, String resClass) {
        try {
            return context.getResources().getIdentifier(resInString, resClass, context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


}
