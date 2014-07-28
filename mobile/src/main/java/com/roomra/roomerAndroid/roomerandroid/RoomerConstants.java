package com.roomra.roomerAndroid.roomerandroid;

/**
 * Created by jeremyclifton on 7/9/14.
 */
public class RoomerConstants {
    public static final String ROOMRA_URL="https://roomra.com/";
    public static final String PREFS_FILE = "roomra";
    public static final String LOCK_TAG = "roomra";
    public static final Long START_TIME = 1000L * 4L;
    public static final String UPDATE_ACCESS = "<ACCESSTOKEN>";
    public static final String UPDATE_REFRESH = "<REFRESHTOKEN>";
    public static final String UPDATE_USERNAME = "<USERNAME>";
    public static final String[] RADII = { "15\nm", "16\nm", "17\nm", "18\nm", "19\nm", "20\nm", "22\nm", "23\nm", "25\nm", "27\nm", "30\nm", "32\nm", "35\nm", "39\nm", "42\nm", "46\nm", "51\nm", "56\nm", "62\nm", "69\nm", "77\nm", "85\nm", "95\nm", ".11\nkm", ".12\nkm", ".13\nkm", ".15\nkm", ".17\nkm", ".19\nkm", ".21\nkm", ".24\nkm", ".27\nkm", ".3\nkm", ".34\nkm", ".39\nkm", ".44\nkm", ".5\nkm", ".57\nkm", ".65\nkm", ".73\nkm", ".84\nkm", ".96\nkm", "1\nkm", "1.3\nkm", "1.5\nkm", "1.7\nkm", "1.9\nkm", "2.2\nkm", "2.5\nkm", "2.9\nkm", "3.3\nkm", "3.8\nkm", "4.5\nkm", "5\nkm", "6\nkm", "7\nkm", "8\nkm", "9.3\nkm", "10.8\nkm", "12.5\nkm", "14.6\nkm", "17\nkm", "20\nkm", "23\nkm", "27\nkm", "31.5\nkm", "37\nkm", "43.3\nkm", "50.7\nkm", "59.5\nkm", "69.8\nkm", "82\nkm", "96.5\nkm", "115\nkm", "135\nkm", "155\nkm", "185\nkm", "220\nkm", "260\nkm", "305\nkm", "360\nkm", "430\nkm", "505\nkm", "600\nkm", "710\nkm", "845\nkm", "10000\nkm", "12000\nkm", "14200\nkm", "17000\nkm", "20125\nkm", "24000\nkm", "28500\nkm", "34000\nkm", "40700\nkm", "48700\nkm", "58250\nkm", "69700\nkm", "83465\nkm", "90000\nkm" };

    public static String getDistanceString(int n) {
        //Domain is [0..100]
        //Range is [15m .. 10,000km]
        //Function is 15*e^((13.41/100)*x)
        if(n <= 0) { return "15\nm";}
        if(n >= 100) { return "10Mm";}
        return RADII[n];
    }


}
