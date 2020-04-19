package com.bluekhata.ui.tags;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.bluekhata.ViewModelProviderFactory;
import com.bluekhata.data.model.db.Tag;
import com.bluekhata.ui.base.BaseActivity;
import com.bluekhata.ui.base.BaseAlertButtonClickListener;
import com.bluekhata.BR;
import com.bluekhata.R;
import com.bluekhata.databinding.ActivityTagBinding;

import java.util.List;

import javax.inject.Inject;

public class TagActivity extends BaseActivity<ActivityTagBinding, TagActivityViewModel> implements
        View.OnTouchListener, View.OnClickListener, TagItemListener, BaseAlertButtonClickListener {

    @Inject
    DividerItemDecoration dividerItemDecoration;
    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    TagAdapter tagAdapter;
    @Inject
    ViewModelProviderFactory factory;

    private Tag tag;
    private ActivityTagBinding tagBinding;
    private TagActivityViewModel viewModel;

    public static Intent newIntent(Context context) {
        return new Intent(context, TagActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tag;
    }

    @Override
    public TagActivityViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(TagActivityViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tagBinding = getViewDataBinding();

        alertButtonClickListener = this;

        setSupportActionBar(tagBinding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tagBinding.recyclerView.addItemDecoration(dividerItemDecoration);
        tagBinding.recyclerView.setLayoutManager(layoutManager);
        tagBinding.recyclerView.setEmptyView(tagBinding.imgEmpty);

        setUpTagListObserver();
        setUpSnackBarMessageObserver();

        viewModel.fetchTagList();
        tagBinding.recyclerView.setAdapter(tagAdapter);

        tagAdapter.setListener(this);
        tagBinding.tvTag.setOnTouchListener(this);
        tagBinding.okTag.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        viewModel.addTag(new Tag(viewModel.getTagTitle().get()));
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        tagAdapter.resetEdit();
        return false;
    }

    @Override
    public void onEdit(Tag tag) {
        viewModel.updateTag(tag);
    }

    @Override
    public void onDelete(Tag tag) {
        this.tag = tag;
        showAlertDialog();
    }

    @Override
    public void onPositionClickListener() {
        viewModel.deleteTag(tag);
    }

    @Override
    public void onNegativeClickListener() {
        hideAlertDialog();
    }

    private void setUpTagListObserver() {
        viewModel.getTagList().observe(this, new Observer<List<Tag>>() {
            @Override
            public void onChanged(@Nullable List<Tag> tags) {
                tagAdapter.setList(tags);
            }
        });

        viewModel.getTagAdded().observe(this, new Observer<Tag>() {
            @Override
            public void onChanged(@Nullable Tag tag) {
                tagBinding.tvTag.setText("");
                tagAdapter.addTag(tag);
            }
        });
    }

    private void setUpSnackBarMessageObserver() {
        viewModel.getSnackBarMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Snackbar.make(tagBinding.getRoot(), s, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
