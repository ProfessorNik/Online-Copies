package ru.nsu.onlinecopiesofyourself.infrastructure.network;

import ru.nsu.onlinecopiesofyourself.application.model.MessageInfo;
import ru.nsu.onlinecopiesofyourself.config.AppProperties;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.*;

public class Transceiver {
    private final MulticastSocket multicastSocket;
    private final String hostname;

    private final int port;
    private final int bufferSize;

    private final NetworkInterface networkInterface;
    private final SocketAddress socketAddress;


    public Transceiver(){
        try{
            var appProperties = AppProperties.getInit();
            hostname = appProperties.getHostname();
            port = appProperties.getPort();
            bufferSize = appProperties.getBufferSize();
            multicastSocket = new MulticastSocket(port);
            networkInterface = multicastSocket.getNetworkInterface();
            socketAddress = new InetSocketAddress(hostname, port);
            multicastSocket.joinGroup(socketAddress, networkInterface);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public MessageInfo receiveMassage(){
        DatagramPacket packet = receivePacket();
        return new MessageInfo(new String(packet.getData(), packet.getOffset(),packet.getLength()),
                packet.getAddress().toString());
    }

    private DatagramPacket receivePacket() {
        try{
            var buffer = new byte[bufferSize];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            multicastSocket.receive(packet);
            return packet;
        } catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    public void sendMessage(String message){
        try{
            byte[] msg = message.getBytes();
            DatagramPacket packet = new DatagramPacket(msg, msg.length, socketAddress);
            multicastSocket.send(packet);
            System.out.println("message: " + message + " was send");
        } catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    public void closeConnection(){
        try {
            multicastSocket.leaveGroup(socketAddress, networkInterface);
            multicastSocket.close();
        } catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }
}
