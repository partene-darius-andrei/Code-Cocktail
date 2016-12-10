package com.compilation.demos.login;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        (findViewById(R.id.floatingActionButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //consider calling to server
                String user = userName.getText().toString();
                String pass = password.getText().toString();

                Toast.makeText(getApplicationContext(), "Call to server", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);
                progressBar.bringToFront();

                //delayed responde (for fake preview)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No response", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }, 3000);
            }
        });
    }
}