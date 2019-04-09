package com.example.android.notepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class Add_Item extends AppCompatActivity {

    int intentTYPE, id;
    EditText editTitle, editContent;
    DBnotepad db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__item);

        db = new DBnotepad(this);

        editTitle = findViewById(R.id.Title);
        editContent = findViewById(R.id.Content);

        intentTYPE = getIntent().getIntExtra("t", 0);
        if (intentTYPE == 1) {

            setTitle(getString(R.string.ADD));

        } else if (intentTYPE == 2) {

            setTitle(getString(R.string.UPDATE));
            id = getIntent().getIntExtra("id", 0);

            Item item = db.getNoteById(id);

            editTitle.setText(item.getTitle());
            editContent.setText(item.getContent());

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.save_item, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.save):
                if (intentTYPE == 1) {

                    String title = editTitle.getText().toString();
                    String content = editContent.getText().toString();

                    Item newItem = new Item(title, content);

                    db.addItem(newItem);

                    finish();

                } else if (intentTYPE == 2) {

                    String title = editTitle.getText().toString();
                    String content = editContent.getText().toString();

                    Item newItem = new Item(id, title, content);

                    db.updateNote(newItem);

                    finish();

                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
