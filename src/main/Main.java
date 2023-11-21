package main;
import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //init server socket at port ENV PORT
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(System.getenv("PORT")));
        System.out.println("Listening at " + System.getenv("PORT"));

        while (true) {
            //read request
            Socket socket = serverSocket.accept();
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            //read and print request 
            String line = reader.readLine();
            String[] query = line.split(" ");
            //parse and print parameters and values from the query string
            String[] params = query[1].split("\\?")[1].split("&");
            for (String param : params) {
                String[] pair = param.split("=");
                System.out.println(pair[0] + ": " + pair[1]);
            }


            OutputStream output = socket.getOutputStream();
            output.write("HTTP/1.1 200 OK\r\n".getBytes());
            output.write("Content-Type: text/html\r\n".getBytes());
            output.write("\r\n".getBytes());
            //write index.html in src
            output.flush();
            File file = new File("src/main/index.html");

            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            output.write(bytes);
            output.write("\r\n".getBytes());
            output.flush();
            socket.close();
        }
    }
}