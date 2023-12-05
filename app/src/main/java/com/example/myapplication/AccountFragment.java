package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Model.User;

public class AccountFragment extends Fragment {
    private HomeActivity parentActivity;
    private View mView;
    private TextView name, email, first, last;
    public AccountFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_acc, container, false);
        parentActivity = (HomeActivity) getActivity();
        showBasicInfo();
        return mView;
    }

    private void showBasicInfo() {
        name = mView.findViewById(R.id.user_name);
        email = mView.findViewById(R.id.user_mail);
        first= mView.findViewById(R.id.user_first);
        last = mView.findViewById(R.id.user_last);

        name.setText(User.getMe().username);
        email.setText(User.getMe().email);
        first.setText(User.getMe().firstName);
        last.setText(User.getMe().lastName);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
