package com.cegep.lenbow.adapters;
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
import com.cegep.lenbow.R;
import com.cegep.lenbow.models.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Item list adapter
 * @author dipmal lakhani
 * @author Sai Akshay
 * @author Gopichand
 * @author HarshaVardhan
 * @author Vinay
 * @author prashant
 * @author Amandeep singh
 */


public class ItemlistAdapter extends BaseAdapter implements Filterable {
    /**
     * list originalitems attribute
     */
    private List<Item> originalitems;
    /**
     * list filtereditems attribute
     */
    private List<Item> filtereditems;
    /**
     * Context object
     */
    private Context mContext;
    /**
     * Firebase storage object
     */
    private FirebaseStorage firebaseStorage;
    /**
     * Firebase database object
     */
    private FirebaseDatabase firebaseDatabase;

    Map<Integer,View> views = new HashMap<Integer,View>();
    /**
     * ItemFilter object mFilter
     */
    private ItemFilter mFilter = new ItemFilter();

    public ItemlistAdapter(Context context,List<Item> items ) {
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

    private class ViewHolder{
        TextView itemName;
        TextView itemId;
        ImageView itemImg;
        TextView item_type;
        ImageView delete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View v = convertView;
        ViewHolder holder = new ViewHolder();


            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.itemlist, null);
            holder.itemName = v.findViewById(R.id.itemName);
            holder.itemId = v.findViewById(R.id.itemId);
            holder.itemImg = v.findViewById(R.id.itemImg);
            holder.item_type = v.findViewById(R.id.item_type);
            holder.delete = v.findViewById(R.id.removeItem);




        holder.item_type.setText(filtereditems.get(position).getItemType());

        final ViewHolder finalHolder = holder;

        if(finalHolder.itemImg.getTag()==null) {
            firebaseStorage.getReference().child(filtereditems.get(position).getItemUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(mContext).load(uri).into(finalHolder.itemImg);
                }
            });
            finalHolder.itemImg.setTag(true);
        }

       holder.itemId.setText(filtereditems.get(position).getItemId());

        holder.itemName.setText(filtereditems.get(position).getItemName().toString());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseDatabase.getReference().child("Items").child(filtereditems.get(position).getItemId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(mContext,"Removed",Toast.LENGTH_LONG).show();
                            notifyDataSetChanged();
                        }
                    }
                });

            }
        });

        v.setTag(holder);
        return v;

    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        /**
         * This method filters the items
         */
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
                if (filterableItem.getItemName().toLowerCase().contains(filterString) || filterableItem.getItemId().toLowerCase().startsWith(filterString) || filterableItem.getItemType().toLowerCase().startsWith(filterString)) {
                    nlist.add(filterableItem);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        /**
         * This method displays the filtered results
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filtereditems = (ArrayList<Item>) results.values;
            notifyDataSetChanged();
        }

    }
}
