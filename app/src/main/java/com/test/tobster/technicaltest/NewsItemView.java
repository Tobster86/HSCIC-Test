package com.test.tobster.technicaltest;

import android.content.Context;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tobster on 21/08/15.
 */
public class NewsItemView extends LinearLayout
{
    private static final String REFERENCE_TEXT = "Reference Number: ";
    private static final int PADDING = 32;

    public NewsItemView(Context context, NewsItem newsItem)
    {
        super(context);

        //Set layout for this text view to show reference, date and truncated content on three lines, with padding above.
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setPadding(0, PADDING, 0, 0);
        setOrientation(HORIZONTAL);

        LinearLayout layoutText = new LinearLayout(context);
        layoutText.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        layoutText.setOrientation(VERTICAL);

        TextView txtReference = new TextView(context);
        txtReference.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        txtReference.setText(REFERENCE_TEXT + newsItem.GetReferenceNo());

        /*TextView txtDate = new TextView(context);
        txtDate.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        txtDate.setText(newsItem.GetDateString());*/

        TextView txtContent = new TextView(context);
        txtContent.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        txtContent.setText(newsItem.GetContentString());
        txtContent.setSingleLine();
        txtContent.setEllipsize(TextUtils.TruncateAt.END);

        layoutText.addView(txtReference);
        //layoutText.addView(txtDate);
        layoutText.addView(txtContent);

        addView(layoutText);
    }
}
