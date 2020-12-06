package com.oreo.DogMate.ListsAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oreo.DogMate.Objects.Advertiser;
import com.oreo.DogMate.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdvertiserAdapter extends ArrayAdapter<Advertiser> {
    private Activity context;
    private List<Advertiser> advertisers;

    public AdvertiserAdapter(Activity context, List<Advertiser> advertisers){
        super(context, R.layout.advertiser_item, advertisers);
        this.context = context;
        this.advertisers = advertisers;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =  context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.advertiser_item, null, true);

        TextView address = listViewItem.findViewById(R.id.BakerAddress);
        TextView name = listViewItem.findViewById(R.id.BakerName);


        Advertiser advertiser = advertisers.get(position);
        address.setText("עיר: "+ advertiser.getAddress().getCity());
        name.setText("שם המפרסם: "+ advertiser.getFull_name());

        return listViewItem;
    }

    @Override
    public int getCount() {
        if(advertisers == null) return 0;
        else return advertisers.size();
    }
}