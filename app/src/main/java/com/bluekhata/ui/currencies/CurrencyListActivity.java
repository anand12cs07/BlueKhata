package com.bluekhata.ui.currencies;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluekhata.R;
import com.bluekhata.utils.RecyclerViewEmptySupport;

public class CurrencyListActivity extends AppCompatActivity implements CurrencyListAdapter.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        prefManager = new PrefManager(this);
//        boolean themeTypeLight = prefManager.getThemeType().equals(LIGHT_THEME);
//        setTheme(themeTypeLight ? R.style.BaseTheme_Light : R.style.BaseTheme_Dark);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);
        RecyclerViewEmptySupport recyclerView = (RecyclerViewEmptySupport) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CurrencyListAdapter adapter = new CurrencyListAdapter();
        adapter.setItemClickListener(this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(String currencyCode) {
        Intent intent = new Intent();
        intent.putExtra("symbol", currencyCode);
        setResult(RESULT_OK, intent);
        finish();
    }
}
