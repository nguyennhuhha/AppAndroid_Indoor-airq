package com.example.myapplication;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginTabFragment extends Fragment {
     EditText name, pass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_tab, container, false);
        Button returnButton = view.findViewById(R.id.login_back);
        Button login = view.findViewById(R.id.login_button);
        name= view.findViewById(R.id.login_username);
        pass = view.findViewById(R.id.login_password);
        TextView reset = view.findViewById(R.id.reset_password);

        reset.setPaintFlags(reset.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //quay về trang chính
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện các thao tác cần thiết để quay trở lại activity chính
                getActivity().onBackPressed();
            }
        });
        //login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), LoadingActivity.class);
                String username=name.getText().toString();
                String password = pass.getText().toString();
                int data=1;
                //truyền tham số
                it.putExtra("fragment", data);
                it.putExtra("name", username);
                it.putExtra("pass", password);
                startActivity(it);

            }
        });
        //reset passsword
        String text = reset.getText().toString();
        SpannableString ss = new SpannableString(text);
        ClickableSpan c1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //mở trang để reset password

            }
        };
        ss.setSpan(c1, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        reset.setText(ss);
        reset.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }

}