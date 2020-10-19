package com.cegep.lanbow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Student;

import java.util.ArrayList;
import java.util.List;

public class UserlistAdapter extends BaseAdapter implements Filterable {

    private List<Student> originalstudents;
    private List<Student> filteredstudents;
    private Context mContext;



    private ItemFilter mFilter = new ItemFilter();

    public UserlistAdapter(Context context,List<Student> students) {
        this.originalstudents = students;
        this.filteredstudents = students;
        mContext = context;
    }


    @Override
    public int getCount() {
        return filteredstudents.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredstudents.get(position);
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
            v = vi.inflate(R.layout.userlist, null);
        }


        TextView userName = v.findViewById(R.id.userName);
        TextView userId = v.findViewById(R.id.userId);

        userName.setText(filteredstudents.get(position).getName());
        userId.setText(filteredstudents.get(position).getStudentId());

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

            final List<Student> list = originalstudents;

            int count = list.size();
            final ArrayList<Student> nlist = new ArrayList<Student>(count);

          Student filterableStudent;

            for (int i = 0; i < count; i++) {
                filterableStudent= list.get(i);
                if (filterableStudent.getName().toLowerCase().contains(filterString) || filterableStudent.getStudentId().toLowerCase().startsWith(filterString)) {
                    nlist.add(filterableStudent);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredstudents = (ArrayList<Student>) results.values;
            notifyDataSetChanged();
        }

    }
}
