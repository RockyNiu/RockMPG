package com.rockyniu.calculatempg.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rockyniu.calculatempg.R;
import com.rockyniu.calculatempg.model.Record;
import com.rockyniu.calculatempg.util.StringHelper;

import java.util.List;

public class RecordListAdapter extends ArrayAdapter<Record> {

    private final List<Record> list;
    //	private final Context context;
    private static LayoutInflater inflater = null;

    public RecordListAdapter(Activity context, List<Record> list) {
        super(context, R.layout.row_record, list);
//		this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Record getItem(int position) {
        return list.get(position);
    }

    public void updateList(List<Record> newlist) {
        list.clear();
        list.addAll(newlist);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            if (position == 0) {
                view = inflater.inflate(R.layout.row_record_first, null);
            } else {
                view = inflater.inflate(R.layout.row_record, null);
            }
        }

        ImageView imagePriorityBar = (ImageView) view.findViewById(R.id.priority_bar);
        TextView milesTextView = (TextView) view.findViewById(R.id.record_row_miles);
        TextView gasTextView = (TextView) view.findViewById(R.id.record_row_gas);
        TextView mpgTextView = (TextView) view.findViewById(R.id.record_row_mpg);


        Record currentItem = list.get(position);

        float miles = currentItem.getMiles();
        float gas = currentItem.getGas();
        float mpg = 0;
        if (gas != 0) {
            mpg = miles / gas;
        }
        milesTextView.setText(StringHelper.formatFloat(miles));
        gasTextView.setText(StringHelper.formatFloat(gas));
        mpgTextView.setText(StringHelper.formatFloat(mpg));
        return view;
    }
}
