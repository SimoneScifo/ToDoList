package simone.it.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Locale;

import static simone.it.todolist.MainActivity.NOTE_BODY_KEY;
import static simone.it.todolist.MainActivity.NOTE_DATE_KEY;
import static simone.it.todolist.MainActivity.NOTE_SPECIAL_KEY;
import static simone.it.todolist.MainActivity.NOTE_TITLE_KEY;
import static simone.it.todolist.MainActivity.REQUEST_EDIT;
import static simone.it.todolist.R.id.expiration_dateET;
import static simone.it.todolist.R.id.image_star;

/**
 * Created by Simone on 20/02/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    EditText titleET, expirationDateET, bodyET;
    Calendar myCalendar = Calendar.getInstance();
    ImageView starImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        titleET = (EditText) findViewById(R.id.titleET);
        expirationDateET= (EditText) findViewById(expiration_dateET);
        bodyET=(EditText) findViewById(R.id.body_ET);
        expirationDateET.setOnClickListener(this);
        //starImage.findViewById(R.id.image_star);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);//Attacco la toolbar al layout
        setSupportActionBar(toolbar);
        if(null != getSupportActionBar()){
            //setto la toolbar all'activity
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            if (getIntent() != null) {
                if (getIntent().getStringExtra(MainActivity.NOTE_TITLE_KEY) != null) {
                    titleET.setText(getIntent().getStringExtra(MainActivity.NOTE_TITLE_KEY));
                    bodyET.setText(getIntent().getStringExtra(MainActivity.NOTE_BODY_KEY));
                    expirationDateET.setText(getIntent().getStringExtra(MainActivity.NOTE_DATE_KEY));
                }
            }

            }
        }
    @Override
    // Inflate the menu; Questo aggiunge item nell'action bar se sono presenti.
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_add) {
            switch (id){
                case R.id.action_add:
            Intent intent = new Intent();
            intent.putExtra(NOTE_TITLE_KEY, titleET.getText().toString());
            intent.putExtra(NOTE_BODY_KEY, bodyET.getText().toString());
            intent.putExtra(NOTE_DATE_KEY, expirationDateET.getText().toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
            return true;
                case R.id.action_special:
                    Intent i = new Intent();
                    //i.putExtra(NOTE_SPECIAL_KEY, starImage.setVisibility());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
       if (v.getId() == expiration_dateET) {
            new DatePickerDialog(AddActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);

        expirationDateET.setText(sdf.format(myCalendar.getTime()));
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
