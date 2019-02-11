import java.net.*;
import java.io.*;

public class MulticastClient
{
  public static void main(String[] args) throws IOException 
  {
    byte[] bufferIN = new byte[1024];          // buffer di ricezione
    //parameri del server 
    int porta = 6789;
    String gruppo = "225.4.5.6";
    
    // creazione del socket sulla porta 
    MulticastSocket socket = new MulticastSocket(porta);
    // mi aggiungo al gruppo Multicast 
    socket.joinGroup(InetAddress.getByName(gruppo));
    
    // creo il DatagramPacket e mi metto in ricezione 
    DatagramPacket packetIN = new DatagramPacket(bufferIN, bufferIN.length);
    socket.receive(packetIN); 
    
    // Visualizzo i  parametri ed i dati ricevuti
    System.out.println("Ho ricevuto dati di lunghezza: "+packetIN.getLength()
       + " da : " + packetIN.getAddress().toString() 
       + " porta :" + packetIN.getPort());
    System.out.write(packetIN.getData(),0,packetIN.getLength());
    System.out.println();
    
    // al termine della ricezione lascio il gruppo
    socket.leaveGroup(InetAddress.getByName(gruppo));
    // chiudoi il socket 
    socket.close();
  }
}

