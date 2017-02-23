package simone.it.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static int REQUEST_ADD = 1001;
    public static int REQUEST_EDIT = 1002;
    final static String NOTE_TITLE_KEY = "NOTE_BODY_TITLE";
    final static String NOTE_BODY_KEY = "NOTE_BODY_KEY";
    final static String NOTE_DATE_KEY = "NOTE_DATE_KEY";

    Note editingNote;

    TextView titleTV, expiration_dateTV, bodyTV;
    RecyclerView noteRV;
    LinearLayoutManager layoutManager;
    NoteAdapter adapter;
    Intent intent;
    FloatingActionButton btn_add;

    Note note = new Note();

    DatabaseHandler database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent=getIntent();

        titleTV = (TextView) findViewById(R.id.titleTV);
        expiration_dateTV = (TextView) findViewById(R.id.expiration_dateTV);
        bodyTV = (TextView) findViewById(R.id.body_tv);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);//Attacco la toolbar al layout
        setSupportActionBar(toolbar);//setto la toolbar all'activity

        noteRV = (RecyclerView) findViewById(R.id.list_RV);
        layoutManager = new LinearLayoutManager(this);
        adapter = new NoteAdapter();
        noteRV.setLayoutManager(layoutManager);
        noteRV.setAdapter(adapter);


        btn_add = (FloatingActionButton) findViewById(R.id.floating_add_button);
        btn_add.setOnClickListener(MainActivity.this);

        database = new DatabaseHandler(this);
        adapter.setDataSet(database.getAllNotes());

    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.floating_add_button){
            Intent intent = new Intent(v.getContext(), simone.it.todolist.AddActivity.class);
            ((Activity) v.getContext()).startActivityForResult(intent, REQUEST_ADD);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_ADD && resultCode== RESULT_OK){
                note.setTitle(data.getStringExtra(NOTE_TITLE_KEY));
                note.setBody(data.getStringExtra(NOTE_BODY_KEY));
                note.setExpiration_date(data.getStringExtra(NOTE_DATE_KEY));
                noteRV.scrollToPosition(0);
                database.addNote(note);
                adapter.addNote(note);
            }
        else if (requestCode == REQUEST_EDIT && resultCode == RESULT_OK){
                editingNote.setTitle(data.getStringExtra(NOTE_TITLE_KEY));
                editingNote.setBody(data.getStringExtra(NOTE_BODY_KEY));
                noteRV.scrollToPosition(0);
                adapter.updateNote(editingNote,adapter.getPosition());
                database.updateNote(editingNote);
            }

    }


}
