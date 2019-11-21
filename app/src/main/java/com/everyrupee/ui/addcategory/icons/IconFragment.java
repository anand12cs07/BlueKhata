package com.everyrupee.ui.addcategory.icons;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.everyrupee.utils.AppUtils;
import com.everyrupee.R;
import com.everyrupee.ui.addcategory.AddCategoryActivity;
import com.everyrupee.utils.RecyclerViewEmptySupport;

/**
 * A simple {@link Fragment} subclass.
 */
public class IconFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_icon, container, false);
        RecyclerViewEmptySupport recyclerViewEmptySupport = (RecyclerViewEmptySupport)view.findViewById(R.id.recyclerView);
        recyclerViewEmptySupport.setLayoutManager(new GridLayoutManager(getContext(), AppUtils.calculateNoOfColumns(getContext())));
        recyclerViewEmptySupport.setAdapter(new IconAdapter(getResources().obtainTypedArray(R.array.category_icons),
                ((AddCategoryActivity)getActivity()).getListener()));

        return view;
    }

}
