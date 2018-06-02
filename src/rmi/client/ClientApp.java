package rmi.client;

import rmi.server.input.Order;
import rmi.server.output.Bill;
import rmi.server.StoreServer;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class ClientApp {
    public static void main(String[] s){
        int registryPort = 8000;
        StoreServer server = null;
        try{
             System.setSecurityManager(new RMISecurityManager());
             server = (StoreServer)Naming.lookup("//localhost:"+registryPort+"/sServer");
             
             Order order = new Order("1234", "telewizor", 7, "12345667");
             Bill bill = server.orderItems(order);
             System.out.println(bill);    
        }
        catch (Exception ex ){
             System.out.println(ex);
        }
    }
}
