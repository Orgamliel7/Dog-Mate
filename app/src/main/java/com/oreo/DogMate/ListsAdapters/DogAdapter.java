package com.oreo.DogMate.ListsAdapters;

import android.annotation.SuppressLint;
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
    private List<Dog> dogs;

    public DogAdapter(Activity context, List<Dog> dogs ){
            super(context, R.layout.list_dog_item,dogs);
        this.context = context;
        this.dogs = dogs;

    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =  context.getLayoutInflater();
        @SuppressLint("ViewHolder") View listViewItem = inflater.inflate(R.layout.list_dog_item, null, true);

        TextView name = listViewItem.findViewById(R.id.dogName);
        TextView age = listViewItem.findViewById(R.id.age);
        TextView region = listViewItem.findViewById(R.id.region);
        TextView gender = listViewItem.findViewById(R.id.gender);

        Dog dog = dogs.get(position);
        age.setText("גיל: "+ dog.getAge().name());
        name.setText("שם: "+ dog.getName());
        region.setText("איזור בארץ: "+ dog.getRegion().name());
        gender.setText("מין: "+ dog.getGender().name());

        return listViewItem;
    }

    @Override
    public int getCount() {
        if(dogs == null) return 0;
        else return dogs.size();
    }
}
