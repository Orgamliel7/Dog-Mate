package com.oreo.DogMate.ListsAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oreo.DogMate.R;
import com.oreo.DogMate.Objects.Pastry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PastryAdapter extends ArrayAdapter<Pastry> {
    private Activity context;
    private List<Pastry> pastries;

    public PastryAdapter(Activity context, List<Pastry> pastries ){
            super(context, R.layout.list_pastry_item,pastries);
        this.context = context;
        this.pastries = pastries;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =  context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_pastry_item, null, true);

        TextView price = listViewItem.findViewById(R.id.price);
        TextView name = listViewItem.findViewById(R.id.pastryName);
        TextView allerganics = listViewItem.findViewById(R.id.allerganics);
        TextView description = listViewItem.findViewById(R.id.descript);

        Pastry pastry = pastries.get(position);
        price.setText("מחיר: "+pastry.getPrice());
        name.setText("שם: "+pastry.getName());
        allerganics.setText("רכיבים אלרגניים: "+pastry.getAllerganics());
        description.setText("תיאור: "+pastry.getDescription());

        return listViewItem;
    }

    @Override
    public int getCount() {
        if(pastries == null) return 0;
        else return pastries.size();
    }
}
