package com.example.wika.shared_preference.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wika.shared_preference.R;
import com.example.wika.shared_preference.models.User;
import com.example.wika.shared_preference.UserViewModel;
/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    private OnRegisterFragmentListener listener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        final EditText usernameText = view.findViewById(R.id.input_username);
        final EditText passwordText = view.findViewById(R.id.input_password);
        Button registerButton = view.findViewById(R.id.button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    String username = usernameText.getText().toString();
                    String password = passwordText.getText().toString();
                    listener.onRegisterButtonClicked(view, username, password);
                }
            }
        });

        TextView loginLink = view.findViewById(R.id.link_login);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onLoginLinkClicked();
                }
            }
        });
        return view;
    }
    public interface OnRegisterFragmentListener {
        void onRegisterButtonClicked(View view, String username, String password);
        void onLoginLinkClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisterFragmentListener) {
            listener = (OnRegisterFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRegisterFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
