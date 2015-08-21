package com.test.tobster.technicaltest;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class TechnicalTest extends ActionBarActivity
{
    private static final String SEARCH_SEPARATOR = " ";

    private List<NewsItem> NewsItems = new ArrayList<>();

    private LinearLayout lytNewsItems;
    private EditText txtSearchTerms;
    private RadioButton radOr;
    private RadioButton radAnd;
    private Button btnSearch;
    private Button btnClear;

    private boolean bError = false;

    private String[] UNIT_TEST_STRINGS =
    {
        "Care Quality Commission",
        "September 2004",
        "general population generally",
        "Care Quality Commission admission",
        "general population Alzheimer"
    };

    private boolean[] UNIT_TESTS_AND =
    {
        false,
        false,
        false,
        true,
        true
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_test);

        //Get references to relevant user interface elements.
        lytNewsItems = (LinearLayout)findViewById(R.id.lytNewsItems);
        txtSearchTerms = (EditText)findViewById(R.id.txtSearchTerms);
        radOr = (RadioButton)findViewById(R.id.radOr);
        radAnd = (RadioButton)findViewById(R.id.radAnd);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        btnClear = (Button)findViewById(R.id.btnClear);

        //Load news data.
        if(InitialiseNewsData())
        {
            //News data is valid. Set up user interface accordingly.
            PerformSearch();
        }
        else
        {
            //News data is not valid. Disable user interface accordingly and display error message.
            bError = true;
            btnSearch.setEnabled(false);
            btnClear.setEnabled(false);

            TextView txtNoResults = new TextView(this);
            txtNoResults.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtNoResults.setText(R.string.error);
            lytNewsItems.addView(txtNoResults);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_technical_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(!bError)
        {
            //Handle unit tests.
            int lTestIndex = -1;

            switch (item.getItemId())
            {
                case R.id.unittest1:
                    lTestIndex = 0;
                    break;
                case R.id.unittest2:
                    lTestIndex = 1;
                    break;
                case R.id.unittest3:
                    lTestIndex = 2;
                    break;
                case R.id.unittest4:
                    lTestIndex = 3;
                    break;
                case R.id.unittest5:
                    lTestIndex = 4;
                    break;
            }

            if (lTestIndex >= 0)
            {
                txtSearchTerms.setText(UNIT_TEST_STRINGS[lTestIndex]);
                radAnd.setChecked(UNIT_TESTS_AND[lTestIndex]);
                radOr.setChecked(!UNIT_TESTS_AND[lTestIndex]);
                PerformSearch();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean InitialiseNewsData()
    {
        //Create array list to store the raw news items (carriage-return delimited strings).
        List<String> strNewsItems = new ArrayList<>();

        //Create the streams to read the raw news file.
        final InputStream inputStream = getResources().openRawResource(R.raw.hscic_news);
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        //Read the individual news items as lines (carriage-return delimited).
        String nextLine;

        try
        {
            while((nextLine = bufferedReader.readLine()) != null)
            {
                strNewsItems.add(nextLine);
            }
        }
        catch (IOException e)
        {
            //Return false if there are any errors reading the file.
            return false;
        }

        //Create news items from the news item strings.
        int lReferenceIDs = 0;

        for(String strNewsItem : strNewsItems)
        {
            //Create news item. Postincrement the ID for the subsequent news item.
            NewsItem newsItem = new NewsItem(lReferenceIDs++, strNewsItem);

            if(newsItem.IsValid())
            {
                //Store news item if it is valid.
                NewsItems.add(newsItem);
            }
        }

        //File has been read successfully, return true.
        return true;
    }

    private void PerformSearch()
    {
        //Performs the search operation, clearing the news scroll view and repopulating with results.
        lytNewsItems.removeAllViews();

        //Dismiss the keyboard if it is present.
        View view = this.getCurrentFocus();
        if (view != null)
        {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        String[] strSearchTerms = txtSearchTerms.getText().toString().split(SEARCH_SEPARATOR);
        int lResults = 0;

        for(NewsItem newsItem : NewsItems)
        {
            boolean bRelevant = false;

            if(strSearchTerms.length == 0)
            {
                //No search terms entered. Display all results.
                bRelevant = true;
            }
            else if(radOr.isChecked())
            {
                //Search with 'Or' semantic.
                for(String strSearchTerm : strSearchTerms)
                {
                    if(newsItem.Contains(strSearchTerm))
                    {
                        //Contains this term. Flag true and break.
                        bRelevant = true;
                        break;
                    }
                }
            }
            else if(radAnd.isChecked())
            {
                //Set initially true before we 'And' test the search terms.
                bRelevant = true;

                //Search with 'And' semantic.
                for(String strSearchTerm : strSearchTerms)
                {
                    if(!newsItem.Contains(strSearchTerm))
                    {
                        //Does not contain this term. Reflag to false and break.
                        bRelevant = false;
                        break;
                    }
                }
            }

            if(bRelevant)
            {
                lytNewsItems.addView(new NewsItemView(this, newsItem));
                lResults++;
            }
        }

        if(lResults == 0)
        {
            //Display 'No results'.
            TextView txtNoResults = new TextView(this);
            txtNoResults.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtNoResults.setText(R.string.no_results);
            lytNewsItems.addView(txtNoResults);
        }
    }

    public void SearchPressed(View view)
    {
        PerformSearch();
    }

    public void ClearPressed(View view)
    {
        txtSearchTerms.setText("");
        PerformSearch();
    }
}
