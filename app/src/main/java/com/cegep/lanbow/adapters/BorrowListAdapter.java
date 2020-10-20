package com.cegep.lanbow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Item;
import com.cegep.lanbow.models.Reserve;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Date;
import java.util.List;

public class BorrowListAdapter extends BaseAdapter {

    private List<Reserve> originalreserve;
    private List<Reserve> filteredreserve;
    private Context mContext;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;

    public BorrowListAdapter(Context context, List<Reserve> reserves) {
        this.mContext = context;
        this.originalreserve = reserves;
        this.filteredreserve = reserves;
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

        TextView itemTitle = v.findViewById(R.id.itemTitle);
        TextView itemId = v.findViewById(R.id.itemId);
        TextView borrowDate = v.findViewById(R.id.borrowdate);
        TextView returnDate = v.findViewById(R.id.returndate);
        TextView userId = v.findViewById(R.id.userId);
        ImageView itemImg = v.findViewById(R.id.itemImg);

        itemTitle.setText(filteredreserve.get(position).getItemName());
        itemId.setText(filteredreserve.get(position).getItemId());
        borrowDate.setText(new Date(filteredreserve.get(position).getBorrowDate()));
        returnDate.setText(new Date(filteredreserve.get(position).getReturnDate()));

        return v;
    }
}
