/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automahoma.webservice;

import java.io.IOException;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.osgi.service.http.HttpContext;

/**
 *
 * @author SESA244648
 */
public class Context implements HttpContext {

    public boolean handleSecurity(HttpServletRequest hsr, HttpServletResponse hsr1) throws IOException {
        return true;
    }

    public URL getResource(String string) {
        throw new UnsupportedOperationException();
    }

    public String getMimeType(String name) {
        return MimeMap.getMimeType(name);
    }
    
}
