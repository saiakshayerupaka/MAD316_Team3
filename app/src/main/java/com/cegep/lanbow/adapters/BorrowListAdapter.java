package com.cegep.lanbow.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import java.util.Date;
import java.util.List;

public class BorrowListAdapter extends BaseAdapter {

    private List<Reserve> originalreserve;
    private List<Reserve> filteredreserve;
    private Context mContext;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;
    private DateFormat df;


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
}
