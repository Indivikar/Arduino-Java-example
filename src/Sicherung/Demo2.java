package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.TwoWaySerialComm.SerialReader;
import main.TwoWaySerialComm.SerialWriter;
 
public class Demo2 extends Application {
	
	CommPort commPort;
	
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Arduino - LED demo ");
        
        Button buttonConnect = new Button();
        buttonConnect.setPrefWidth(150);
        buttonConnect.setText("Connect");
        buttonConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Connect");
                
                try
                {
                    (new TwoWaySerialComm()).connect("COM3");
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
                new SerialWriter ( 1 );
            }
        });
        
        
        Button buttonAus = new Button();  
        buttonAus.setPrefWidth(150);
        buttonAus.setText("Aus");
        buttonAus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Aus");
                
            }
        });
        
        
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.getChildren().addAll(buttonConnect, buttonAn, buttonAus);
        

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
    
    private void arduinoConnect() {
		
//    	ArduinoSerial arduino = new ArduinoSerial("COM3");

	}
    
    private void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            
            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(57600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                
                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();
                
                (new Thread(new SerialReader(in))).start();
                (new Thread(new SerialWriter(out))).start();

                System.out.println("Connected");
            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        } 
        
    }
    
    public static class SerialReader implements Runnable 
    {
        InputStream in;
        
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
            try
            {
                while ( ( len = this.in.read(buffer)) > -1 )
                {
                    System.out.print(new String(buffer,0,len));
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }
    
    public static class SerialWriter implements Runnable 
    {
        OutputStream out;
        
        public SerialWriter ( OutputStream out )
        {
            this.out = out;
        }
        
        public SerialWriter ( int i )
        {
        	try {
        		System.out.println("sende " + i);
				this.out.write(i);
				System.out.println("gesendet");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
        
        public void run ()
        {
            try
            {                
                int c = 0;
                while ( ( c = System.in.read()) > -1 )
                {
                	System.out.println("sende " + c);
                    this.out.write(c);
                    System.out.println("gesendet");
                }                
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }
}