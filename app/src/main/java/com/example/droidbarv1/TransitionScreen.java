package com.example.droidbarv1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import androidx.fragment.app.Fragment;


public class TransitionScreen extends AsyncTask<Void, Void, Void> {

    private View fragment;
    private Intent i;
    private Context context;

    public TransitionScreen(View fragment, Intent intent, Context context){
        this.fragment = fragment;
        this.i = intent;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        fragment.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        context.startActivity(i);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        fragment.setVisibility(View.INVISIBLE);
    }
}
