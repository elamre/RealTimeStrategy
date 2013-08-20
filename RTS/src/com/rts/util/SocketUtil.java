package com.rts.util;

/**
 * This class contains some useful socket and networking utilities.
 *
 * @author Elmar
 */

import java.io.*;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketUtil {

    /**
     * This class wraps a output socket into a buffered data outputStream
     *
     * @param socket The socked to wrap
     * @return Returns the Buffered dataOutputStream
     * @throws IOException Should get handled in the function itself
     */
    public static DataOutputStream wrapOutputStream(Socket socket)
            throws IOException {
        return new DataOutputStream(new BufferedOutputStream(
                socket.getOutputStream()));
    }

    /**
     * This class wraps a output socket into a buffered data inputStream
     *
     * @param socket The socked to wrap
     * @return Buffered dataInputStream
     * @throws IOException Should get handled in the function itself
     */
    public static DataInputStream wrapInputStream(Socket socket)
            throws IOException {
        return new DataInputStream(new BufferedInputStream(
                socket.getInputStream()));
    }

    /**
     * Closes the given {@link Closeable}.
     *
     * @param closeable the closeable
     */
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException exception) {
        }
    }

    /**
     * Closes the given {@link Socket}.
     *
     * @param socket the socket
     */
    public static void close(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException exception) {
        }
    }

    /**
     * Closes the given {@link ServerSocket}.
     *
     * @param serverSocket the server socket
     */
    public static void close(ServerSocket serverSocket) {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException exception) {
        }
    }

    /**
     * Closes the given {@link DatagramSocket}.
     *
     * @param socket the socket
     */
    public static void close(DatagramSocket socket) {
        if (socket != null) {
            socket.close();
        }
    }
}