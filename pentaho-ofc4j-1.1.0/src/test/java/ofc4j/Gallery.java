///*
//This file is part of OFC4J.
//
//OFC4J is free software: you can redistribute it and/or modify
//it under the terms of the Lesser GNU General Public License as
//published by the Free Software Foundation, either version 3 of
//the License, or (at your option) any later version.
//
//OFC4J is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//See <http://www.gnu.org/licenses/lgpl-3.0.txt>.
//*/
//
//package ofc4j;
//
//import java.io.File;
//
//import ofc4j.gallery.GalleryServlet;
//
//import org.eclipse.jetty.server.Server;
//import org.mortbay.jetty.Server;
//import org.mortbay.jetty.handler.ResourceHandler;
//import org.mortbay.jetty.servlet.Context;
//
//public class Gallery {
//    public static void main(String... args) throws Exception {
//        int port = 8080;
//        if (args.length > 0) {
//            port = Integer.parseInt(args[0]);
//        }
//
//        File index = new File(".", "index.html");
//        File gallery = new File(".", "gallery");
//        boolean indexExists = index.exists() && index.isFile();
//        boolean galleryExists = gallery.exists() && gallery.isDirectory();
//        if (!(indexExists && galleryExists)) {
//            System.err.println("ERROR: missing required files.  Make sure 'web' is the working directory.");
//            System.exit(1);
//        }
//
//        Server s = new Server(port);
//        Context root = new Context(s, "/chart", Context.SESSIONS);
//        root.addServlet(GalleryServlet.class, "/*");
//
//        ResourceHandler handler = new ResourceHandler();
//        handler.setResourceBase(".");
//        s.addHandler(handler);
//
//        s.start();
//    }
//}
