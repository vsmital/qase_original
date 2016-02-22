package soc.qase.test.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vojtech.Smital on 22.2.2016.
 */
public class QaseServer implements Runnable {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    //TODO vsmital 27.2.2016 externalize
    private static final int PORT = 4000;

    private byte[] incomingBuffer = new byte[2048];
    private DatagramPacket incomingPacket = new DatagramPacket(incomingBuffer, incomingBuffer.length);

    private DatagramSocket datagramSocket;

    public QaseServer() {
        try {
            datagramSocket = new DatagramSocket(PORT);
        } catch (SocketException se) {
            LOGGER.error("Unable to initiate new DatagramSocket instance", se);
        }
    }

    public void run() {
        while (true) {
            try {
                datagramSocket.receive(incomingPacket);
            } catch (IOException ioe) {
                LOGGER.error("Unable to reiceve data from socket at port " + PORT, ioe);
                break;
            }
        }
    }

    public void sendData(final byte[] data) {
        final DatagramPacket outgoingPacket = new DatagramPacket(data, data.length);
        try {
            datagramSocket.send(outgoingPacket);
        } catch (IOException ioe) {
            LOGGER.error("Unable to send packet", ioe);
        }
    }

    public static int getPort() {
        return PORT;
    }
}
