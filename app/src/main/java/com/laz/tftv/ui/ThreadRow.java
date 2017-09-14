package com.laz.tftv.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.laz.tftv.R;

import java.util.concurrent.Callable;

public class ThreadRow extends RelativeLayout {
    public ThreadRow(Context context) {
        super(context);
        init(context);
    }

    public ThreadRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ThreadRow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {}

    public void setThreadData(String title, String frags, String eta, String poster, String forum) {
        TextView titleView = findViewById(R.id.threadrow_title);
        TextView descView = findViewById(R.id.threadrow_desc);
        TextView fragsView = findViewById(R.id.threadrow_frags);

        try {
            titleView.setText(title);

            StringBuilder sb = new StringBuilder("posted ");
            sb.append(eta); sb.append(" by "); sb.append(poster);
            sb.append(" in "); sb.append(forum);

            descView.setText(sb);
            fragsView.setText(frags);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void setListener(final Runnable func) {
        Button btn = findViewById(R.id.threadrow_btn);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    System.out.println("helo");
                    func.run();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
