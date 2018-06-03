package rmi.client;

import rmi.server.input.Order;
import rmi.server.output.BankStatement;
import rmi.server.output.Bill;
import rmi.server.output.NoSuchClientException;
import rmi.server.output.NotEnoughItemsException;
import rmi.server.BankServer;
import rmi.server.StoreServer;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ClientApp extends Application {
    private static int storeRegistryPort = 8000;
    private static int bankRegistryPort = 8001;
    private static StoreServer storeServer = null;
    private static BankServer bankServer = null;
	
    private static Bill rachunekDoOp�acenia = null;
    
    private Scene scena; 
    private BorderPane kontenerGlowny, konternerDolny;
    private GridPane kontenerSiatki;
    private HBox kontenerPrzyciskow; 
    private TextField poleIdentyfikatora, poleIlo�ciTowaru, poleNumeruRachunku;
    private ObservableList<String> produkty;
    private ComboBox<String> poleProdukt�w;
    
    private TextArea poleWyszukiwania;
    private Button zam�wProdukt, op�a�Zam�wienie, pobierzHistori�Zlece�;   


    
    // =============================================================================
    
    private void prepareScene(Stage primaryStage) {     	
        kontenerGlowny = new BorderPane();
        kontenerGlowny.setPadding(new Insets(15, 15, 15, 15)); 
        
        poleIdentyfikatora = new TextField();
        poleIdentyfikatora.setPrefSize(120, poleIdentyfikatora.getHeight());
        poleIdentyfikatora.setPromptText("Mirek");
        
        produkty = FXCollections.observableArrayList("telewizor", "pralka", "lod�wka");  
        poleProdukt�w = new ComboBox(produkty);
        
        poleIlo�ciTowaru = new TextField();
        poleIlo�ciTowaru.setPrefSize(60, poleIdentyfikatora.getHeight());
        poleIlo�ciTowaru.setPromptText("4");
        
        poleNumeruRachunku = new TextField();
        poleNumeruRachunku.setPrefSize(200, poleNumeruRachunku.getHeight());
        poleNumeruRachunku.setPromptText("11 2222 3333 4444 5555");
        
        kontenerSiatki = new GridPane();
        kontenerSiatki.setVgap(4);
        kontenerSiatki.setHgap(8);
        kontenerSiatki.setPadding(new Insets(5, 5, 5, 5));
        kontenerSiatki.add(new Label("Identyfikator:"), 0, 0);
        kontenerSiatki.add(poleIdentyfikatora, 0, 1);
        kontenerSiatki.add(new Label("Produkt:"), 1, 0);
        kontenerSiatki.add(poleProdukt�w, 1, 1); 
        kontenerSiatki.add(new Label("Ilo��:"), 2, 0);
        kontenerSiatki.add(poleIlo�ciTowaru, 2, 1);
        kontenerSiatki.add(new Label("Numer rachunku:"), 3, 0);
        kontenerSiatki.add(poleNumeruRachunku, 3, 1);
        kontenerSiatki.setPadding(new Insets(0, 0, 10, 0)); 
        
        kontenerGlowny.setTop(kontenerSiatki);
        
        poleWyszukiwania = new TextArea();
        kontenerGlowny.setCenter(poleWyszukiwania);
        
        konternerDolny = new BorderPane();
        konternerDolny.setPadding(new Insets(10, 0, 0, 0));  // tworzy odst�p nad kontenerem
        
        kontenerPrzyciskow = new HBox(16);
        
        zam�wProdukt = new Button("Zam�w produkt");
        op�a�Zam�wienie = new Button("Op�a� zam�wienie");
        pobierzHistori�Zlece� = new Button("Pobierz histori� zlece�");
        
        kontenerPrzyciskow.getChildren().add(zam�wProdukt);
        kontenerPrzyciskow.getChildren().add(op�a�Zam�wienie);
        kontenerPrzyciskow.getChildren().add(pobierzHistori�Zlece�);
        
        zam�wProdukt.setOnAction((event) -> {	
        	try {
        		if (!poleIdentyfikatora.getText().equals("") && poleProdukt�w.getSelectionModel().isEmpty() == false && !poleIlo�ciTowaru.getText().equals("") & !poleNumeruRachunku.getText().equals("")) {	
        			Order order = new Order(poleIdentyfikatora.getText(), poleProdukt�w.getSelectionModel().getSelectedItem(), Integer.valueOf(poleIlo�ciTowaru.getText()), poleNumeruRachunku.getText());
        			Bill bill = null;
                
        			try {
        				bill = storeServer.orderItems(order);
        			} catch (RemoteException e) {
        				wy�wietlRaport(e.getMessage());
        			} catch (NotEnoughItemsException e) {
        				wy�wietlRaport(e.getMessage());
        			}

        			if (bill != null) {
        				rachunekDoOp�acenia = bill;
        				wy�wietlRaport(bill.toString());
        			}
        		}
        	} catch (Exception ex) {}  //przechwytuje b��d rzutowania do typu liczbowego z tekstu
		});
        
        op�a�Zam�wienie.setOnAction((event) -> {
        	try {
        		if (rachunekDoOp�acenia != null) {
    				if (bankServer.payBill(rachunekDoOp�acenia) == true) {
    					wy�wietlRaport("Zam�wienie zosta�o op�acone.");
    				};
        		}
			} catch (RemoteException e) {
				wy�wietlRaport(e.getMessage());
			}
		});
        
        pobierzHistori�Zlece�.setOnAction((event) -> {	
        	BankStatement historiaZlece�Op�at = null;
        	
            try {
            	historiaZlece�Op�at = bankServer.getBankStatement(poleIdentyfikatora.getText());
			} catch (RemoteException e) {
				wy�wietlRaport(e.getMessage());
			} catch (NoSuchClientException e) {
				wy�wietlRaport(e.getMessage());
			}     
            
            if (historiaZlece�Op�at != null) wy�wietlRaport(historiaZlece�Op�at.toString());
		});
         
        konternerDolny.setRight(kontenerPrzyciskow);
        kontenerGlowny.setBottom(konternerDolny);
        scena = new Scene(kontenerGlowny, 800, 600);
    }
    
    // =============================================================================
    
    @Override
    public void start(Stage primaryStage) {   
    	
        prepareScene(primaryStage);
        
        primaryStage.setTitle("RMI Client");
        primaryStage.setScene(scena);
        primaryStage.show();
    }

    // =============================================================================

    public static void main(String[] args) { 
    	
        try{
             System.setSecurityManager(new RMISecurityManager());
             storeServer = (StoreServer)Naming.lookup("//localhost:"+storeRegistryPort+"/sServer");
             bankServer = (BankServer)Naming.lookup("//localhost:"+bankRegistryPort+"/bServer");
        } catch (Exception ex ){
             System.out.println(ex);
        }
        
        launch(args);
    }

    // =============================================================================
    
	public void wy�wietlRaport(String raport) {
		poleWyszukiwania.appendText(raport + "\n");
	}
}
