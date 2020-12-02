package com.oreo.DogMate.ListsAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oreo.DogMate.Objects.Dog;
import com.oreo.DogMate.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DogAdapter extends ArrayAdapter<Dog> {
    private Activity context;
    private List<Dog> pastries;

    public DogAdapter(Activity context, List<Dog> pastries ){
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

        Dog dog = pastries.get(position);
        price.setText("מחיר: "+ dog.getPrice());
        name.setText("שם: "+ dog.getName());
        allerganics.setText("רכיבים אלרגניים: "+ dog.getAllerganics());
        description.setText("תיאור: "+ dog.getDescription());

        return listViewItem;
    }

    @Override
    public int getCount() {
        if(pastries == null) return 0;
        else return pastries.size();
    }
}
