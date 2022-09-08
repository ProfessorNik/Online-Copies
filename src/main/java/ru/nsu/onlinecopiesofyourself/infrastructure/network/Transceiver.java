package ru.nsu.onlinecopiesofyourself.infrastructure.network;

import ru.nsu.onlinecopiesofyourself.config.AppProperties;
import ru.nsu.onlinecopiesofyourself.infrastructure.dto.MessageDto;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.*;

public class Transceiver {
    private final MulticastSocket multicastSocket;
    private final int bufferSize;
    private final NetworkInterface networkInterface;
    private final SocketAddress socketAddress;


    public Transceiver(String hostname){
        try{
            var appProperties = AppProperties.getInit();
            var port = appProperties.getPort();
            bufferSize = appProperties.getBufferSize();
            multicastSocket = new MulticastSocket(port);
            networkInterface = multicastSocket.getNetworkInterface();
            socketAddress = new InetSocketAddress(hostname, port);
            multicastSocket.joinGroup(socketAddress, networkInterface);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public MessageDto receiveMassage(){
        DatagramPacket packet = receivePacket();
        return new MessageDto(new String(packet.getData(), packet.getOffset(),packet.getLength()),
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
