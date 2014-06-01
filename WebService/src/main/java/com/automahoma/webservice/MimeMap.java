/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice;

/**
 *
 * @author SESA244648
 */
public final class MimeMap {
    private MimeMap() { }
    
    public static final String CSS = "text/css";
    public static final String HTML = "text/html";
    public static final String JS = "application/javascript";
    public static final String DEFAULT = "text/plain";
    
    public static String getMimeType(String name) {
        if (name.endsWith(".html")) {
            return HTML;
        } else if (name.endsWith(".js")) {
            return JS;
        } else if (name.endsWith(".css")) {
            return CSS;
        } else {
            return DEFAULT;
        }
    }
}
