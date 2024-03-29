package com.anuraj.project.collabowf.utils;

public interface AppConstants {
    public int JAN = 1, FEB = 2, MAR = 3, APR = 4,
            MAY = 5, JUN = 6, JUL = 7, AUG = 8,
            SEP = 9, OCT = 10, NOV = 11, DEC = 12;
    public int DAY_VIEW = 1;
    public int WEEK_VIEW = 7;
    public int MONTH_VIEW = 30;

    public int SWIPE_MIN_DISTANCE = 120;
    public int SWIPE_MAX_OFF_PATH = 250;
    public int SWIPE_THRESHOLD_VELOCITY = 200;

    // For Shared Preferences
    public String LOGIN_PREFERENCES = "com.anuraj.project.collabowf";

    // Title
    String SELECT_SLOT_TITLE = "Select Slot";

    // Font URLS
    String RALEWAY_REGULAR = "fonts/RalewayRegular.ttf";
    String RALEWAY_LIGHT = "fonts/RalewayLight.ttf";
    String RALEWAY_SEMI_BOLD = "fonts/RalewaySemiBold.ttf";

    // Date Formats
    String DD_MMM_YYYY = "dd MMM yyyy";

    // Date Index
    int START = 0;
    int END = 1;

    // Calendar Text Sizes
    int DAY_TEXT_SIZE = 12;
    int DAY_EVENT_SIZE = 14;
    int WEEK_EVENT_SIZE = 10;

    // Messages
    String EVENT_ADD_FAILURE_MESSAGE = "Failed to add event";


    String MORNING_SHIFT = "Morning Shift";
    String AFTERNOON_SHIFT = "Afternoon Shift";
    String NIGHT_SHIFT = "Night Shift";
    String ON_LEAVE = "On Leave";
}
