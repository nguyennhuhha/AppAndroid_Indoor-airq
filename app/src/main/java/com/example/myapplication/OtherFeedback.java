package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.Adapter.FeedbackAdapter;
import com.example.myapplication.Adapter.FeedbackHandler;
import com.example.myapplication.Model.Feedback;

import java.util.List;

public class OtherFeedback extends AppCompatActivity {
    private FeedbackAdapter adapter;
    private RecyclerView lv;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_feedback);
        lv = findViewById(R.id.rcv_fb);
        back = findViewById(R.id.btn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        FeedbackHandler db = new FeedbackHandler(this);
        List<Feedback> fbs = db.getAllFb();

        adapter= new FeedbackAdapter(OtherFeedback.this, fbs);
        lv.setLayoutManager(new LinearLayoutManager(OtherFeedback.this, RecyclerView.VERTICAL, false));
        lv.setAdapter(adapter);
    }
}