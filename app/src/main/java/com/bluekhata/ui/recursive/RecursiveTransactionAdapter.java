package com.bluekhata.ui.recursive;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluekhata.data.model.db.custom.RecurrenceDetail;
import com.bluekhata.R;
import com.bluekhata.utils.BindingUtils;
import com.bluekhata.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aman on 05-08-2018.
 */

public class RecursiveTransactionAdapter extends RecyclerView.Adapter<RecursiveTransactionAdapter.ViewHolder> {

    private FragmentActivity context;
    private ArrayList<RecurrenceDetail> list;
    ;

    public RecursiveTransactionAdapter(FragmentActivity context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecursiveTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecursiveTransactionAdapter.ViewHolder holder, int position) {
        RecurrenceDetail recurrenceDetail = list.get(position);

        holder.tvTitle.setText(recurrenceDetail.getCategory().getCatTitle());
        holder.tvSubTitle.setText(recurrenceDetail.getTagString());
        holder.tvData.setText(recurrenceDetail.getRecurrence().getRecurrenceTitle());
        holder.tvAmount.setText(CommonUtils.getFormattedAmount(recurrenceDetail.getTransaction().getTransactionAmount()));

        BindingUtils.setCategoryIconBackGround(holder.imgIcon, recurrenceDetail.getCategory().getCatColor());
        BindingUtils.setCategoryIconImage(holder.imgIcon, recurrenceDetail.getCategory().getCatIcon());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void refreshList(List<RecurrenceDetail> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public ArrayList<RecurrenceDetail> getList() {
        return list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgIcon;
        AppCompatTextView tvTitle, tvSubTitle, tvData, tvAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            imgIcon = (AppCompatImageView) itemView.findViewById(R.id.img_icon);
            tvTitle = (AppCompatTextView) itemView.findViewById(R.id.tv_title);
            tvSubTitle = (AppCompatTextView) itemView.findViewById(R.id.tv_transaction);
            tvData = (AppCompatTextView) itemView.findViewById(R.id.tv_data);
            tvAmount = (AppCompatTextView) itemView.findViewById(R.id.tv_money);
        }

    }
}

