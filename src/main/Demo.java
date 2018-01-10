package main;



import com.fazecast.jSerialComm.SerialPort;

import arduino.Arduino;
import gnu.io.CommPort;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

 
public class Demo extends Application {
	
	CommPort commPort;
	public static Arduino arduino;
	ComboBox<String> comboBoxPortList;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Arduino - LED demo ");
        
        
        
        comboBoxPortList = new ComboBox<String>();        
        comboBoxPortList.setEditable(true);
        

        
        
        
        Button buttonConnect = new Button();
        buttonConnect.setPrefWidth(150);
        buttonConnect.setText("Connect");
        buttonConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("try to Connect...");
                
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
        });
        
        
        Button buttonAn = new Button();
        buttonAn.setPrefWidth(150);
        buttonAn.setText("An");
        buttonAn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("An");
                
//                new SerialWriter ( 1 );
                arduino.serialWrite('d');
                arduino.serialRead();
                
            }
        });
        
        
        Button buttonAus = new Button();  
        buttonAus.setPrefWidth(150);
        buttonAus.setText("Aus");
        buttonAus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Aus");
                arduino.serialWrite('0');
                arduino.serialRead();
            }
        });
        
        
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.getChildren().addAll(comboBoxPortList, buttonConnect, buttonAn, buttonAus);
        

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
        
        addItems();
        
    }
    
    @Override
    public void stop() throws Exception {
        arduino.serialWrite('0');
        arduino.serialRead();
    }
    
    private void arduinoConnect() {
		
//    	ArduinoSerial arduino = new ArduinoSerial("COM3");

	}
    
    private void addItems() {
        SerialPort[] portNames = SerialPort.getCommPorts();
		for(int i = 0; i < portNames.length; i++) {
			comboBoxPortList.getItems().add(portNames[i].getSystemPortName());					
		}
		comboBoxPortList.getSelectionModel().select(0);
	}
    
    private void connect() throws Exception
    {
    	
			 arduino = new Arduino(comboBoxPortList.getSelectionModel().getSelectedItem(),9600, null);
			 if(arduino.openConnection()){
				System.out.println("Connected");
			 } else {
				 System.err.println("can not Connect");
			 }
			 
		
	
    }
    
}