package de.hdm.itProjektGruppe4.shared.report;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Report, der alle Konten alle Kunden darstellt.
 * Die Klasse tr#gt keine weiteren Attribute- und Methoden-Implementierungen,
 * da alles Notwendige schon in den Superklassen vorliegt. Ihre Existenz ist 
 * dennoch wichtig, um bestimmte Typen von Reports deklarieren und mit ihnen 
 * objektorientiert umgehen zu können.
 * 
 * @author Thies
 * @author Yücel
 * @author Oikonomou
 */

public class AlleNachrichtenReport extends CompositeReport 
implements Serializable{


	
	private static final long serialVersionUID = 1L;

	public ArrayList<Row> getRows() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addRow(Row headline) {
		// TODO Auto-generated method stub
		
	}
	
}
