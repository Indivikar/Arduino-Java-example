package application.model;

import application.controller.MainWindowController;
import javafx.concurrent.Task;

public class ArduinoInputReader {

	MainWindowController mainWindowController;
	
	public ArduinoInputReader(MainWindowController mainWindowController) {
		this.mainWindowController = mainWindowController;
					
//		new Thread(task).start();
	}
	
	Task<Void> task = new Task<Void>() {
	    @Override public Void call() {
	    	    	
	    	while (true) {
				
	    		
				try {										
					Thread.sleep(500);
					MainWindowController.arduino.serialWrite('c');
					MainWindowController.arduino.serialRead();
					System.out.println("thread");
					
				} catch (InterruptedException e) {
					System.out.println("nicht verbunden");
					
				}
				
			}
	    }
	};
	
	
	public void action(String input) {
		
		System.out.println("read -> " + input);
		
		if (input.equals("noch verbunden")) {
			
		}
		
		if (input.equals("An")) {
			mainWindowController.getButtonAn().setDisable(true);
			mainWindowController.getButtonAus().setDisable(false);
		} 
		
		if (input.equals("Aus")) {
			mainWindowController.getButtonAn().setDisable(false);
			mainWindowController.getButtonAus().setDisable(true);
			
		} 
		
	}
	
}
