package com.cegep.lenbow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.cegep.lenbow.R;
import com.cegep.lenbow.models.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * user list adapter
 * @author dipmal lakhani
 * @author Sai Akshay
 * @author Gopichand
 * @author HarshaVardhan
 * @author Vinay
 * @author prashant
 * @author Amandeep singh
 */


public class UserlistAdapter extends BaseAdapter implements Filterable {

   /**
     * list originalstudents attribute
     */
    private List<Student> originalstudents;
    /**
     * list filteredstudents attribute
     */
    private List<Student> filteredstudents;
    /**
     * mContext object
     */
    private Context mContext;
    /**
     *  object of ItemFilter
     */

    private ItemFilter mFilter = new ItemFilter();

    public UserlistAdapter(Context context,List<Student> students) {
        this.originalstudents = students;
        this.filteredstudents = students;
        mContext = context;
    }

    /**
     * This method returns the count of students
     */
    @Override
    public int getCount() {
        return filteredstudents.size();
    }

    /**
     * This method gets the filteredstudents
     */
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
    /**
     * This method filters from the list
     */
    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        /**
         * This method performs filtering based on the characters typed
         */
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
        /**
         * This method shows the filteredresults and notifies about the changes made
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredstudents = (ArrayList<Student>) results.values;
            notifyDataSetChanged();
        }



    }
}
