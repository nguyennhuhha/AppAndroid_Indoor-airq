package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SignupTabFragment extends Fragment {
    private Button back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_tab, container, false);
        Button returnButton = view.findViewById(R.id.signup_back);
        Button signup = view.findViewById(R.id.signup_button);
        EditText username_et = view.findViewById(R.id.signup_username);
        EditText email_et = view.findViewById(R.id.signup_email);
        EditText pass_et = view.findViewById(R.id.signup_password);
        EditText confirm_et = view.findViewById(R.id.signup_confirm);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện các thao tác cần thiết để quay trở lại activity chính
                getActivity().onBackPressed();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), LoadingActivity.class);
                int data=2;
                it.putExtra("fragment", data);
                it.putExtra("signup_usr", username_et.getText().toString());
                it.putExtra("signup_email", email_et.getText().toString());
                it.putExtra("signup_pass", pass_et.getText().toString());
                it.putExtra("signup_cfmpass", confirm_et.getText().toString());
                startActivity(it);

            }
        });
        return view;
    }
}