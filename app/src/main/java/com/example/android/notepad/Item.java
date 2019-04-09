package com.example.android.notepad;

public class Item {

    private int id;
    private String title, content;

    public Item(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Item(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
