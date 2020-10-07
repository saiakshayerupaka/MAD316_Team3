package com.cegep.lanbow.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.cegep.lanbow.models.Item;
import com.cegep.lanbow.models.Student;

import java.util.ArrayList;
import java.util.List;

public class ItemlistAdapter extends BaseAdapter implements Filterable {

    private List<Item> originalitems;
    private List<Item> filtereditems;
    private Context mContext;

    private ItemFilter mFilter = new ItemFilter();

    public ItemlistAdapter(Context context,List<Item> items ) {
        this.originalitems = items;
        this.filtereditems = items;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return filtereditems.size();
    }

    @Override
    public Item getItem(int position) {
        return filtereditems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Item> list = originalitems;

            int count = list.size();
            final ArrayList<Item> nlist = new ArrayList<Item>(count);

            Item filterableItem;

            for (int i = 0; i < count; i++) {
                filterableItem = list.get(i);
                if (filterableItem.getItemName().toLowerCase().contains(filterString)) {
                    nlist.add(filterableItem);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filtereditems = (ArrayList<Item>) results.values;
            notifyDataSetChanged();
        }

    }
}
