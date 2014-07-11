package com.roomra.roomerAndroid.roomerandroid;

import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;

/**
 * Created by jeremyclifton on 7/11/14.
 */
public class RoomerDeveloper {

    public RoomerDeveloper() {

    }

    public void testTaskBuilder() {
        Task t[] = new Task[400];
        int i = 0;
        ArrayList<BasicNameValuePair> pvs = new ArrayList<BasicNameValuePair>();
        pvs.add(new BasicNameValuePair("userId", "clifton"));
        pvs.add(new BasicNameValuePair("password", "anyanton"));
        for(i=0;i<400;i++) {
            t[i] = new Task(TaskType.DB, "oauth2/authenticate?reqId="+(new Integer(i)).toString(), pvs);
        }
        AsyncConnection ac = new AsyncConnection(true, t);
        ac.connect();
        //look at get/posts
    }
}
