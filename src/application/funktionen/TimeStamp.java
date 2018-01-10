package application.funktionen;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {

    private static final SimpleDateFormat zeit = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat datum = new SimpleDateFormat("yyyy-MM-dd");

    public TimeStamp() {
		// TODO Auto-generated constructor stub
	}

    public String getAktuelleZeit() {

    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());

   		return zeit.format(timestamp);
	}

    public String getAktuellesDatum() {

    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());

   		return datum.format(timestamp);
	}
    
    
    public static void main(String[] args) {

        //method 1
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        System.out.println(timestamp);
//
//        //method 2 - via Date
//        Date date = new Date();
//        System.out.println(new Timestamp(date.getTime()));
//
//        //return number of milliseconds since January 1, 1970, 00:00:00 GMT
//        System.out.println(timestamp.getTime());

        
        
    }

}
