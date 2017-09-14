package com.laz.tftv;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laz.tftv.ui.Post;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by maxmelo on 9/3/17.
 */

public class PostsActivity extends AppCompatActivity {
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        link = getIntent().getStringExtra("link");
        new PostData().execute();
    }

    private class PostData extends AsyncTask<Void, Void, Void> {
        List<Element> posts;
        String title;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(getString(R.string.links_main) + link).get();
                System.out.println(link);

                posts = document.body().select(getString(R.string.selectors_post));
                title = document.body().select(getString(R.string.selectors_threadheadertitle)).first().html();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try { getActionBar().setTitle(title); } catch(Exception e) {}
            try { getSupportActionBar().setTitle(title); } catch(Exception e) {}

            populateList(posts);
        }
    }

    private void populateList(List<Element> posts) {
        final String SEL_POST_CONTENT = getString(R.string.selectors_post_content);

        LinearLayout layout = (LinearLayout) findViewById(R.id.post_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        params.setMargins(20,15,20,15);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (Element elem : posts) {
            Post post = (Post) inflater.inflate(R.layout.view_post,null);

            try {
                Element contentElem = elem.select(SEL_POST_CONTENT).first();

                post.setPostData("poster","1",contentElem.html());

            } catch(Exception e) { e.printStackTrace(); }

            layout.addView(post);
        }
    }
}
