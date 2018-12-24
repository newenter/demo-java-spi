package com.oneape.javaspi.log4j;

import com.oneape.javaspi.Log;

/**
 * described :
 * Created by oneape on 2018-12-24 10:19.
 * Modify:
 */
public class Log4j implements Log {

    public void debug(String log) {
        System.out.println("Log4j[debug]: --->" + log);
    }

    public void info(String log) {
        System.out.println("Log4j[info]: --->" + log);
    }
}
