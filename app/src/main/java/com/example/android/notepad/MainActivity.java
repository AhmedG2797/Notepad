package com.example.android.notepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBnotepad db;
    ListView listView;
    ItemAdapter itemAdapter;
    Item selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBnotepad(this);

        listView = findViewById(R.id.list_view);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedItem = (Item) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, ViewItem.class);
                intent.putExtra("id", selectedItem.getId());

                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Item> items = db.getAllNotes();

        itemAdapter = new ItemAdapter(this, items);

        listView.setAdapter(itemAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.add_note):

                Intent intent = new Intent(MainActivity.this, Add_Item.class);
                intent.putExtra("t", 1);
                startActivity(intent);

                break;
            case (R.id.delete_all):

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmation")
                        .setMessage("Are You sure to delete \"All Notes\" ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                db.deleteAllNotes();
                                onResume();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Select option");
        getMenuInflater().inflate(R.menu.context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {

            case (R.id.open):

                selectedItem = itemAdapter.getItem(info.position);
                Intent intent = new Intent(MainActivity.this, ViewItem.class);
                intent.putExtra("id", selectedItem.getId());

                startActivity(intent);

                break;
            case (R.id.edit):

                selectedItem = itemAdapter.getItem(info.position);
                Intent i = new Intent(MainActivity.this, Add_Item.class);
                i.putExtra("t", 2);
                i.putExtra("id", selectedItem.getId());
                startActivity(i);


                break;
            case (R.id.delete):

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmation")
                        .setMessage("Are You sure to delete this note ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                selectedItem = itemAdapter.getItem(info.position);
                                int id = selectedItem.getId();

                                db.deleteNote(id);
                                onResume();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                break;
        }

        return super.onContextItemSelected(item);
    }

}
