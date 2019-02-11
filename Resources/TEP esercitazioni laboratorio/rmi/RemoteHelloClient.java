package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Client dell'oggetto remoto.
 */
public class RemoteHelloClient {
    /**
     * @param args
     * @throws NotBoundException
     * @throws RemoteException
     * @throws MalformedURLException
     */
    public static void main(String[] args) throws MalformedURLException,
            RemoteException, NotBoundException {
        RemoteHello remoteHello;
        String url = "rmi://127.0.0.1/remote_hello";

        remoteHello = (RemoteHello) Naming.lookup(url);
        System.out.println(remoteHello.sayHello("cliente"));
        System.out.println(remoteHello.getDate().toString());
    }

}
