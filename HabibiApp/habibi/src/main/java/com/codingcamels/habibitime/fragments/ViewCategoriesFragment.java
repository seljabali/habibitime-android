package com.codingcamels.habibitime.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codingcamels.habibitime.adapters.CategoryAdapter;
import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.datasources.CategoryDataSource;
import com.codingcamels.habibitime.models.Category;
import com.codingcamels.habibitime.models.Phrase;

import java.util.ArrayList;

/**
* Created by habibi on 5/7/14.
*/
public class ViewCategoriesFragment extends Fragment {
    public static String TAG = ViewCategoriesFragment.class.getSimpleName();
    public static String CATEGORIES_KEY = "categories";
    private ArrayList<Category> categories;
    private ListView listView;

    public static ViewCategoriesFragment newInstance(Activity activity) {
        ViewCategoriesFragment fragment = new ViewCategoriesFragment();
        CategoryDataSource categoryDataSource = new CategoryDataSource(activity);
        categoryDataSource.open();
        ArrayList<Category> categories = new ArrayList<Category>(categoryDataSource.getCategories());
        categoryDataSource.close();
        categories.add(Category.SETTINGS);
        Bundle args = new Bundle();
        args.putParcelableArrayList(CATEGORIES_KEY, categories);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_view_categories, container, false);
        if (view == null) {
            return null;
        }
        categories = getArguments().getParcelableArrayList(CATEGORIES_KEY);
        listView = (ListView) view.findViewById(R.id.categories_list);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), R.id.category_view_text, categories);
        listView.setAdapter(categoryAdapter);
    }
}
