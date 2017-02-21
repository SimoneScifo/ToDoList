package simone.it.todolist;

import android.widget.TextView;


/**
 * Created by Simone on 20/02/2017.
 */
enum State {
        ACTIVE, COMPLETE, WAIT;
                }
public class Note {

    private String title, creation_date, last_modify, body, expiration_date;
    private State state;

    public Note(String title, String body, String expiration_date) {
        this.title = title;
        //this.creation_date = creation_date;
        //this.last_modify = last_modify;
        this.body = body;
        this.expiration_date = expiration_date;
        //this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getLast_modify() {
        return last_modify;
    }

    public void setLast_modify(String last_modify) {
        this.last_modify = last_modify;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


}
