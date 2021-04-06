package com.bluekhata.ui.dashboard.home.calendarmodes;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bluekhata.R;
import com.bluekhata.databinding.ItemHistoryFilterBinding;
import com.multicalenderview.HorizontalCalendar;

public class CalendarModeAdapter extends RecyclerView.Adapter<CalendarModeAdapter.ViewHolder> {
    private int selectedPosition = -1;
    private OnCalendarModeClickListener clickListener;
    private String[] modes = new String[]{"Daily", "Weekly", "Monthly", "Yearly"};

    public CalendarModeAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryFilterBinding view = ItemHistoryFilterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.title.setText(modes[position]);
        holder.checkBox.setChecked(position == selectedPosition);

        holder.itemView.setBackgroundColor(getColorFromAttr(
                holder.itemView.getContext(),
                position == selectedPosition ? R.attr.WindowBackGroundColor : R.attr.BackGroundColorWhite
        ));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.checkBox.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (clickListener != null) {
                            clickListener.calendarModeClickListener(getSelectedMode(holder.getAdapterPosition()));
                        }
                    }
                }, 250);

            }
        });
    }

    @Override
    public int getItemCount() {
        return modes.length;
    }

    public void setOnCalendarModeClickListener(OnCalendarModeClickListener calendarModeClickListener) {
        this.clickListener = calendarModeClickListener;
    }

    public void refreshSelectedPosition(int selectedPosition){
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    private String getSelectedMode(int selectedPosition) {
        switch (selectedPosition) {
            case 1:
                return HorizontalCalendar.MODE_WEEKLY;
            case 2:
                return HorizontalCalendar.MODE_MONTHLY;
            case 3:
                return HorizontalCalendar.MODE_YEARLY;
            default:
                return HorizontalCalendar.MODE_DAILY;

        }
    }

    private int getColorFromAttr(Context context, @AttrRes int attr) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView title;
        AppCompatCheckBox checkBox;

        public ViewHolder(@NonNull ItemHistoryFilterBinding itemView) {
            super(itemView.getRoot());
            title = itemView.title;
            checkBox = itemView.chk;
        }
    }
}
