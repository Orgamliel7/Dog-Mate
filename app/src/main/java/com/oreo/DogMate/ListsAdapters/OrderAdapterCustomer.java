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

public class OrderAdapterCustomer extends ArrayAdapter<Order> {

    private Activity context;
    private List<Order> ordersList;

    public OrderAdapterCustomer(Activity context, List<Order> ordersList) {
        super(context, R.layout.order_item_customer, ordersList);
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.order_item_customer, null, true);
        TextView B_phone = listViewItem.findViewById(R.id.bakerPhone);
        TextView B_name = listViewItem.findViewById(R.id.bakerName);
        TextView B_email = listViewItem.findViewById(R.id.bakerEmail);
        TextView B_City = listViewItem.findViewById(R.id.bakerCity);
        TextView B_Street = listViewItem.findViewById(R.id.bakerStreet);
        TextView B_House = listViewItem.findViewById(R.id.bakerNumHouse);
        TextView B_floor = listViewItem.findViewById(R.id.bakerFloor);
        TextView B_appartment = listViewItem.findViewById(R.id.bakerAppartment);
        TextView date = listViewItem.findViewById(R.id.dateOrder);
        TextView pay = listViewItem.findViewById(R.id.PayOrder);
        TextView recievedOrder = listViewItem.findViewById(R.id.recievedOrder);
        TextView namePastry = listViewItem.findViewById(R.id.namePastry);
        TextView comments = listViewItem.findViewById(R.id.Comment);

        Order order = ordersList.get(position);
        Pastry pastry = ordersList.get(position).getPastry();
        B_email.setText("מייל האופה: " +order.getBaker().getEmail());
        B_phone.setText("טלפון האופה: " +order.getBaker().getPhone());
        B_name.setText("שם האופה: " +order.getBaker().getFull_name());
        B_City.setText("עיר: " +order.getBaker().getAddress().getCity());
        B_Street.setText("רחוב: " +order.getBaker().getAddress().getStreetName());
        B_House.setText("מספר בית: " +order.getBaker().getAddress().getBuildingNumber());
        B_floor.setText("קומה: " +order.getBaker().getAddress().getFloor());
        B_appartment.setText("דירה: " +order.getBaker().getAddress().getAppartmentNumber());
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