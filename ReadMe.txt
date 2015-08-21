README

ReadMe file for HSCIC screening test 'news search' solution.



AUTHOR

William Tobias Joseph Wilson



INSTALLATION

The .apk file may be installed on any device running Android 4.0.3 Ice Cream Sandwich (API Level 15).

It may be necessary to enable installation of unsigned applications (Settings -> Security -> Unknown sources).
The launcher icon is titled 'HCSIS Technical Test' and bears a stock Android logo.



OPERATION

The application is bundled with the news data file and reads it at startup.

Initially, all entries are displayed in the scroll view underneath the search tools. Pressing the 'Clear' button clears the search terms and returns the application to this state.

Search terms may be entered as space delimited text into the top text edit field, and search semantics (And/Or) are specified by the radio buttons underneath.

Pressing 'Search' clears the scroll view, and repopulates it with entries that match the search criteria. 'No results' is displayed if no items match the criteria.



INVENTORY

TechnicalTest.zip contains the Android Studio solution folder which was created with Android Studio 1.2.1.1 under Linux Mint 17.1 'Rebecca'.

The java source files are in /app/src/main/java/com/test/tobster/technicaltest and are as follows:
TechnicalTest.java - The main activity file for the app, which contains the core application logic and unit test functionality.
NewsItem.java - Represents an instance of a news item with methods to retreive data and check search terms.
NewsItemView.java - A view instance for displaying the reference number and content of a news item.

The layout xml file is located in /app/src/main/res/layout .
