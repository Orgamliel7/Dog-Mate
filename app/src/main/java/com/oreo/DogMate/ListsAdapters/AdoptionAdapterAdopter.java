package com.oreo.DogMate.ListsAdapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oreo.DogMate.Objects.Adoption;
import com.oreo.DogMate.Objects.Dog;
import com.oreo.DogMate.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdoptionAdapterAdopter extends ArrayAdapter<Adoption> {

    private Activity context;
    private List<Adoption> ordersList;

    public AdoptionAdapterAdopter(Activity context, List<Adoption> ordersList) {
        super(context, R.layout.adoption_item_adopter, ordersList);
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View listViewItem = inflater.inflate(R.layout.adoption_item_adopter, null, true);
        TextView B_phone = listViewItem.findViewById(R.id.advertiserPhone);
        TextView B_name = listViewItem.findViewById(R.id.advertiserName);
        TextView B_email = listViewItem.findViewById(R.id.advertiserEmail);
        TextView B_City = listViewItem.findViewById(R.id.advertiserCity);

        //TextView B_appartment = listViewItem.findViewById(R.id.advertiserAppartment);
        TextView date = listViewItem.findViewById(R.id.dateOrder);
        TextView nameDog = listViewItem.findViewById(R.id.dog_name);
        TextView comments = listViewItem.findViewById(R.id.Comment);

        Adoption adoption = ordersList.get(position);
        Dog dog = ordersList.get(position).getDog();
        B_email.setText("מייל המפרסם: " + adoption.getAdvertiser().getEmail());
        B_phone.setText("טלפון המפרסם: " + adoption.getAdvertiser().getPhone());
        B_name.setText("שם המפרסם: " + adoption.getAdvertiser().getFull_name());
        B_City.setText("עיר: " + adoption.getAdvertiser().getAddress().getCity());
        date.setText("תאריך: " + adoption.getDate());
        nameDog.setText("שם הכלב: " + adoption.getDog().getName());
        comments.setText(adoption.getRequest());

        return listViewItem;
    }
}