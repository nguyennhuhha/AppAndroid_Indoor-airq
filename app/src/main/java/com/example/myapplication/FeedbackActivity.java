package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.FeedbackHandler;
import com.example.myapplication.Model.Feedback;
import com.example.myapplication.Model.User;

import java.util.Calendar;

public class FeedbackActivity extends AppCompatActivity {
    private ImageView back;
    private Button submit;
    private CheckBox cbOver1, cbOver2, cbOver3, cbTemp1, cbTemp2,
            cbTemp3, cbWind1, cbWind2, cbWind3, cbOther1, cbOther2, cbOther3;

    private TextView cbOver11, cbOver22, cbOver33, cbTemp11, cbTemp22,
            cbTemp33, cbWind11, cbWind22, cbWind33, cbOther11, cbOther22, cbOther33;
    String overall = "";
    String temp= "";
    String wind= "";
    String other = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        back = findViewById(R.id.btnBack);
        submit = findViewById(R.id.submit);

        InitViews();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //send fb
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(v);
            }
        });
    }
    private void InitViews(){
        cbOver1 = findViewById(R.id.cbOver1);
        cbOver2 = findViewById(R.id.cbOver2);
        cbOver3 = findViewById(R.id.cbOver3);

        cbTemp1 = findViewById(R.id.cbTemp1);
        cbTemp2 = findViewById(R.id.cbTemp2);
        cbTemp3 = findViewById(R.id.cbTemp3);

        cbWind1 = findViewById(R.id.cbWind1);
        cbWind2 = findViewById(R.id.cbWind2);
        cbWind3 = findViewById(R.id.cbWind3);

        cbOther1 = findViewById(R.id.cbOther1);
        cbOther2 = findViewById(R.id.cbOther2);
        cbOther3 = findViewById(R.id.cbOther3);

        cbOver11 = findViewById(R.id.cbOver11);
        cbOver22 = findViewById(R.id.cbOver22);
        cbOver33 = findViewById(R.id.cbOver33);

        cbTemp11 = findViewById(R.id.cbTemp11);
        cbTemp22 = findViewById(R.id.cbTemp22);
        cbTemp33 = findViewById(R.id.cbTemp33);

        cbWind11 = findViewById(R.id.cbWind11);
        cbWind22 = findViewById(R.id.cbWind22);
        cbWind33 = findViewById(R.id.cbWind33);

        cbOther11 = findViewById(R.id.cbOther11);
        cbOther22 = findViewById(R.id.cbOther22);
        cbOther33 = findViewById(R.id.cbOther33);

    }
    private void send(View v){
        if(cbOver1.isChecked()){
            overall = cbOver11.getText().toString();
        }else if(cbOver2.isChecked()){
            overall = cbOver22.getText().toString();
        }else{
            overall = cbOver33.getText().toString();
        }

        if(cbTemp1.isChecked()){
            temp = cbTemp11.getText().toString();
        }else if(cbTemp2.isChecked()){
            temp = cbTemp22.getText().toString();
        }else{
            temp = cbTemp33.getText().toString();
        }

        if(cbWind1.isChecked()){
            wind = cbWind11.getText().toString();
        }else if(cbWind2.isChecked()){
            wind = cbWind22.getText().toString();
        }else{
            wind = cbWind33.getText().toString();
        }

        if(cbOther1.isChecked()){
            other = cbOther11.getText().toString();
        }else if(cbOther2.isChecked()){
            other = cbOther22.getText().toString();
        }else{
            other = cbOther33.getText().toString();
        }
        Feedback a = new Feedback(User.getMe().username, User.getMe().email,
                Utils.formatLongToDate1(Calendar.getInstance().getTimeInMillis()), overall , temp ,wind ,other);
        Log.d("Fb1", a.username + a.email + a.time + a.overall + a.degree + a.wind + a.other);
        FeedbackHandler db = new FeedbackHandler(v.getContext());
        db.addFB(a);
        cbTemp1.setChecked(false); cbTemp2.setChecked(false); cbTemp3.setChecked(false);
        cbWind1.setChecked(false); cbWind2.setChecked(false); cbWind3.setChecked(false);
        cbOver1.setChecked(false); cbOver2.setChecked(false); cbOver3.setChecked(false);
        cbOther1.setChecked(false); cbOther2.setChecked(false); cbOther3.setChecked(false);
        Intent it = new Intent(FeedbackActivity.this, OtherFeedback.class);
        startActivity(it);
    }
}