package com.cegep.lenbow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cegep.lenbow.R;
import com.cegep.lenbow.models.Message;
import com.cegep.lenbow.models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessagelistAdapter extends BaseAdapter implements Filterable {

    private List<Message> originalmessages;
    private List<Message> filteredmessages;
    private Context mContext;
    private FirebaseDatabase database;
    private ItemFilter mFilter = new ItemFilter();


    public MessagelistAdapter(List<Message> originalmessages, Context mContext) {
        this.originalmessages = originalmessages;
        this.filteredmessages = originalmessages;
        this.mContext = mContext;
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public int getCount() {
        return filteredmessages.size();
    }

    @Override
    public Message getItem(int position) {
        return filteredmessages.get(position);
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
            v = vi.inflate(R.layout.messagelist, null);
        }

        TextView MessageTitle = v.findViewById(R.id.messageTitle);
        TextView issueType = v.findViewById(R.id.issue_type);
        final TextView studentId = v.findViewById(R.id.studentId);
        ImageView newMessage = v.findViewById(R.id.newMessage);

        if(filteredmessages.get(position).getMessageStatus().equals("unread")){
            newMessage.setVisibility(View.VISIBLE);
        }
        else{
            newMessage.setVisibility(View.GONE);
        }

        database.getReference().child("Users").child(filteredmessages.get(position).getMessageBy()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentId.setText(snapshot.getValue(Student.class).getStudentId());
                filteredmessages.get(position).setStudentId(snapshot.getValue(Student.class).getStudentId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        MessageTitle.setText(filteredmessages.get(position).getMessageTitle());
        issueType.setText(filteredmessages.get(position).getMessageType());

        return v;    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Message> list = originalmessages;

            int count = list.size();
            final ArrayList<Message> nlist = new ArrayList<Message>(count);

            Message filterableMessgae;

            for (int i = 0; i < count; i++) {
                filterableMessgae = list.get(i);
                if (filterableMessgae.getMessageTitle().toLowerCase().contains(filterString) || filterableMessgae.getStudentId().toLowerCase().startsWith(filterString) || filterableMessgae.getMessageType().toLowerCase().startsWith(filterString)) {
                    nlist.add(filterableMessgae);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredmessages = (ArrayList<Message>) results.values;
            notifyDataSetChanged();
        }

    }
}
