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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.cegep.lenbow.R;
import com.cegep.lenbow.models.Item;
import com.cegep.lenbow.models.Reserve;
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

/**
 * Borrowed item list adapter
 * @author dipmal lakhani @author prashant reddy nannuru
 */


public class BorrowListAdapter extends BaseAdapter implements Filterable {

    /**
     * list originalreserve attribute
     */
    private List<Reserve> originalreserve;
    /**
     * list filteredreserve attribute
     */
    private List<Reserve> filteredreserve;
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
    /**
     * dateformat object
     */
    private DateFormat df;
    /**
     * mfilter object is created
     */
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

    private class ViewHolder{
        TextView itemTitle;
        TextView itemId;
        TextView borrowDate;
        TextView returnDate;
        ImageView itemImg;
        LinearLayout cancel;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        final ViewHolder viewHolder = new ViewHolder();


            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.borrowlist, null);

        viewHolder.itemTitle = v.findViewById(R.id.itemName);
        viewHolder.itemId = v.findViewById(R.id.itemId);
        viewHolder.borrowDate = v.findViewById(R.id.borrowdate);
        viewHolder.returnDate = v.findViewById(R.id.returndate);
        viewHolder.itemImg = v.findViewById(R.id.itemImg);
        viewHolder.cancel = v.findViewById(R.id.cancelreserve);

        if(filteredreserve.get(position).getReserveId()==null) {
            viewHolder.cancel.setVisibility(View.GONE);
        }
        else {
            if (countDays(new Date(), new Date(filteredreserve.get(position).getBorrowDate())) >= 0) {
                viewHolder.cancel.setVisibility(View.GONE);
            } else {
                viewHolder.cancel.setVisibility(View.VISIBLE);
            }

            viewHolder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseDatabase.getReference().child("Reserve").child(filteredreserve.get(position).getReserveId()).removeValue();
                    Toast.makeText(mContext, "Reservation cancelled sucessfully", Toast.LENGTH_LONG).show();
                }
            });
        }



        viewHolder.itemTitle.setText(filteredreserve.get(position).getItemName());
        viewHolder.itemId.setText(filteredreserve.get(position).getItemId());
        viewHolder.borrowDate.setText(df.format(new Date(filteredreserve.get(position).getBorrowDate())));
        viewHolder.returnDate.setText(df.format(new Date(filteredreserve.get(position).getReturnDate())));


        firebaseDatabase.getReference().child("Items").child(filteredreserve.get(position).getItemId()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Item item = snapshot.getValue(Item.class);

                firebaseStorage.getReference().child(item.getItemUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(mContext).load(uri).into(viewHolder.itemImg);
                    }
                });
            }
            /**
             * This method displays Database error when data cannot be fetched from database
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        v.setTag(viewHolder);



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

    public int countDays(Date start,Date end){
        long diff =  start.getTime() - end.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

}
