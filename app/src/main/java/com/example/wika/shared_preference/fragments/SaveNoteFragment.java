package com.example.wika.shared_preference.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.wika.shared_preference.Constant;
import com.example.wika.shared_preference.R;
import com.example.wika.shared_preference.models.Note;
/**
 * A simple {@link Fragment} subclass.
 */
public class SaveNoteFragment extends Fragment {
    private OnSaveNoteFragmentListener listener;

    public SaveNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_save_note, container, false);
        final EditText titleText = view.findViewById(R.id.input_title);
        final EditText contentText = view.findViewById(R.id.input_content);
        Button saveButton = view.findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int tag = Constant.INSERT_NOTE;
                    String title = titleText.getText().toString();
                    String content = contentText.getText().toString();

                    Note newNote = new Note();
                    newNote.setTitle(title);
                    newNote.setContent(content);
                    listener.onSaveButtonClicked(view, newNote, tag);
                }
            }
        });
        return view;
    }

    public interface OnSaveNoteFragmentListener {
        void onSaveButtonClicked(View view, Note note, int tag);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSaveNoteFragmentListener) {
            listener = (OnSaveNoteFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnNewNoteFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}
