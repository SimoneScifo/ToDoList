package simone.it.todolist;

import android.widget.TextView;

import java.util.Date;


/**
 * Created by Simone on 20/02/2017.
 */

public class Note {

    private String title;
    private String body;
    private int isSpecial;
    private Date createdAt;
    private Date modifiedAt;
    private String expiration_date;
    private int id;


    /*public Note(String title, String body, String expiration_date) {
        this.title = title;
        this.body = body;
        this.expiration_date = expiration_date;
    }*/
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getSpecial() {
        return isSpecial;
    }

    public void setSpecial(int isSpecial) {
        this.isSpecial = isSpecial;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
