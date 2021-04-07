package com.bluekhata.ui.dashboard.history;

import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluekhata.utils.CalendarUtils;
import com.bluekhata.R;
import com.bluekhata.data.model.db.custom.TransactionWithTagAndCategory;
import com.bluekhata.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private ArrayList<TransactionWithTagAndCategory> list, mPageList, listFiltered;

    private boolean isLoadingAdded = false;
    private boolean isSearchApplicable = false;

    private FragmentActivity context;

    public HistoryAdapter(FragmentActivity context) {
        this.list = new ArrayList<>();
        mPageList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_detail, parent, false);
            return new VHItem(view);
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
            return new VHHeader(view);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TransactionWithTagAndCategory dataItem = getItem(position);
        if (holder instanceof VHItem) {
            ((VHItem) holder).tvItemTitle.setText(dataItem.getTransactionWithTag().getTagList());

            ((VHItem) holder).tvItemTransaction.setText(CommonUtils.getCategoryType(
                    dataItem.getCategory()
            ));

            ((VHItem) holder).tvMoney.setText(CommonUtils.getFormattedAmount(
                    dataItem.getTransactionWithTag().getTransaction().getTransactionAmount())
            );

            ((VHItem) holder).indicator.setBackgroundColor(context.getResources().getColor(
                    dataItem.getCategory().getCatType() == 0 ? R.color.colorRoseRed : R.color.colorGreen)
            );

        } else if (holder instanceof VHHeader) {
            ((VHHeader) holder).tvHeader.setText(CalendarUtils.getDateInDdMmmYyyy(
                    dataItem.getTransactionWithTag().getTransaction().getTransactionDate())
            );
        }
    }

    @Override
    public int getItemCount() {
        return mPageList == null ? 0 : mPageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mPageList.get(position).getTransactionWithTag().isHeader())
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    public void setList(List<TransactionWithTagAndCategory> list) {
        this.list.clear();
        this.list.addAll(list);
        listFiltered = new ArrayList<>(list);
    }

    public void setPageList() {
        mPageList.clear();
    }

    public ArrayList<TransactionWithTagAndCategory> getList() {
        return list;
    }

    public void addSubList(List<TransactionWithTagAndCategory> list) {
        if (list.size() == 0) {
            notifyDataSetChanged();
        }

        for (TransactionWithTagAndCategory item : list) {
            addItem(item);
        }
    }

    public void addItem(TransactionWithTagAndCategory item) {
        mPageList.add(item);
        notifyItemInserted(mPageList.size() == 0 ? 0 : mPageList.size() - 1);
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        addItem(new TransactionWithTagAndCategory(list.get(0)));
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mPageList.size() - 1;
        TransactionWithTagAndCategory item = getItem(position);

        if (item != null) {
            mPageList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public boolean isSearchApplicable() {
        return isSearchApplicable;
    }

    public void filterResult(String filter) {
        isLoadingAdded = false;
        if (filter.isEmpty()) {
            mPageList = listFiltered;
            isSearchApplicable = false;
        } else if (listFiltered != null && !listFiltered.isEmpty()) {
            ArrayList<TransactionWithTagAndCategory> filteredList = new ArrayList<>();
            isSearchApplicable = true;
            for (TransactionWithTagAndCategory row : listFiltered) {

                if (row.getTransactionWithTag().isHeader() ||
                        row.getTransactionWithTag().getTagList().toLowerCase().contains(filter.toLowerCase())) {
                    filteredList.add(row);
                }
            }
            mPageList = filteredList;
        }
        notifyDataSetChanged();
    }

    private TransactionWithTagAndCategory getItem(int position) {
        return mPageList.get(position);
    }

    class VHItem extends RecyclerView.ViewHolder {
        private AppCompatTextView tvItemTitle;
        private AppCompatTextView tvItemTransaction;
        private AppCompatTextView tvMoney;
        private View indicator;

        public VHItem(View itemView) {
            super(itemView);
            tvItemTitle = itemView.findViewById(R.id.tv_title);
            tvItemTransaction = itemView.findViewById(R.id.tv_transaction);
            tvMoney = itemView.findViewById(R.id.tv_money);
            indicator = itemView.findViewById(R.id.indicator);
        }
    }

    public class VHHeader extends RecyclerView.ViewHolder {
        private AppCompatTextView tvHeader;

        public VHHeader(View itemView) {
            super(itemView);
            tvHeader = (AppCompatTextView) itemView.findViewById(R.id.tv_header);
        }
    }
}
