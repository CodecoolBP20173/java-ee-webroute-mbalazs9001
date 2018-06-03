package com.mbalazs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class TestRoutes {

    @WebRoute("/my-first-route")
    public static void myFirstRoute(HttpExchange t) throws IOException {
        String response = "This is the first route's response";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @WebRoute("/my-second-route")
    public static void mySecondRoute(HttpExchange t) throws IOException {
        String response = "This is the second route's response";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
