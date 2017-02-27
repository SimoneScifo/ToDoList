package simone.it.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static int REQUEST_ADD = 1001;
    public static int REQUEST_EDIT = 1002;
    final static String NOTE_TITLE_KEY = "NOTE_BODY_TITLE";
    final static String NOTE_BODY_KEY = "NOTE_BODY_KEY";
    final static String NOTE_DATE_KEY = "NOTE_DATE_KEY";
    final static String NOTE_SPECIAL_KEY = "NOTE_SPECIAL_KEY";

    Note editingNote;

    TextView titleTV, expiration_dateTV, bodyTV;
    RecyclerView noteRV;
    RecyclerView.LayoutManager layoutManager;
    NoteAdapter adapter;
    Intent intent;
    FloatingActionButton btn_add;

    Note note = new Note();

    DatabaseHandler database;
    Toolbar toolbar;
    public ActionMode actionMode;
    //Costanti per modificare layout
    private static final String LAYOUT_MANAGER_KEY = "LAYOUT_MANAGER_KEY";
    private int STAGGERED_LAYOUT = 20;
    private int LINEAR_LAYOUT = 21;
    private int layoutManagerType = LINEAR_LAYOUT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();

        titleTV = (TextView) findViewById(R.id.titleTV);
        expiration_dateTV = (TextView) findViewById(R.id.expiration_dateTV);
        bodyTV = (TextView) findViewById(R.id.body_tv);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Attacco la toolbar al layout
        setSupportActionBar(toolbar);//setto la toolbar all'activity

        noteRV = (RecyclerView) findViewById(R.id.list_RV);
        layoutManager = getSavedLayoutManager();
        adapter = new NoteAdapter();
        noteRV.setLayoutManager(layoutManager);
        noteRV.setAdapter(adapter);

        btn_add = (FloatingActionButton) findViewById(R.id.floating_add_button);
        btn_add.setOnClickListener(MainActivity.this);


        database = new DatabaseHandler(this);
        adapter.setDataSet(database.getAllNotes());

    }

    private RecyclerView.LayoutManager getSavedLayoutManager() {
        SharedPreferences sharedPrefs = getSharedPreferences(getString(R.string.layout_key), Context.MODE_PRIVATE);
        int layoutManager = sharedPrefs.getInt(LAYOUT_MANAGER_KEY, -1);
        if (layoutManager == STAGGERED_LAYOUT) {
            setLayoutManagerType(layoutManager);
            return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        }
        if (layoutManager == LINEAR_LAYOUT) {
            setLayoutManagerType(layoutManager);
            return new LinearLayoutManager(this);
        }
        return new LinearLayoutManager(this);

    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.floating_add_button) {
            Intent intent = new Intent(v.getContext(), simone.it.todolist.AddActivity.class);
            ((Activity) v.getContext()).startActivityForResult(intent, REQUEST_ADD);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD && resultCode == RESULT_OK) {
            note.setTitle(data.getStringExtra(NOTE_TITLE_KEY));
            note.setBody(data.getStringExtra(NOTE_BODY_KEY));
            note.setExpiration_date(data.getStringExtra(NOTE_DATE_KEY));
            noteRV.scrollToPosition(0);
            database.addNote(note);
            adapter.addNote(note);
            Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_SHORT).show();

        } else if (requestCode == REQUEST_EDIT && resultCode == RESULT_OK) {
            editingNote.setTitle(data.getStringExtra(NOTE_TITLE_KEY));
            editingNote.setBody(data.getStringExtra(NOTE_BODY_KEY));
            editingNote.setExpiration_date(data.getStringExtra(NOTE_DATE_KEY));
            noteRV.scrollToPosition(0);
            adapter.updateNote(editingNote, adapter.getPosition());
            database.updateNote(editingNote);
            Toast.makeText(getApplicationContext(), "Edit success", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_layoutSTAG) {
            if (getLayoutManagerType() == STAGGERED_LAYOUT) {
                setLayoutManagerType(LINEAR_LAYOUT);
                noteRV.setLayoutManager(new LinearLayoutManager(this));
                item.setIcon(getDrawable(R.drawable.view_dashboard));


            }else{
                setLayoutManagerType(STAGGERED_LAYOUT);
                noteRV.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                item.setIcon(getDrawable(R.drawable.view_list));

            }

        }

        return super.onOptionsItemSelected(item);
    }
    public int getLayoutManagerType() {

        return layoutManagerType;
    }

    public void setLayoutManagerType(int layoutManagerType) {
        this.layoutManagerType = layoutManagerType;
    }



    //<editor-fold desc="action mode">

    //CREO MENU CON ONLONGCLICK
    public ActionMode.Callback callback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_toolbar, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }


        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    //remove record
                    database.deleteNote(adapter.getNote(adapter.getPosition()));
                    // remove from adapter
                    adapter.deleteNote(adapter.getPosition());
                    Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                    mode.finish(); // Action picked, so close the CAB
                    return true;

                case R.id.action_edit:
                    editingNote = adapter.getNote(adapter.getPosition());
                    Intent i = new Intent(MainActivity.this, AddActivity.class);
                    i.putExtra(NOTE_TITLE_KEY, editingNote.getTitle());
                    i.putExtra(NOTE_BODY_KEY, editingNote.getBody());
                    i.putExtra(NOTE_DATE_KEY, editingNote.getExpiration_date());
                    startActivityForResult(i, REQUEST_EDIT);
                    mode.finish();
                    return true;
            }
            return false;
        }



        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }

    };

    //</editor-fold>

    // Called when the user long-clicks on someView
    public boolean onLongClick(View view) {

        if (actionMode != null) {
            return false;
        }
        adapter.setPosition(adapter.getPosition());
        // Start the CAB using the ActionMode.Callback defined above
        actionMode = (this.startSupportActionMode(callback));
        view.setSelected(true);
        return true;
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
}

