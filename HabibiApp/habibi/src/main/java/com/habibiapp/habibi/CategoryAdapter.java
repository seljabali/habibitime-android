package com.habibiapp.habibi;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
//            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.view_category, null);
        }

        final Category category = getItem(position);
        if (category!= null) {
            TextView itemView = (TextView) view.findViewById(R.id.category_view_text);
            if (itemView != null) {
                itemView.setText(category.getCategoryName());
                itemView.setBackgroundColor(getColorForCategory(category));
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
        return view;
    }

    private int getColorForCategory(Category category) {
        if (category.getId() == Category.QUESTION.getId()) {
            return activity.getResources().getColor(R.color.pink);
        } else if (category.getId() == Category.ANSWER.getId()) {
            return activity.getResources().getColor(R.color.tortoise);
        } else if (category.getId() == Category.MOOD.getId()) {
            return activity.getResources().getColor(R.color.green);
        } else if (category.getId() == Category.FLIRT.getId()) {
            return activity.getResources().getColor(R.color.light_blue);
        }
        return activity.getResources().getColor(R.color.pink);
    }

}