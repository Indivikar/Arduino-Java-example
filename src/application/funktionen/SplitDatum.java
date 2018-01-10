package application.funktionen;

public class SplitDatum {

	private String tag;
	private String monat;
	private String jahr;

	public SplitDatum() {

	}

	public SplitDatum(String datum) {
		System.out.println("SplitDatum: " + datum);

    	String[] part = datum.split("-");
    	this.jahr = part[0];    	
    	this.monat = part[1];
    	this.tag = part[2];

        System.out.println("Tag: " + tag);
        System.out.println("Monat: " + monat);
        System.out.println("Jahr: " + jahr);

	}
	
	
	public String splitAndGetTag(String datum) {		
    	String[] part = datum.split("-");	
		return part[2];			
	}	
	
	public String splitAndGetMonat(String datum) {		
    	String[] part = datum.split("-");	
		return part[1];			
	}	
	
	public String splitAndGetJahr(String datum) {		
    	String[] part = datum.split("-");	
		return part[0];			
	}	
	

	// Getter
	public final String getTag() {return tag;}
	public final String getMonat() {return monat;}
	public final String getJahr() {return jahr;}

	// Setter
	public final void setTag(String tag) {this.tag = tag;}
	public final void setMonat(String monat) {this.monat = monat;}
	public final void setJahr(String jahr) {this.jahr = jahr;}

}
