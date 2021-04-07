package com.bluekhata.utils.datasource;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

import java.util.Collections;
import java.util.List;

public class GenericDataSource<Value extends GenericDataSource.AbstractValue> extends ItemKeyedDataSource<Integer, Value> {
    private List<Value> dataList;

    public GenericDataSource(List<Value> dataList) {
        if (dataList != null) {
            this.dataList = dataList;
        } else {
            this.dataList = Collections.emptyList();
        }
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Value> callback) {
        callback.onResult(dataList.subList(0, Math.min(params.requestedLoadSize, dataList.size())));
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Value> callback) {
        callback.onResult(dataList.subList(params.key, Math.min(params.requestedLoadSize, dataList.size())));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Value> callback) {

    }

    @NonNull
    @Override
    public Integer getKey(@NonNull Value item) {
        return item.id;
    }

    public interface AbstractValue {
        public Integer id = 0;
    }
}
