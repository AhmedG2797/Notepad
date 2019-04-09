package com.example.android.notepad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewItem extends AppCompatActivity {

    int id;
    DBnotepad db;
    TextView textTitle, textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        id = getIntent().getIntExtra("id", 0);

        db = new DBnotepad(this);

        Item item = db.getNoteById(id);

        textTitle = findViewById(R.id.Title);
        textContent = findViewById(R.id.Content);

        textTitle.setText(item.getTitle());
        textContent.setText(item.getContent());

    }
}
