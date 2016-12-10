package com.compilation.demos.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.compilation.R;
import com.compilation.mainApp.HolderActivity;

public class Activity extends HolderActivity {

    /**
     * Just a simple login screen using material design
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //editTexts to get input
        final EditText userName = (EditText) findViewById(R.id.user_edit_text);
        final EditText password = (EditText) findViewById(R.id.password_edit_text);

        (findViewById(R.id.floatingActionButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //consider calling to server
                String user = userName.getText().toString();
                String pass = password.getText().toString();
                Toast.makeText(getApplicationContext(), "Call to server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}