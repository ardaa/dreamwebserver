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

            // read and print request
            String line = reader.readLine();
            String[] query = line.split(" ");

            // parse and print parameters and values from the query string
            try{
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
        output.write("<h1>Hello World!</h1>".getBytes());
        output.flush();
    }
}
