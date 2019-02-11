//non eseguire su macchine Windows
import java.net.*;
import java.io.*;
public class PrimoClient {
 public static void main(String args[]){
  String nomeserver ="mioPC";           // nome del server
  int portaserver = 13;                 // porta per servizio data e ora
  Socket mioSocket = null;
  try
  {
   mioSocket = new Socket (nomeserver,portaserver);
   // la apertura di una socket può generare un ConnectException
   InputStream inputs = mioSocket.getInputStream();
   InputStreamReader inputr = new InputStreamReader(inputs);
   BufferedReader bufferr = new BufferedReader(inputr);
   String linea = bufferr.readLine();
   System.out.println(linea);
   mioSocket.close();
  }
  catch (UnknownHostException e){
   System.err.println("Server sconosciuto"); }
  catch (Exception e){
   System.err.println(e);}
  }
}
