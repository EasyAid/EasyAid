package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * L'interfaccia dell'oggetto remoto
 */
public interface RemoteHello extends Remote {
    public String sayHello(String name) throws RemoteException;
    public Date getDate() throws  RemoteException;
}
