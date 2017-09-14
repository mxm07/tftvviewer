package com.laz.tftv;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.laz.tftv.ui.ThreadRow;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        List<Element> threads;
        String title;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(getString(R.string.links_threads_main)).get();

                threads = document.body().select(getString(R.string.selectors_threadrow));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.threads_menu, menu);
        return true;
    }

    protected void populateList(List<Element> list) {
        final String SEL_THREAD_TITLE = getString(R.string.selectors_threadrow_title);
        final String SEL_THREAD_DATEETA = getString(R.string.selectors_threadrow_date_eta);
        final String SEL_THREAD_DATEFULL = getString(R.string.selectors_threadrow_date_full);
        final String SEL_THREAD_SUBFORUM = getString(R.string.selectors_threadrow_subforum);
        final String SEL_THREAD_FRAGCOUNT = getString(R.string.selectors_threadrow_fragcount);
        final String SEL_THREAD_POSTER = getString(R.string.selectors_threadrow_poster);

        LinearLayout layout = (LinearLayout) findViewById(R.id.thread_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        params.setMargins(20,15,20,15);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (Element elem : list) {
            ThreadRow row = (ThreadRow) inflater.inflate(R.layout.view_threadrow, null);

            try {
                Element titleElem = elem.select(SEL_THREAD_TITLE).first();
                Element dateETAElem = elem.select(SEL_THREAD_DATEETA).first();
                Element fragsElem = elem.select(SEL_THREAD_FRAGCOUNT).first();
                Element subforumElem = elem.select(SEL_THREAD_SUBFORUM).first();

                //Match "posted by <name> in" to extract the poster name
                String poster = "poster";
                Pattern pattern = Pattern.compile("posted by (.*) in");
                Matcher matcher = pattern.matcher(elem.select(SEL_THREAD_POSTER).first().ownText());
                while (matcher.find()) {poster = matcher.group(1);}


                row.setThreadData(
                        titleElem.html(),
                        fragsElem.html(),
                        dateETAElem.html(),
                        poster,
                        subforumElem.html());

                final Element pass = elem;
                row.setListener(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(ThreadsActivity.this, PostsActivity.class);
                        intent.putExtra("LINK", pass.select(".main > a").first().attr("href"));
                        startActivity(intent);
                    }
                });

            } catch(Exception e) {
                e.printStackTrace();
            }
            layout.addView(row, params);
        }
    }
}
