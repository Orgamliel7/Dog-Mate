package com.oreo.DogMate.ListsAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oreo.DogMate.Objects.Adoption;
import com.oreo.DogMate.R;
import com.oreo.DogMate.Objects.Dog;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdoptionAdapterAdvertiser extends ArrayAdapter<Adoption> {

    private Activity context;
    private List<Adoption> ordersList;

    public AdoptionAdapterAdvertiser(Activity context, List<Adoption> ordersList)  {
        super(context, R.layout.order_item_advertiser, ordersList);
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.order_item_advertiser, null, true);
        TextView C_phone = listViewItem.findViewById(R.id.customerPhone);
        TextView C_name = listViewItem.findViewById(R.id.customerName);
        TextView C_email = listViewItem.findViewById(R.id.cusEmail);
        TextView C_City = listViewItem.findViewById(R.id.customerCity);
        TextView C_Street = listViewItem.findViewById(R.id.customerStreet);
        TextView C_House = listViewItem.findViewById(R.id.customerNumHouse);
        TextView C_floor = listViewItem.findViewById(R.id.customerFloor);
        TextView C_appartment = listViewItem.findViewById(R.id.customerAppartment);
        TextView date = listViewItem.findViewById(R.id.dateOrder);
        TextView pay = listViewItem.findViewById(R.id.PayOrder);
        TextView recievedOrder = listViewItem.findViewById(R.id.recievedOrder);
        TextView namePastry = listViewItem.findViewById(R.id.namePastry);
        TextView comments = listViewItem.findViewById(R.id.Comment);

        Adoption adoption = ordersList.get(position);
        Dog dog = ordersList.get(position).getDog();
        C_email.setText("מייל הלקוח: " + adoption.getAdopter().getEmail());
        C_phone.setText("טלפון הלקוח: " + adoption.getAdopter().getPhone());
        C_name.setText("שם הלקוח: " + adoption.getAdopter().getFull_name());
        C_City.setText("עיר: " + adoption.getAdopter().getAddress().getCity());
        C_Street.setText("רחוב: " + adoption.getAdopter().getAddress().getStreetName());
        C_House.setText("מספר בית: " + adoption.getAdopter().getAddress().getBuildingNumber());
        C_floor.setText("קומה: " + adoption.getAdopter().getAddress().getFloor());
        C_appartment.setText("דירה: " + adoption.getAdopter().getAddress().getAppartmentNumber());
        date.setText("תאריך: " + adoption.getDate());
        namePastry.setText("שם המאפה: " + adoption.getDog().getName());
        comments.setText("הערות: " + adoption.getComments());
        if(adoption.isCard() == true){
            pay.setText("אמצעי תשלום: אשראי");
        }
        else {
            pay.setText("אמצעי תשלום: מזומן");
        }

        if (adoption.isDelivery()==true){
            recievedOrder.setText("קבלת ההזמנה: משלוח");
        }
        else{
            recievedOrder.setText("קבלת ההזמנה: איסוף עצמי");
        }
        return listViewItem;
    }
}
