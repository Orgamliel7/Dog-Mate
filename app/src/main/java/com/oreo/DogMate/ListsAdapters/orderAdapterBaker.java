package com.oreo.DogMate.ListsAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oreo.DogMate.Objects.Order;
import com.oreo.DogMate.R;
import com.oreo.DogMate.Objects.Pastry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class orderAdapterBaker extends ArrayAdapter<Order> {

    private Activity context;
    private List<Order> ordersList;

    public orderAdapterBaker(Activity context, List<Order> ordersList)  {
        super(context, R.layout.order_item_baker, ordersList);
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.order_item_baker, null, true);
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

        Order order = ordersList.get(position);
        Pastry pastry = ordersList.get(position).getPastry();
        C_email.setText("מייל הלקוח: " +order.getCustomer().getEmail());
        C_phone.setText("טלפון הלקוח: " +order.getCustomer().getPhone());
        C_name.setText("שם הלקוח: " +order.getCustomer().getFull_name());
        C_City.setText("עיר: " +order.getCustomer().getAddress().getCity());
        C_Street.setText("רחוב: " +order.getCustomer().getAddress().getStreetName());
        C_House.setText("מספר בית: " +order.getCustomer().getAddress().getBuildingNumber());
        C_floor.setText("קומה: " +order.getCustomer().getAddress().getFloor());
        C_appartment.setText("דירה: " +order.getCustomer().getAddress().getAppartmentNumber());
        date.setText("תאריך: " +order.getDate());
        namePastry.setText("שם המאפה: " +order.getPastry().getName());
        comments.setText("הערות: " +order.getComments());
        if(order.isCard() == true){
            pay.setText("אמצעי תשלום: אשראי");
        }
        else {
            pay.setText("אמצעי תשלום: מזומן");
        }

        if (order.isDelivery()==true){
            recievedOrder.setText("קבלת ההזמנה: משלוח");
        }
        else{
            recievedOrder.setText("קבלת ההזמנה: איסוף עצמי");
        }
        return listViewItem;
    }
}
