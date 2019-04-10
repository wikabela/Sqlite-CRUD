package com.example.wika.shared_preference.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.wika.shared_preference.dao.NoteDao;
import com.example.wika.shared_preference.db.AppDatabase;
import com.example.wika.shared_preference.models.Note;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> notes;

    public NoteRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        noteDao = db.noteDao();
        notes = noteDao.getAll();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }
    public void insert(Note note) {
        new InsertAsyncTask(noteDao)
                .execute(note);
    }
    public void update(Note note) {
        new UpdateAsyncTask(noteDao)
                .execute(note);
    }

    private static class InsertAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao asyncTaskDao;
        InsertAsyncTask(NoteDao dao) {
            asyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            asyncTaskDao.insert(notes);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao asyncTaskDao;
        UpdateAsyncTask(NoteDao dao) {
            asyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            asyncTaskDao.update(notes);
            return null;
        }
    }
}
