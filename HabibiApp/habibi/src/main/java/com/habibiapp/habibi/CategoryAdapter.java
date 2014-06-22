package com.habibiapp.habibi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.habibiapp.habibi.models.Category;
import java.util.HashMap;
import java.util.List;

/**
 * Created by habibi on 6/22/14.
 */
public class CategoryAdapter extends ArrayAdapter<Category> {

    HashMap<Category, Integer> idMap = new HashMap<Category, Integer>();
    private Context context;

    public CategoryAdapter(Context context, int resource, List<Category> categories) {
        super(context, resource, categories);
        this.context = context;
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.view_category, null);
        }

        Category item = getItem(position);
        if (item!= null) {
            TextView itemView = (TextView) view.findViewById(R.id.category_view_text);
            if (itemView != null) {
                itemView.setText(item.getCategoryName());
            }
        }
        return view;
    }
}