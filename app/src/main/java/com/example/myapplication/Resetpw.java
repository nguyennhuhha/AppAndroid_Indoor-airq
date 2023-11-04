package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Resetpw extends AppCompatActivity {
    private Button rsback,reset_btn;
    private EditText et_username,et_email,et_newpass,et_confnp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpw);

        rsback = (Button) findViewById(R.id.back_btn);
        reset_btn = (Button) findViewById(R.id.resetpw_button);
        et_username = findViewById(R.id.repw_username);
        et_email = findViewById(R.id.repw_email);
        et_newpass = findViewById(R.id.repw_password);
        et_confnp = findViewById(R.id.repw_confirm);
        rsback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Resetpw.this, MainActivity.class);
                startActivity(intent);
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernametxt = et_username.getText().toString();
                final String emailtxt = et_email.getText().toString();
                final String newpasstxt = et_newpass.getText().toString();
                final String confnptxt = et_confnp.getText().toString();
                if (usernametxt.isEmpty() || emailtxt.isEmpty() || newpasstxt.isEmpty() || confnptxt.isEmpty()) {
                    Toast.makeText(Resetpw.this, "Please fill all fields ", Toast.LENGTH_SHORT).show();
                } else {
                        // xu ly

                }
            }
        });
    }

}