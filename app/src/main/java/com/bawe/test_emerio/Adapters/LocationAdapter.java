package com.bawe.test_emerio.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.bawe.test_emerio.Interfaces.IListItem;
import com.bawe.test_emerio.Models.LocationModel;
import com.bawe.test_emerio.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by poing on 8/28/18.
 */

public class LocationAdapter extends ArrayAdapter<Object> implements IListItem {

    private List<Object> dataSet;
    private List<Object> visibleDataSet;
    private Context mContext;

    private static class ViewHolder {
        TextView txtLocation;
    }


    public LocationAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        mContext = context;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return visibleDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return visibleDataSet == null? 0 : visibleDataSet.size();
    }

    @Override
    public void populate(List<Object> objects) {
        try {
            Log.d("TEST_EMERIO", "Populate Adapter...");
            dataSet = objects;
            visibleDataSet = new ArrayList<>(objects);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public List<Object> getDataSet() {
        return dataSet;
    }

    @Override
    public Object get(int index) {
        return dataSet.get(index);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        try {
            Log.d("TEST_EMERIO", "Adapter Get View...");
            LocationModel dataModel = (LocationModel) getItem(position);

            ViewHolder viewHolder;

            final View result;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.li_location_item, parent, false);
                viewHolder.txtLocation = (TextView) convertView.findViewById(R.id.label_list_location);

                result = convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            viewHolder.txtLocation.setText(dataModel.combine());
            // Return the completed view to render on screen
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;

    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        visibleDataSet.clear();
        if (charText.length() == 0) {
            visibleDataSet.addAll(dataSet);
        }
        else
        {
            for (Object obj : dataSet)
            {
                LocationModel loc = (LocationModel) obj;
                if (loc.combine().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    visibleDataSet.add(loc);
                }
            }
        }
        notifyDataSetChanged();
    }

}
