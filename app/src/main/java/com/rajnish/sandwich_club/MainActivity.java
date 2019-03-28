package com.rajnish.sandwich_club;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpListView();
    }

    private void setUpListView() {
        listView = findViewById(R.id.sandwiches_listview);
        String sandWiches[] = getResources().getStringArray(R.array.sandwich_names);

        ArrayAdapter<String> sandwichArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,sandWiches);

        listView.setAdapter(sandwichArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchDetailsScreen(position);
            }
        });
    }

    private void launchDetailsScreen(int position) {
        Intent detailIntent = new Intent(this,DetailsActivity.class);
        detailIntent.putExtra(DetailsActivity.EXTRA_POSITION,position);
        startActivity(detailIntent);
    }
}
