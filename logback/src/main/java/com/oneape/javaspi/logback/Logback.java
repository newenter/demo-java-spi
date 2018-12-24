package com.oneape.javaspi.logback;

import com.oneape.javaspi.Log;

/**
 * described :
 * Created by oneape on 2018-12-24 10:23.
 * Modify:
 */
public class Logback implements Log {
    public void debug(String log) {
        System.out.println("Logback[debug]: --->" + log);
    }

    public void info(String log) {
        System.out.println("Logback[info]: --->" + log);
    }
}
