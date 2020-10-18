package com.cegep.lanbow.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class HomeItemListAdapter extends BaseAdapter implements Filterable {
    private List<Item> originalitems;
    private List<Item> filtereditems;
    private Context mContext;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;

    private ItemFilter mFilter = new ItemFilter();

    public HomeItemListAdapter(Context context,List<Item> items ) {
        this.originalitems = items;
        this.filtereditems = items;
        this.mContext = context;

        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.homelistitem, null);
        }



        TextView itemName = v.findViewById(R.id.itemName);
        TextView itemId = v.findViewById(R.id.itemId);
        final ImageView itemImg = v.findViewById(R.id.itemImg);

        firebaseStorage.getReference().child(filtereditems.get(position).getItemUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mContext).load(uri).into(itemImg);
            }
        });

        itemId.setText(filtereditems.get(position).getItemId());
        itemName.setText(filtereditems.get(position).getItemName().toString());

        return v;

    }

    @Override
    public Filter getFilter() {
        return mFilter;
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
                if (filterableItem.getItemName().toLowerCase().contains(filterString) || filterableItem.getItemId().toLowerCase().startsWith(filterString)) {
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
