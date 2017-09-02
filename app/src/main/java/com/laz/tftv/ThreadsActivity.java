package com.laz.tftv;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.laz.tftv.ui.ThreadRow;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;

public class ThreadsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try { getActionBar().setTitle(""); } catch(Exception e) {}
        try { getSupportActionBar().setTitle(""); } catch(Exception e) {}

        new ThreadData().execute();
    }

    private class ThreadData extends AsyncTask<Void, Void, Void> {
        String title = "";
        List<Element> threads;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(getString(R.string.tftv_threads_link)).get();
                threads = document.body().select(".title");
                title = document.title();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try { getActionBar().setTitle(title); } catch(Exception e) {}
            try { getSupportActionBar().setTitle(title); } catch(Exception e) {}

            populateList(threads);
        }
    }

    protected void populateList(List<Element> list) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.thread_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        params.setMargins(20,15,20,15);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        for (Element elem : list) {
            ThreadRow row = (ThreadRow) inflater.inflate(R.layout.view_threadrow, null);
            row.setThreadTitle(elem.attributes().get("title"));
            layout.addView(row, params);
        }
    }
}
