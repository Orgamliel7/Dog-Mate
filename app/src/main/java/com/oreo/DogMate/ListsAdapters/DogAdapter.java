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
    private List<Dog> dogs;

    public DogAdapter(Activity context, List<Dog> dogs ){
            super(context, R.layout.list_dog_item,dogs);
        this.context = context;
        this.dogs = dogs;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =  context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_dog_item, null, true);

        TextView age = listViewItem.findViewById(R.id.age);
        TextView name = listViewItem.findViewById(R.id.dogName);
        TextView allerganics = listViewItem.findViewById(R.id.allerganics);
        TextView description = listViewItem.findViewById(R.id.descript);

        Dog dog = dogs.get(position);
        age.setText("גיל: "+ dog.getAge());
        name.setText("גזע: "+ dog.getName());
        allerganics.setText("רכיבים אלרגניים: "+ dog.getAllerganics());
        description.setText("תיאור: "+ dog.getDescription());

        return listViewItem;
    }

    @Override
    public int getCount() {
        if(dogs == null) return 0;
        else return dogs.size();
    }
}
