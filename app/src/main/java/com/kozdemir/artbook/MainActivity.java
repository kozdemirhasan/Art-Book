package com.kozdemir.artbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> nameArray;
    ArrayList<Integer> idArray;
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        nameArray = new ArrayList<>();
        idArray = new ArrayList<>();



        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameArray);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("artId",idArray.get(position));
                intent.putExtra("info", "old");
                startActivity(intent);
            }
        });


        getData();


    }

    public void getData() {
        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Arts", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM arts", null);

            int nameIndex = cursor.getColumnIndex("artname");
            int idIndex = cursor.getColumnIndex("id");

            while (cursor.moveToNext()) {
                nameArray.add(cursor.getString(nameIndex));
                idArray.add(cursor.getInt(idIndex));

            }

            //list View e yaz
            arrayAdapter.notifyDataSetChanged();

            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflater
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_art, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_art_item) {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("info","new");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}