package com.codingcamels.habibitime.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codingcamels.habibitime.OnSwipeTouchListener;
import com.codingcamels.habibitime.R;
import com.codingcamels.habibitime.SettingsActivity;
import com.codingcamels.habibitime.ViewUtil;
import com.codingcamels.habibitime.fragments.SettingsFragment;
import com.codingcamels.habibitime.fragments.ViewPhrasesFragment;
import com.codingcamels.habibitime.models.Category;
import java.util.HashMap;
import java.util.List;

/**
 * Created by habibi on 6/22/14.
 */
public class CategoryAdapter extends ArrayAdapter<Category> {

    HashMap<Category, Integer> idMap = new HashMap<Category, Integer>();
    private Activity activity;
    private List<Category> categories;

    public CategoryAdapter(Context context, int resource, List<Category> categories) {
        super(context, resource, categories);
        this.activity = (Activity)context;
        this.categories = categories;
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
                itemView.setText(category.getCategoryName().toUpperCase());
                itemView.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                init(category);
                            }
                        }
                );
            }
        }
        int dpHeight = Math.round(ViewUtil.getScreenHeightDP(activity) / categories.size());
        dpHeight -= 10;
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpHeight));
        view.setBackgroundColor(getColorForCategory(position));

        return view;
    }

    private void init(Category category) {
        Fragment fragment;
        if (Category.SETTINGS.equals(category)) {
            fragment = SettingsFragment.newInstance();
        } else {
            fragment = ViewPhrasesFragment.newInstance(category);
        }
        activity.getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fragment_slide_left_enter,
                        R.anim.fragment_slide_left_exit,
                        R.anim.fragment_slide_right_enter,
                        R.anim.fragment_slide_right_exit)
                .replace(R.id.fragmentLayoutContainer, fragment, ViewPhrasesFragment.TAG)
                .addToBackStack(ViewPhrasesFragment.TAG)
                .commit();
    }

    private int getColorForCategory(int position) {
        TypedArray colors = activity.getResources().obtainTypedArray(R.array.habibi_colors);
        return colors.getColor(position % colors.length(), 0);
    }

}

//                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out, R.anim.slide_in_left, R.anim.slide_out)