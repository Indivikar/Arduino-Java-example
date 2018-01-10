package application.controller;

import com.fazecast.jSerialComm.SerialPort;


import application.SystemTrayIcon;
import application.model.ArduinoInputReader;
import arduino.Arduino;
import javafx.beans.property.SimpleDoubleProperty;

import javafx.concurrent.Task;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainWindowController {

	// Stage
	private SystemTrayIcon main;
	private Stage primaryStage;

	// Models
	private ArduinoInputReader arduinoInputReader;

	private Thread t1;
	
	// Properties
	SimpleDoubleProperty propDoubleLabelMeldungDBEintrag = new SimpleDoubleProperty();

	public static Arduino arduino;
	
	// View's
	@FXML private AnchorPane mainAnchorPane;
	
	// Steuerung
	@FXML private VBox vBoxSteuerung;
	@FXML private Button buttonAn;
	@FXML private Button buttonAus;
	
	// Connect
	@FXML private ComboBox<String> comboBoxPortList;
	@FXML private Button buttonRefreshPortList;
	@FXML private HBox hBoxPunkt;
	@FXML private Button buttonConnect;
	@FXML private Button buttonDisconnect;
	


	// Action
	@FXML public void actionButtonAn(){
		System.out.println("Button An");		
		
		if(t1.isAlive()) {
			t1.resume();
		} else {
			t1.start();
		}
		
		buttonAn.setDisable(true);
		buttonAus.setDisable(false);
//        arduino.serialWrite('d');
//        arduino.serialRead();
        
	}

	@FXML public void actionButtonAus(){
		System.out.println("Button Aus");
//		t1.stop();
//		t1.interrupt();
//		t1.resume();
		t1.suspend();
		
		buttonAn.setDisable(false);
		buttonAus.setDisable(true);
//        arduino.serialWrite('0');
//        arduino.serialRead();
		
	}
	
	
	Task<Void> task = new Task<Void>() {
	    @Override public Void call() {
	    	    	
	    	int stepTime = 2000;
	    	
	    	while (true) {
					    		
				try {			
					Thread.sleep(stepTime);
					
						
					MainWindowController.arduino.serialWrite('b');										
					MainWindowController.arduino.serialWrite('1');	
					
					MainWindowController.arduino.serialWrite('c');
					MainWindowController.arduino.serialRead();	
					
					Thread.sleep(stepTime);
					System.out.println("----------------------------");
					
					MainWindowController.arduino.serialWrite('0');						
					MainWindowController.arduino.serialWrite('a');
					
					MainWindowController.arduino.serialWrite('c');
					MainWindowController.arduino.serialRead();	
												
					System.out.println("----------------------------");
//					System.out.println("thread");
					
				} catch (InterruptedException e) {
					System.out.println("nicht verbunden");
					
				}
				
			}
	    }
	};
	
	
	@FXML public void actionButtonConnect(){
		System.out.println("Connect");
        try
        {
        	connect();
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	@FXML public void actionButtonDisconnect(){		
		arduino.closeConnection();
		vBoxSteuerung.setDisable(true);		
		buttonConnect.setDisable(false);
		buttonDisconnect.setDisable(true);
		comboBoxPortList.setDisable(false);
		buttonRefreshPortList.setDisable(false);
		System.out.println("Disconnect");
	}
	
	@FXML public void actionButtonRefreshPortList(){
		System.out.println("Connect");
		addPortList();
	}
	
	public void initialize(){
		t1 = new Thread(task);
		vBoxSteuerung.setDisable(true);
		buttonDisconnect.setDisable(true);
		addPortList();
	}

	private void addBindings() {
		
	}

	 private void addPortList() {
		 	comboBoxPortList.getItems().clear();
	        SerialPort[] portNames = SerialPort.getCommPorts();
			for(int i = 0; i < portNames.length; i++) {
				comboBoxPortList.getItems().add(portNames[i].getSystemPortName());					
			}
			comboBoxPortList.getSelectionModel().select(0);
		}
	
    private void connect() throws Exception
    {
   
			 arduino = new Arduino(comboBoxPortList.getSelectionModel().getSelectedItem(), 9600, this);
			 if(arduino.openConnection()){				
				arduinoInputReader = new ArduinoInputReader(this);
				vBoxSteuerung.setDisable(false);
				buttonConnect.setDisable(true);
				buttonDisconnect.setDisable(false);
				comboBoxPortList.setDisable(true);
				buttonRefreshPortList.setDisable(true);
				System.out.println("Connected");
			 } else {
				 vBoxSteuerung.setDisable(true);
				 System.err.println("can not Connect");
			 }

    }
	
    // Getter
    public Button getButtonAn() {return buttonAn;}
    public Button getButtonAus() {return buttonAus;}
    public ComboBox<String> getComboBoxPortList() {return comboBoxPortList;}
    public HBox gethBoxPunkt() {return hBoxPunkt;}
    public Button getButtonConnect() {return buttonConnect;}
    public Button getButtonDisconnect() {return buttonDisconnect;}
    public ArduinoInputReader getArduinoInputReader() {return arduinoInputReader;}

	// Setter
    public void setButtonAn(Button buttonAn) {this.buttonAn = buttonAn;}
    public void setButtonAus(Button buttonAus) {this.buttonAus = buttonAus;}
    public void setComboBoxPortList(ComboBox<String> comboBoxPortList) {this.comboBoxPortList = comboBoxPortList;}
    public void sethBoxPunkt(HBox hBoxPunkt) {this.hBoxPunkt = hBoxPunkt;}
    public void setButtonConnect(Button buttonConnect) {this.buttonConnect = buttonConnect;}
    public void setButtonDisconnect(Button buttonDisconnect) {this.buttonDisconnect = buttonDisconnect;}
    
    
	public void setMainWindowStage(SystemTrayIcon systemTrayIcon, Stage stageEinstellungen) {
		
		
	}

}
