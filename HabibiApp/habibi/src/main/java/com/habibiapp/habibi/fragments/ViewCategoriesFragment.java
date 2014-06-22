package com.habibiapp.habibi.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.habibiapp.habibi.CategoryAdapter;
import com.habibiapp.habibi.R;
import com.habibiapp.habibi.models.Category;

import java.util.List;

/**
* Created by habibi on 5/7/14.
*/
public class ViewCategoriesFragment extends Fragment {
    public static String TAG = ViewCategoriesFragment.class.getSimpleName();
    private List<Category> categories;

    public static ViewCategoriesFragment newInstance(List<Category> categories) {
        ViewCategoriesFragment fragment = new ViewCategoriesFragment();
        fragment.setCategories(categories);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_view_categories, container, false);

        ListView listView = (ListView) view.findViewById(R.id.categories_list);
        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), R.id.category_view_text, categories);
        listView.setAdapter(categoryAdapter);

        return view;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
