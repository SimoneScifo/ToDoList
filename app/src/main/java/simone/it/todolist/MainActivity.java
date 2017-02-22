package simone.it.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import static simone.it.todolist.AddActivity.ADD_BODY;
import static simone.it.todolist.AddActivity.ADD_DATE;
import static simone.it.todolist.AddActivity.ADD_TITLE;
import static simone.it.todolist.AddActivity.EDIT_BODY;
import static simone.it.todolist.AddActivity.EDIT_DATE;
import static simone.it.todolist.AddActivity.EDIT_TITLE;
import static simone.it.todolist.AddActivity.REQUEST_EDIT;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static int REQUEST_ADD = 1001;



    TextView titleTV, expiration_dateTV, bodyTV;
    RecyclerView noteRV;
    LinearLayoutManager layoutManager;
    NoteAdapter adapter;
    Intent intent;
    FloatingActionButton btn_add;

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
        adapter.setDataSet(getNotes());


        btn_add = (FloatingActionButton) findViewById(R.id.floating_add_button);
        btn_add.setOnClickListener(MainActivity.this);
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

    private ArrayList<Note> getNotes(){
        ArrayList<Note> lists = new ArrayList<>();
        Note note1 = new Note ("Ritirare i vestiti","Ricordati di prendere i vestiti", "25/02/2017");
        Note note2 = new Note ("Mangiare","Ricordati di Mangiare", "16/09/2017");
        Note note3 = new Note ("Studiare","Ricordati di studiare", "15/07/2017");
        Note note4 = new Note ("Lavare","Ricordati di lavarti", "25/05/2017");
        lists.add(note1);
        lists.add(note2);
        lists.add(note3);
        lists.add(note4);

        return lists;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.floating_add_button){
            Intent intent = new Intent(v.getContext(),AddActivity.class);
            ((Activity) v.getContext()).startActivityForResult(intent, REQUEST_ADD);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_ADD && resultCode== RESULT_OK){

                String title = (data.getStringExtra(ADD_TITLE));
                String body=(data.getStringExtra(ADD_BODY));
                String date=(data.getStringExtra(ADD_DATE));
                Note note = new Note(title,body,date);
                adapter.addNote(note);
                noteRV.scrollToPosition(0);
            }
        else if (requestCode == REQUEST_EDIT && resultCode == RESULT_OK){
                String title = (data.getStringExtra(EDIT_TITLE));
                String body=(data.getStringExtra(EDIT_BODY));
                String date=(data.getStringExtra(EDIT_DATE));
                Note note = new Note(title,body,date);
                adapter.editNote(note, adapter.getPosition());
                noteRV.scrollToPosition(0);
            }

    }


}
