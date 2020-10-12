package com.cegep.lanbow.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import java.util.List;

public class HomeItemListAdapter extends BaseAdapter {
    private List<Item> originalitems;
    private List<Item> filtereditems;
    private Context mContext;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;


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
            v = vi.inflate(R.layout.itemlist, null);
        }


        TextView itemName = v.findViewById(R.id.itemName);
        TextView itemId = v.findViewById(R.id.itemId);
        final ImageView itemImg = v.findViewById(R.id.itemImg);
        ImageView delete = v.findViewById(R.id.removeItem);

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
}
