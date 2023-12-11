package main;

import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) throws IOException {
        startServer();
    }

    public static void startServer() throws IOException {
        //init server socket at port ENV PORT
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(System.getenv("PORT")));
        System.out.println("Listening at " + System.getenv("PORT"));

        while (true) {
            handleRequest(serverSocket.accept());
        }
    }

    public static void handleRequest(Socket socket) throws IOException {
        try (InputStream input = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input));
             OutputStream output = socket.getOutputStream()) {
            // parse and print parameters and values from the query string
            try{
                String line = reader.readLine();
                System.out.println(line);
                if (line == null || line.isEmpty()) {
                    return;
                }
                String[] query = line.split(" ");
                String[] params = query[1].split("\\?")[1].split("&");
            for (String param : params) {
                String[] pair = param.split("=");
                System.out.println(pair[0] + ": " + pair[1]);
            }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("No parameters");
            }
            
            sendResponse(output);
        } finally {
            socket.close();
        }
    }

    public static void sendResponse(OutputStream output) throws IOException {
        output.write("HTTP/1.1 200 OK\r\n".getBytes());
        output.write("Content-Type: text/html\r\n".getBytes());
        output.write("\r\n".getBytes());
        output.write("<!DOCTYPE html>".getBytes());
             output.write("<html lang=\"en\">\n".getBytes());
            output.write("<head>\n".getBytes());
            output.write("<style>\n".getBytes());
            output.write(".app{\n".getBytes());
            output.write("display: flex;\n".getBytes());
            output.write("justify-content: center;\n".getBytes());
            output.write("align-items: center;\n".getBytes());
            output.write("flex-direction: column;\n".getBytes());
            output.write("background-color: #2e2ee6;\n".getBytes());
            output.write("color: white;\n".getBytes());
            output.write("height: 100vh;\n".getBytes());
            output.write("width: 100vw;\n".getBytes());
            output.write("padding: 0;\n".getBytes());
            output.write("}\n".getBytes());
            output.write("</style>\n".getBytes());
            output.write("</head>\n".getBytes());
            output.write("<body style=\"margin: 0; padding: 0;\">\n".getBytes());
            output.write("<div class=\"app\">\n".getBytes());
            output.write("<img src=\"https://dreamgames.com/Content/images/logo-white.svg\" alt=\"Dream logo\"/>\n".getBytes());;
            output.write("<h1>Hello Dream Games!</h1>\n".getBytes());
            output.write("</div>\n".getBytes());
            output.write("</html>\n".getBytes());
        output.flush();
    }
}
