package com.habibiapp.habibi;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.habibiapp.habibi.fragments.ViewPhrasesFragment;
import com.habibiapp.habibi.models.Category;
import java.util.HashMap;
import java.util.List;

/**
 * Created by habibi on 6/22/14.
 */
public class CategoryAdapter extends ArrayAdapter<Category> {

    HashMap<Category, Integer> idMap = new HashMap<Category, Integer>();
    private Activity activity;

    public CategoryAdapter(Context context, int resource, List<Category> categories) {
        super(context, resource, categories);
        this.activity = (Activity)context;
        for (int i = 0; i < categories.size(); i++) {
            idMap.put(categories.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        Category item = getItem(position);
        return idMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
            return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.view_category, null);
        }

        final Category category = getItem(position);
        if (category!= null) {
            TextView itemView = (TextView) view.findViewById(R.id.category_view_text);
            if (itemView != null) {
                itemView.setText(category.getCategoryName());
                itemView.setBackgroundColor(getColorForCategory(position));
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewPhrasesFragment fragment = ViewPhrasesFragment.newInstance(category);
                        activity.getFragmentManager().beginTransaction()
                                .replace(R.id.fragmentLayoutContainer, fragment, ViewPhrasesFragment.TAG)
                                .addToBackStack(ViewPhrasesFragment.TAG)
                                .commit();
                    }
                });
            }
        }
        int dpHeight = (int) ViewUtil.getScreenHeightDP(activity) / (Category.CATEGORY_COUNT-1);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.category_view_layout);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpHeight));

        return view;
    }

    private int getColorForCategory(int position) {
        TypedArray colors = activity.getResources().obtainTypedArray(R.array.habibi_colors);
        return colors.getColor(position % colors.length(), 0);
    }

}