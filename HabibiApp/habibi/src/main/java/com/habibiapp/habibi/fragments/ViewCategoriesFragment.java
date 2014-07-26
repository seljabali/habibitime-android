package com.habibiapp.habibi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.habibiapp.habibi.adapters.CategoryAdapter;
import com.habibiapp.habibi.R;
import com.habibiapp.habibi.datasources.CategoryDataSource;
import com.habibiapp.habibi.models.Category;

import java.util.ArrayList;

/**
* Created by habibi on 5/7/14.
*/
public class ViewCategoriesFragment extends Fragment {
    public static String TAG = ViewCategoriesFragment.class.getSimpleName();
    public static String CATEGORIES_KEY = "categories";
    private ArrayList<Category> categories;

    public static ViewCategoriesFragment newInstance(Activity activity) {
        ViewCategoriesFragment fragment = new ViewCategoriesFragment();
        CategoryDataSource categoryDataSource = new CategoryDataSource(activity);
        categoryDataSource.open();
        ArrayList<Category> categories = new ArrayList<Category>(categoryDataSource.getCategories());
        categoryDataSource.close();
        categories.add(Category.ALL);
        Bundle args = new Bundle();
        args.putParcelableArrayList(CATEGORIES_KEY, categories);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        categories = getArguments().getParcelableArrayList(CATEGORIES_KEY);
        View view = inflater.inflate(R.layout.fragment_view_categories, container, false);

        ListView listView = (ListView) view.findViewById(R.id.categories_list);
        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), R.id.category_view_text, categories);
        listView.setAdapter(categoryAdapter);

        return view;
    }

}
