package rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.server.input.Order;
import rmi.server.output.Bill;

public interface StoreServer extends Remote {
    public Bill orderItems(Order order) throws RemoteException;
}
