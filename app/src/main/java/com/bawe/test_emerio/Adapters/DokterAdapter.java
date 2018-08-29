package com.bawe.test_emerio.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawe.test_emerio.Interfaces.IListItem;
import com.bawe.test_emerio.Models.DokterModel;
import com.bawe.test_emerio.Models.LocationModel;
import com.bawe.test_emerio.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by poing on 8/28/18.
 */

public class DokterAdapter extends ArrayAdapter implements IListItem {
    public DokterAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    private List<Object> dataSet;
    private Context mContext;

    private static class ViewHolder {
        private ImageView imgPhoto;
        private TextView txtName;
        private TextView txtArea;
        private TextView txtSpeciality;
        private TextView txtCurrencyPrice;
    }



    @Nullable
    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return dataSet == null? 0 : dataSet.size();
    }

    @Override
    public void populate(List<Object> objects) {
        try {
            dataSet = objects;
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

        Log.d("TEST_EMERIO", "getView: STARTED!!!!");
        try {
            DokterModel dataModel = (DokterModel) getItem(position);

            ViewHolder viewHolder;

            final View result;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.li_dokter_item, parent, false);

                viewHolder.imgPhoto = (ImageView) convertView.findViewById(R.id.dokter_photo);
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.dokter_name);
                viewHolder.txtArea = (TextView) convertView.findViewById(R.id.dokter_area);
                viewHolder.txtSpeciality = (TextView) convertView.findViewById(R.id.dokter_type);
                viewHolder.txtCurrencyPrice = (TextView) convertView.findViewById(R.id.dokter_price);


                result = convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            viewHolder.txtName.setText(dataModel.getName());
            viewHolder.txtArea.setText(dataModel.getArea());
            viewHolder.txtSpeciality.setText(dataModel.getSpeciality());
            viewHolder.txtCurrencyPrice.setText(dataModel.getCurrencyPrice());
            // Return the completed view to render on screen
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;

    }
}
