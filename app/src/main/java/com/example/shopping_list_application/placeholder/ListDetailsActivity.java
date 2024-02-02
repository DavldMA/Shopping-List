package com.example.shopping_list_application.placeholder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shopping_list_application.R;

public class ListDetailsActivity extends AppCompatActivity {


    ListView listViewData;
    ArrayAdapter<String> adapter;
    String[] arrayXD = {"Conteudo1", "conteudo2", "produto3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);
        listViewData = findViewById(R.id.lv_data);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arrayXD);
        listViewData.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item_done) {
            String itemSelected = "Selected items:";
            for(int i=0; i<listViewData.getCount(); i++) {
                if(listViewData.isItemChecked(i)) {
                    //AQUI FAZER LOGICA
                    itemSelected += listViewData.getItemAtPosition(i);
                }
            }
            Toast.makeText(this, itemSelected, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}