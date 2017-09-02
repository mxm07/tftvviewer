package com.laz.tftv.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.laz.tftv.R;

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

    public void setThreadTitle(String title) {
        TextView titleView = findViewById(R.id.threadrow_title);
        try {
            titleView.setText(title);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
