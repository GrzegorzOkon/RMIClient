package rmi.client;

import rmi.server.input.Order;
import rmi.server.output.Bill;
import rmi.server.output.NotEnoughItemsException;
import rmi.server.BankServer;
import rmi.server.StoreServer;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class ClientApp {
    public static void main(String[] s){
        int storeRegistryPort = 8000;
        int bankRegistryPort = 8001;
        StoreServer storeServer = null;
        BankServer bankServer = null;
        
        try{
             System.setSecurityManager(new RMISecurityManager());
             storeServer = (StoreServer)Naming.lookup("//localhost:"+storeRegistryPort+"/sServer");
             bankServer = (BankServer)Naming.lookup("//localhost:"+bankRegistryPort+"/bServer");
             
             Order order = new Order("1234", "telewizor", 7, "12345667");
             Bill bill = storeServer.orderItems(order);
             System.out.println(bill); 
             
             System.out.println(bankServer.payBill(bill));          
             System.out.println(bankServer.getBankStatement("1234"));
             System.out.println(bankServer.getBankStatement("12345"));
        } catch (NotEnoughItemsException neie) {
        	System.out.println(neie);
        } catch (Exception ex ){
             System.out.println(ex);
        }
    }
}
