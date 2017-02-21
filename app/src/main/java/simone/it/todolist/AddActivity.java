package simone.it.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Simone on 20/02/2017.
 */

public class AddActivity extends Activity implements View.OnClickListener {
    EditText titleET, expirationDateET, bodyET;
    Button btn_add;

    public static String ADD_TITLE= "ADD_TITLE";
    public static String ADD_BODY= "ADD_BODY";
    public static String ADD_DATE= "ADD_DATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        titleET = (EditText) findViewById(R.id.titleET);
        expirationDateET= (EditText) findViewById(R.id.expiration_dateET);
        bodyET=(EditText) findViewById(R.id.body_ET);
        btn_add = (Button) findViewById(R.id.btn_okADD);
        btn_add.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_okADD){
            Intent intent = new Intent ();
            intent.putExtra(ADD_TITLE,titleET.getText().toString());
            intent.putExtra(ADD_BODY,bodyET.getText().toString());
            intent.putExtra(ADD_DATE,expirationDateET.getText().toString());
           setResult(Activity.RESULT_OK, intent);
            finish();
        }
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
