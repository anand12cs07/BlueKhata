package com.bluekhata.ui.dashboard.budget.add;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluekhata.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BudgetEmptyFragment extends Fragment {


    public BudgetEmptyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_budget_empty, container, false);
    }

}
