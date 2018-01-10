package application;

import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

import javax.imageio.ImageIO;

import application.controller.MainWindowController;
import arduino.Arduino;

import java.io.File;
import java.io.IOException;


public class SystemTrayIcon extends Application {

	// Config

	private Stage primaryStage;
	

	// Models

    private Stage stage;


	@Override
	public void init() throws Exception {
		System.out.println("--- vor Window-Start ---");
	}


    @Override 
    public void start(Stage stage) {
		try {
				FXMLLoader loader = new FXMLLoader(SystemTrayIcon.class.getResource("view/fxml/MainWindowView.fxml"));
			//		FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/fxml/MainWindowViewTableView.fxml"));
				AnchorPane root = loader.load();
					
				stage.setTitle("Vorlage");
				stage.getIcons().add(new Image(SystemTrayIcon.class.getResourceAsStream( "view/images/img.png" )));
				stage.setMinWidth(400.00);
				stage.setMinHeight(300.00);
		
				Scene scene = new Scene(root);
				scene.getStylesheets().add(SystemTrayIcon.class.getResource("view/css/MainWindowCSS.css").toExternalForm());
			
		
				// letzte Position vom Fenster Speichern
			
				MainWindowController mainWindowController = loader.getController();
				mainWindowController.setMainWindowStage(this, stage);
			
				stage.setScene(scene);
				stage.show();
		 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

    @Override
    public void stop() throws Exception {
    	System.out.println("--- Ausführen nachdem Window geschlossen wird ---");
    	MainWindowController.arduino.serialWrite('0');
    	MainWindowController.arduino.serialWrite('b');
//    	MainWindowController.arduino.serialRead();
    	MainWindowController.arduino.closeConnection();
    	Platform.exit();
    	System.exit(0);
		System.out.println("--- Ende SystemTrayIcon ---");
    }

    

    public static void main(String[] args) throws IOException, java.awt.AWTException {
        launch(args);
    }
}
