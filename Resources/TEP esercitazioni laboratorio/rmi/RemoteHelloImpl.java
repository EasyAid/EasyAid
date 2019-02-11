
package rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;



/**
 * L'implementazione dell'oggetto remoto.
  */
public class RemoteHelloImpl extends UnicastRemoteObject implements RemoteHello {

    public RemoteHelloImpl() throws RemoteException {
        super();
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public String sayHello(String name) throws RemoteException {
        return "ciao " + name + "!";
    }


    public Date getDate() throws RemoteException {
        return new Date();
    }

    public static void main(String[] args) throws RemoteException,
            MalformedURLException, AlreadyBoundException {
        RemoteHello remoteHello = new RemoteHelloImpl();
        Naming.bind("remote_hello", remoteHello);
        System.out.println("oggetto registrato");
    }
}
