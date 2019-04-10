package com.example.wika.shared_preference.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wika.shared_preference.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private OnLoginFragmentListener listener;

    public LoginFragment() {
        // Required empty public constructor
    }

    public void setListener(OnLoginFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        final EditText usernameText = view.findViewById(R.id.text_username);
        final EditText passwordText = view.findViewById(R.id.text_password);
        Button loginButton = view.findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    String username = usernameText.getText().toString();
                    String password = passwordText.getText().toString();
                    listener.onLoginButtonClicked(view, username, password);
                }
            }
        });

        TextView registerLink = view.findViewById(R.id.link_register);
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onRegisterLinkClicked();
                }
            }
        });

        Button Link = view.findViewById(R.id.link);
        Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri instagram = Uri.parse("https://www.instagram.com/?hl=en");
                Intent intent = new Intent(Intent.ACTION_VIEW, instagram);
                startActivity(intent);
            }
        });
        return view;
    }

    public interface OnLoginFragmentListener {
        void onLoginButtonClicked(View view, String username, String password);
        void onRegisterLinkClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentListener) {
            listener = (OnLoginFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}
