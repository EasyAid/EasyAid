import java.net.*;
import java.io.*;
import java.util.*;

public class MulticastServer 
{
  public static void main(String[] args) throws IOException 
  {
    boolean attivo = true;               // per la ripetizione del servizio
    byte[] bufferOUT = new byte[1024];   // buffer di spedizione e ricezione
    int conta = 20;                      // secondi di attività del server 
    int porta = 6789;
    InetAddress gruppo = InetAddress.getByName("225.4.5.6");
   
    // creo il socket multicast  
    MulticastSocket socket = new MulticastSocket();
    
    // contenitore per il dato da trasmettere
    String dString = null;

    // ciclo di trasmissione   
    while (attivo)
    {
      // come messaggio viene inviata la data e l'ora di sistema 
      dString = new Date().toString();
      bufferOUT = dString.getBytes();
      // creo il DatagramPacket 
      DatagramPacket packet;
      packet = new DatagramPacket(bufferOUT, bufferOUT.length, gruppo, porta);
      // invio il dato 
      socket.send(packet);
      // introduco un ciclo di attesa di 1 secondo 
      try {
        Thread.sleep(1000);   //attesa di 1000 millisecondi 
      } catch (InterruptedException ie) {
        ie.printStackTrace();
      }
      conta--;
      if (conta==0){
        System.out.println("SERVER IN CHIUSURA. Buona serata.");
        attivo=false;
      }else{
        System.out.println("SERVER attivo per altri secondi "+conta);
      }  
     }
     // alla fine chiudo il socket
     socket.close();
  }
}



