package com.example.shopping_list_application.placeholder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
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
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_layout, R.id.checkTextView, arrayXD) {
            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                Button buttonDelete = view.findViewById(R.id.buttonDelete);

                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //HANDLE DELETE HERE ALEX
                    }
                });

                return view;
            }
        };
        listViewData.setAdapter(adapter);

        listViewData.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(R.id.checkTextView);
                checkedTextView.setChecked(!checkedTextView.isChecked());
                Log.i("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "" + checkedTextView.isChecked());
            }
        });

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