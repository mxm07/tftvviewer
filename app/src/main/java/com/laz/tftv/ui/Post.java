package com.laz.tftv.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.laz.tftv.R;

public class Post extends RelativeLayout {
    private String link;

    public Post(Context context) {
        super(context);
        init(context);
    }

    public Post(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Post(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
    }

    public void setPostData(String poster, String postnum, String content) {
        TextView postNumber = findViewById(R.id.post_number);
        TextView postContent = findViewById(R.id.post_content);
        TextView postAuthor = findViewById(R.id.post_author);

        try {
            postNumber.setText(postnum);
            postAuthor.setText(poster);
            postContent.setText(content);
        } catch(Exception e) { e.printStackTrace(); }
    }
}
