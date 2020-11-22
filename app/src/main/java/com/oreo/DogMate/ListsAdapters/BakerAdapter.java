package com.oreo.DogMate.ListsAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oreo.DogMate.R;
import com.oreo.DogMate.Objects.Baker;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BakerAdapter extends ArrayAdapter<Baker> {
    private Activity context;
    private List<Baker> bakers;

    public BakerAdapter(Activity context, List<Baker> bakers ){
        super(context, R.layout.baker_item,bakers);
        this.context = context;
        this.bakers = bakers;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =  context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.baker_item, null, true);

        TextView address = listViewItem.findViewById(R.id.BakerAddress);
        TextView name = listViewItem.findViewById(R.id.BakerName);


        Baker baker = bakers.get(position);
        address.setText("עיר: "+baker.getAddress().getCity());
        name.setText("שם האופה: "+baker.getFull_name());

        return listViewItem;
    }

    @Override
    public int getCount() {
        if(bakers == null) return 0;
        else return bakers.size();
    }
}