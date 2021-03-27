package com.bluekhata.ui.upcoming;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluekhata.R;
import com.bluekhata.data.model.db.custom.UpcomingTransaction;
import com.bluekhata.utils.BindingUtils;
import com.bluekhata.utils.CalendarUtils;
import com.bluekhata.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aman on 05-08-2018.
 */

public class UpcomingTransactionAdapter extends RecyclerView.Adapter<UpcomingTransactionAdapter.ViewHolder> {

    private FragmentActivity context;
    private ArrayList<UpcomingTransaction> list;;

    public UpcomingTransactionAdapter(FragmentActivity context){
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public UpcomingTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming_transaction,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingTransactionAdapter.ViewHolder holder, int position) {
        UpcomingTransaction upcomingTransaction = list.get(position);

        holder.tvTitle.setText(upcomingTransaction.getCategory().getCatTitle());
        holder.tvSubTitle.setText(upcomingTransaction.getTagList());
        holder.tvData.setText(CalendarUtils.getDateInDdMmmYyyy(upcomingTransaction.getTransaction().getTransactionDate()));
        holder.tvAmount.setText(CommonUtils.getFormattedAmount(upcomingTransaction.getTransaction().getTransactionAmount()));

        BindingUtils.setCategoryIconImage(holder.imgIcon,upcomingTransaction.getCategory().getCatIcon());
        BindingUtils.setIconBackGround(holder.imgIcon, upcomingTransaction.getCategory().getCatColor());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void refreshList(List<UpcomingTransaction> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgIcon;
        AppCompatTextView tvTitle, tvSubTitle, tvData, tvAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            imgIcon = (AppCompatImageView)itemView.findViewById(R.id.img_icon);
            tvTitle = (AppCompatTextView)itemView.findViewById(R.id.tv_title);
            tvSubTitle = (AppCompatTextView)itemView.findViewById(R.id.tv_transaction);
            tvData = (AppCompatTextView) itemView.findViewById(R.id.tv_data);
            tvAmount = (AppCompatTextView)itemView.findViewById(R.id.tv_money);
        }

    }
}

