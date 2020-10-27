package com.cegep.lanbow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Message;
import com.cegep.lanbow.models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MessagelistAdapter extends BaseAdapter {

    private List<Message> originalmessages;
    private List<Message> filteredmessages;
    private Context mContext;
    private FirebaseDatabase database;

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

        database.getReference().child("Users").child(filteredmessages.get(position).getMessageBy()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentId.setText(snapshot.getValue(Student.class).getStudentId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        MessageTitle.setText(filteredmessages.get(position).getMessageTitle());
        issueType.setText(filteredmessages.get(position).getMessageType());

        return v;    }
}
