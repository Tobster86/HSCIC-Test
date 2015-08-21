package com.test.tobster.technicaltest;

/**
 * Created by tobster on 21/08/15.
 */
public class NewsItem
{
    private final static String DATE_SEPERATOR = ":";

    private int lReferenceNo;
    //private String strDate = "";
    private String strContent = "";
    private boolean bValid = false;

    public NewsItem(int lReferenceNo, String strRawText)
    {
        this.lReferenceNo = lReferenceNo;

        /*//Ensure date separators exist before we continue to prevent array out of bounds issue.
        if(strRawText.contains(DATE_SEPERATOR))
        {
            //Date is the first substring when split at the date separator. Ignore remaining strings as they are splits from potentially valid : characters.
            strDate = strRawText.split(DATE_SEPERATOR)[0];

            //Content is the remainder of the string after the length of the date string, plus the separator.
            strContent = strRawText.substring(strDate.length() + 1, strRawText.length());

            //Trim trailing and leading whitespace from both strings.
            strDate = strDate.trim();
            strContent = strContent.trim();

            //Validate this news item. In this case, the date and content must have length.
            bValid = (strDate.length() > 0) && (strContent.length() > 0);
        }*/

        strContent = strRawText;
        bValid = strRawText.length() > 0;
    }

    public boolean IsValid()
    {
        return bValid;
    }

    public int GetReferenceNo()
    {
        return lReferenceNo;
    }

    /*public String GetDateString()
    {
        return strDate;
    }*/

    public String GetContentString()
    {
        return strContent;
    }

    public boolean Contains(String strQuery)
    {
        return strContent.contains(strQuery);
    }
}
