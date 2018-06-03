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
	
    private static Bill rachunekDoOp³acenia = null;
    
    private Scene scena; 
    private BorderPane kontenerGlowny, konternerDolny;
    private GridPane kontenerSiatki;
    private HBox kontenerPrzyciskow; 
    private TextField poleIdentyfikatora, poleIloœciTowaru, poleNumeruRachunku;
    private ObservableList<String> produkty;
    private ComboBox<String> poleProduktów;
    
    private TextArea poleWyszukiwania;
    private Button zamówProdukt, op³aæZamówienie, pobierzHistoriêZleceñ;   


    
    // =============================================================================
    
    private void prepareScene(Stage primaryStage) {     	
        kontenerGlowny = new BorderPane();
        kontenerGlowny.setPadding(new Insets(15, 15, 15, 15)); 
        
        poleIdentyfikatora = new TextField();
        poleIdentyfikatora.setPrefSize(120, poleIdentyfikatora.getHeight());
        poleIdentyfikatora.setPromptText("Mirek");
        
        produkty = FXCollections.observableArrayList("telewizor", "pralka", "lodówka");  
        poleProduktów = new ComboBox(produkty);
        
        poleIloœciTowaru = new TextField();
        poleIloœciTowaru.setPrefSize(60, poleIdentyfikatora.getHeight());
        poleIloœciTowaru.setPromptText("4");
        
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
        kontenerSiatki.add(poleProduktów, 1, 1); 
        kontenerSiatki.add(new Label("Iloœæ:"), 2, 0);
        kontenerSiatki.add(poleIloœciTowaru, 2, 1);
        kontenerSiatki.add(new Label("Numer rachunku:"), 3, 0);
        kontenerSiatki.add(poleNumeruRachunku, 3, 1);
        kontenerSiatki.setPadding(new Insets(0, 0, 10, 0)); 
        
        kontenerGlowny.setTop(kontenerSiatki);
        
        poleWyszukiwania = new TextArea();
        kontenerGlowny.setCenter(poleWyszukiwania);
        
        konternerDolny = new BorderPane();
        konternerDolny.setPadding(new Insets(10, 0, 0, 0));  // tworzy odstêp nad kontenerem
        
        kontenerPrzyciskow = new HBox(16);
        
        zamówProdukt = new Button("Zamów produkt");
        op³aæZamówienie = new Button("Op³aæ zamówienie");
        pobierzHistoriêZleceñ = new Button("Pobierz historiê zleceñ");
        
        kontenerPrzyciskow.getChildren().add(zamówProdukt);
        kontenerPrzyciskow.getChildren().add(op³aæZamówienie);
        kontenerPrzyciskow.getChildren().add(pobierzHistoriêZleceñ);
        
        zamówProdukt.setOnAction((event) -> {	
        	try {
        		if (!poleIdentyfikatora.getText().equals("") && poleProduktów.getSelectionModel().isEmpty() == false && !poleIloœciTowaru.getText().equals("") & !poleNumeruRachunku.getText().equals("")) {	
        			Order order = new Order(poleIdentyfikatora.getText(), poleProduktów.getSelectionModel().getSelectedItem(), Integer.valueOf(poleIloœciTowaru.getText()), poleNumeruRachunku.getText());
        			Bill bill = null;
                
        			try {
        				bill = storeServer.orderItems(order);
        			} catch (RemoteException e) {
        				wyœwietlRaport(e.getMessage());
        			} catch (NotEnoughItemsException e) {
        				wyœwietlRaport(e.getMessage());
        			}

        			if (bill != null) {
        				rachunekDoOp³acenia = bill;
        				wyœwietlRaport(bill.toString());
        			}
        		}
        	} catch (Exception ex) {}  //przechwytuje b³¹d rzutowania do typu liczbowego z tekstu
		});
        
        op³aæZamówienie.setOnAction((event) -> {
        	try {
        		if (rachunekDoOp³acenia != null) {
    				if (bankServer.payBill(rachunekDoOp³acenia) == true) {
    					wyœwietlRaport("Zamówienie zosta³o op³acone.");
    				};
        		}
			} catch (RemoteException e) {
				wyœwietlRaport(e.getMessage());
			}
		});
        
        pobierzHistoriêZleceñ.setOnAction((event) -> {	
        	BankStatement historiaZleceñOp³at = null;
        	
            try {
            	historiaZleceñOp³at = bankServer.getBankStatement(poleIdentyfikatora.getText());
			} catch (RemoteException e) {
				wyœwietlRaport(e.getMessage());
			} catch (NoSuchClientException e) {
				wyœwietlRaport(e.getMessage());
			}     
            
            if (historiaZleceñOp³at != null) wyœwietlRaport(historiaZleceñOp³at.toString());
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
    
	public void wyœwietlRaport(String raport) {
		poleWyszukiwania.appendText(raport + "\n");
	}
}
