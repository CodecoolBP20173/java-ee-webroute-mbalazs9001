package com.mbalazs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.lang.annotation.Annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        Class obj = TestRoutes.class;

        Annotation annotation = obj.getAnnotation(WebRoute.class);
        WebRoute webRoute = (WebRoute) annotation;

        Method[] methods = obj.getMethods();

        for (Method method : methods) {
            if(method.isAnnotationPresent(WebRoute.class)) {
                String param = method.getAnnotation(WebRoute.class).value();
                server.createContext(param, new MyHandler());
            }
        }

        server.setExecutor(null); // creates a default executor
        server.start();

    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {

            Class obj = TestRoutes.class;

            Method[] methods = obj.getMethods();

            for(Method method: methods) {
                if (method.isAnnotationPresent(WebRoute.class)) {
                    WebRoute annotation = method.getAnnotation(WebRoute.class);
                    if(annotation.value().equals(t.getRequestURI().toString())) {
                        try {
                            method.invoke(null, t);
                        }
                        catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
