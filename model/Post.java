package model;

import java.util.Date;

public class Post {
    public String title;
    public String text;
    public Date date;

    public Post(String title, String text, Date date) {
        this.title = title;
        this.text = text;
        this.date = date;
    }
}
