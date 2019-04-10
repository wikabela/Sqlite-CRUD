package com.example.wika.shared_preference;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import com.example.wika.shared_preference.adapters.NoteAdapter;
import com.example.wika.shared_preference.fragments.LoginFragment;
import com.example.wika.shared_preference.fragments.NoteFragment;
import com.example.wika.shared_preference.fragments.RegisterFragment;
import com.example.wika.shared_preference.fragments.SaveNoteFragment;
import com.example.wika.shared_preference.fragments.SettingFragment;
import com.example.wika.shared_preference.models.Note;
import com.example.wika.shared_preference.models.User;

public class MainActivity extends AppCompatActivity implements
        LoginFragment.OnLoginFragmentListener,
        NoteFragment.OnNoteFragmentListener,
        RegisterFragment.OnRegisterFragmentListener,
        SaveNoteFragment.OnSaveNoteFragmentListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private UserViewModel userViewModel;
    private NoteViewModel noteViewModel;
    private Settings settings;
    private Session session;

    public Settings getSettings() {
        return settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new SettingFragment())
                .commit();

        settings = new Settings(this);
        session = new Session(settings);

        addFragment();

        noteViewModel = ViewModelProviders.of(this)
                .get(NoteViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            createSettingFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addFragment() {
        Fragment fragment = (session.isLogin()) ? new NoteFragment() : new LoginFragment();
        changeFragment(fragment, false);
    }

    private void changeFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    private void createSettingFragment() {
        Fragment settingFragment = new SettingFragment();
        changeFragment(settingFragment, true);
    }

    @Override
    public void onLoginButtonClicked(final View view, final String username, final String password) {
        User user = session.doLogin(username, password);
        String message = "Authentication failed";
        if (user != null) {
            message = "Welcome " + username;
            session.setUser(username);
            addFragment();
        }
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterLinkClicked() {
        Fragment registerFragment = new RegisterFragment();
        changeFragment(registerFragment, false);
    }


    @Override
    public void onNotesLoad(final NoteAdapter adapter) {
        noteViewModel.getNotes()
                .observe(this, new Observer<List<Note>>() {
                    @Override
                    public void onChanged(@Nullable List<Note> notes) {
                        adapter.setNotes(notes);
                    }
                });
    }

    @Override
    public void onAddButtonClicked() {
        Fragment fragment = new SaveNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.NOTE_TAG, Constant.INSERT_NOTE);
        fragment.setArguments(bundle);
        changeFragment(fragment, true);
    }

    @Override
    public void onLogoutMenuClicked() {
        session.doLogout();
        addFragment();
    }

    @Override
    public void onRegisterButtonClicked(View view, String username, String password) {
        User user = new User(username, password);
        userViewModel.insert(user);
        Snackbar.make(view, "Registration has been successfull", Snackbar.LENGTH_SHORT)
                .show();
        LoginFragment fragment = new LoginFragment();
        changeFragment(fragment, false);
    }

     @Override
     public void onLoginLinkClicked() {
         Fragment loginFragment = new LoginFragment();
         changeFragment(loginFragment, false);
     }

            @Override
            public void onSaveButtonClicked(View view, Note note, int tag) {
                noteViewModel.insert(note);
                Fragment fragment = new NoteFragment();
                changeFragment(fragment, false);
                Snackbar.make(view, "Saving note....", Snackbar.LENGTH_SHORT)
                        .show();
                // return to previous fragment
                getSupportFragmentManager()
                        .popBackStack();
            }
        }








