package tests;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static org.mockito.Mockito.*;

public class MainTest {

    @Test
    public void testMain() throws IOException {
        testHandleRequest();
    }
    @Test
    public void testHandleRequest() throws IOException {
        // Create a mock Socket
        Socket mockSocket = mock(Socket.class);
        InputStream mockInputStream = new ByteArrayInputStream("GET /?test=test HTTP/1.1".getBytes());
        OutputStream mockOutputStream = mock(OutputStream.class);

        // Stub the Socket methods
        when(mockSocket.getInputStream()).thenReturn(mockInputStream);
        when(mockSocket.getOutputStream()).thenReturn(mockOutputStream);

        // Call the method under test
        main.Main.handleRequest(mockSocket);

        // Verify that the expected interactions occurred
        verify(mockOutputStream, times(5)).write(any(byte[].class));
        verify(mockSocket, times(1)).close();
    }
}
