import java.net.*; 
import java.io.*; 
class ServerThread extends Thread { 
  ServerSocket server      = null;
  Socket client            = null;
  String stringaRicevuta   = null;
  String stringaModificata = null;
  BufferedReader   inDalClient; 
  DataOutputStream outVersoClient;
  
  public ServerThread (Socket socket){ 
    this.client = socket; 
  } 
   
  public void run(){ 
  try{
    comunica();  
  }catch (Exception e){ 
    e.printStackTrace(System.out);  } 
  } 
  
  public void comunica ()throws Exception{ 
    inDalClient      = new BufferedReader(new InputStreamReader (client.getInputStream()));
    outVersoClient   = new DataOutputStream(client.getOutputStream());
    for (;;){ 
      stringaRicevuta = inDalClient.readLine();
      if (stringaRicevuta == null || stringaRicevuta.equals("FINE")){ 
        outVersoClient.writeBytes(stringaRicevuta+" (=>server in chiusura...)" + '\n'); 
        System.out.println("Echo sul server in chiusura  :" + stringaRicevuta); 
        break;
      }
      else{
        outVersoClient.writeBytes(stringaRicevuta+" (ricevuta e ritrasmessa)" + '\n'); 
        System.out.println("6 Echo sul server :" + stringaRicevuta); 
      }
    } 
    outVersoClient.close(); 
    inDalClient.close(); 
    System.out.println("9 Chiusura socket" + client); 
    client.close(); 
  } 
} 
  
public class MultiServer{ 
  public void start(){ 
    try{
      ServerSocket serverSocket = new ServerSocket(6789); 
      for (;;) 
      { 
        System.out.println("1 Server in attesa ... "); 
        Socket socket = serverSocket.accept(); 
        System.out.println("3 Server socket  " + socket); 
        ServerThread serverThread = new ServerThread(socket); 
        serverThread.start(); 
      } 
    }
    catch (Exception e){
      System.out.println(e.getMessage());
      System.out.println("Errore durante l'istanza del server !");
      System.exit(1);
    }
  } 

  public static void main (String[] args){ 
     MultiServer tcpServer = new MultiServer(); 
     tcpServer.start(); 
   } 
}
