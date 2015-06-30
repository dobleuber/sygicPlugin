package com.seon.navigator.util;

/**
 * Created by wcornejo on 26/06/15.
 */
public class Util {

    public static String EMPTY_STRING = "";

    /**
     * Converts the notation xx.xxxxxx to notation in int
     * @param position in double format
     * @return position in int format
     */
    public static int convertDoublePositioningToLong(double position){
        return  (int) (position * 100000);
    }
}
