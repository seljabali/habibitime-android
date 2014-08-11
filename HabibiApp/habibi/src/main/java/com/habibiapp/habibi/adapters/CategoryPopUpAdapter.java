package com.habibiapp.habibi.adapters;


import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.habibiapp.habibi.R;
import com.habibiapp.habibi.models.Category;
import java.util.HashMap;
import java.util.List;

/**
 * Created by habibi on 8/10/14.
 */
public class CategoryPopUpAdapter extends ArrayAdapter<Category> {

        HashMap<Category, Integer> idMap = new HashMap<Category, Integer>();
        private Context context;
        private List<Category> categories;

        public CategoryPopUpAdapter(Context context, int resource, List<Category> categories) {
            super(context, resource, categories);
            this.context = context;
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
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.view_category, null);
            }

            final Category category = getItem(position);
            if (category!= null) {
                TextView itemView = (TextView) view.findViewById(R.id.category_view_text);
                if (itemView != null) {
                    itemView.setText(category.getCategoryName().toUpperCase());
                    itemView.setBackgroundColor(getColorForCategory(position));
//                    itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            ViewPhrasesFragment fragment = ViewPhrasesFragment.newInstance(category);
//                        }
//                    });
                }
            }
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.category_view_layout);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

            return view;
        }

        private int getColorForCategory(int position) {
            TypedArray colors = context.getResources().obtainTypedArray(R.array.habibi_colors);
            return colors.getColor(position % colors.length(), 0);
        }

}
