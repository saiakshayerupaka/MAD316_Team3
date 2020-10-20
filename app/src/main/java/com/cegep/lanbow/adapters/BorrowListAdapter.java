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

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Item;
import com.cegep.lanbow.models.Reserve;
import com.cegep.lanbow.models.Student;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowListAdapter extends BaseAdapter implements Filterable {

    private List<Reserve> originalreserve;
    private List<Reserve> filteredreserve;
    private Context mContext;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;
    private DateFormat df;
    private ItemFilter mFilter = new ItemFilter();



    public BorrowListAdapter(Context context, List<Reserve> reserves) {
        this.mContext = context;
        this.originalreserve = reserves;
        this.filteredreserve = reserves;
        df = new SimpleDateFormat("MMM, d yyyy");

        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();

    }


    @Override
    public int getCount() {
        return filteredreserve.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredreserve.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.borrowlist, null);
        }

        TextView itemTitle = v.findViewById(R.id.itemName);
        final TextView itemId = v.findViewById(R.id.itemId);
        TextView borrowDate = v.findViewById(R.id.borrowdate);
        TextView returnDate = v.findViewById(R.id.returndate);
        final ImageView itemImg = v.findViewById(R.id.itemImg);

        itemTitle.setText(filteredreserve.get(position).getItemName());
        itemId.setText(filteredreserve.get(position).getItemId());
        borrowDate.setText(df.format(new Date(filteredreserve.get(position).getBorrowDate())));
        returnDate.setText(df.format(new Date(filteredreserve.get(position).getReturnDate())));


        firebaseDatabase.getReference().child("Items").child(filteredreserve.get(position).getItemId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Item item = snapshot.getValue(Item.class);

                firebaseStorage.getReference().child(item.getItemUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(mContext).load(uri).into(itemImg);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




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

            final List<Reserve> list = originalreserve;

            int count = list.size();
            final ArrayList<Reserve> nlist = new ArrayList<Reserve>(count);

            Reserve filterablereserve;

            for (int i = 0; i < count; i++) {
                filterablereserve = list.get(i);
                if (filterablereserve.getItemName().toLowerCase().contains(filterString) || filterablereserve.getItemId().toLowerCase().startsWith(filterString)) {
                    nlist.add(filterablereserve);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredreserve= (ArrayList<Reserve>) results.values;
            notifyDataSetChanged();
        }

    }
}
