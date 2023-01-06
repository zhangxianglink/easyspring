package org.easyspring.util;

import java.util.ArrayList;
import java.util.List;

public class MessageTracker {

    public static List<String> getTrackerMessage() {
        return TRACKER_MESSAGE;
    }

    private static List<String> TRACKER_MESSAGE = new ArrayList<String>();

    public static void addMsg(String msg) {
        TRACKER_MESSAGE.add(msg);
    }

    public static void clearMsgs() {
        TRACKER_MESSAGE.clear();
    }

}
