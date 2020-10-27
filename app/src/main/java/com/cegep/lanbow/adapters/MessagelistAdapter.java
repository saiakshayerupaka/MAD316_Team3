package com.cegep.lanbow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Message;

import java.util.List;

public class MessagelistAdapter extends BaseAdapter {

    private List<Message> originalmessages;
    private List<Message> filteredmessages;
    private Context mContext;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.messagelist, null);
        }

        TextView MessageTitle = v.findViewById(R.id.messageTitle);
        TextView issueType = v.findViewById(R.id.issue_type);
        TextView studentId = v.findViewById(R.id.studentId);

        MessageTitle.setText(filteredmessages.get(position).getMessageTitle());
        issueType.setText(filteredmessages.get(position).getMessageType());

        return v;    }
}
