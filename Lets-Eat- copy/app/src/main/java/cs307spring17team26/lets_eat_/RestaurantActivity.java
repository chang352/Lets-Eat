package com.example.android.restaurantactivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.ListActivity;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

public class RestaurantActivity extends ListActivity {

    private ArrayList<String> restaurantList = new ArrayList<>();
    private TextView selection;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        list = (ListView)findViewById(android.R.id.list);
        restaurantList.add("In-n-Out");
        restaurantList.add("La Victoria Tacqueria");
        restaurantList.add("Brittania Arms");
        restaurantList.add("Shabuway");

        setListAdapter(new ArrayAdapter<>(this, R.layout.row, R.id.label, restaurantList));
        selection = (TextView)findViewById(R.id.info);
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        list.setVisibility(View.INVISIBLE);
        selection.setVisibility(View.VISIBLE);
        selection.setText(restaurantList.get(position));
    }

}
