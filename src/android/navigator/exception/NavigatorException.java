package com.seon.navigator.exception;

/**
 * Created by wcornejo on 24/06/15.
 */
public class NavigatorException extends RuntimeException {

    public NavigatorException(Exception e){
        super("Error while calling the navigator module.", e);
    }
}
