package de.hdm.itProjektGruppe4.client.gui;


import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.itProjektGruppe4.shared.MessagingAdministration;
import de.hdm.itProjektGruppe4.shared.MessagingAdministrationAsync;
import de.hdm.itProjektGruppe4.shared.bo.Hashtagabonnement;
import de.hdm.itProjektGruppe4.shared.bo.Nutzer;
import de.hdm.itProjektGruppe4.shared.bo.Nutzerabonnement;

/**
 * Zeigt mehrere Tabellen mit den erforderlichen Daten an, hier in Verbindung mit Nutzer
 * 
 * @author Di Giovanni
 *
 */


		public class NutzeraboTabelle {
			
			
			/**
			 * Erstellung von verschiedenen Panels
			 */

			HorizontalPanel hauptP = new HorizontalPanel();
			
			HorizontalPanel links = new HorizontalPanel();
			HorizontalPanel rechts = new HorizontalPanel();
			
			MessagingAdministrationAsync myAsync = GWT.create(MessagingAdministration.class);
			
			
			/**
			 * Stellt die Tabellen da
			 * Panel + FlexTable
			 */
			
			public Widget zeigeTabelle() {
				
				
				/**
				 * Flextable wird erstellt die alle Abonnierten Nutzer anzeigt
				 */
				
				final FlexTable flexTable = new FlexTable();
				
								
				flexTable.setText(0, 1, "Abonnnierte Nutzer");
				flexTable.setText(0, 2, "Entfernen");
				
				/**
				 * Liest alle Daten aus der DB und füllt sie in die FlexTable
				 */
				
				myAsync.getAllNutzerabonnementsByBeobachteteID(Cookies.getCookie("userID"),new AsyncCallback<ArrayList<Nutzerabonnement>>() {
					
					@Override
					public void onSuccess(ArrayList<Nutzerabonnement> result) {
						int zeileCounter = 1;
						
						for (final Nutzerabonnement na : result) {

							/**
							 * Button wird erstellt der später entfernen soll
							 */
							
							Button bModifizieren = new Button("Entfernen");
						
							Label beoID = new Label(na.getNutzerNickname().getNickname());
							Label followID = new Label(na.getNutzerNickname().getNickname());
							
							
							
//							flexTable.setText(zeileCounter, 0, Cookies.getCookie("userMail"));
							flexTable.setWidget(zeileCounter, 1, followID);
							flexTable.setWidget(zeileCounter, 2, bModifizieren);
							
							zeileCounter ++;
							
							/**
							 * Der erstellte Button wird aufgerufen + neuer Clickhanlder
							 */
							
							bModifizieren.addClickHandler(new ClickHandler() {
								
								@Override
								public void onClick(ClickEvent event) {
								
									loeschenFollower(na);
									
								}
							});
					}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Ein fehler ist aufgetreten: " + caught);
						
					}
				});
				
			 /**
			  * eine neue FlexTable wird erstellt die alle Nutzer anzeigt 
			  */
				 
				 final  FlexTable flexTable2 = new FlexTable();
				 
				 flexTable2.setText(0, 0, "EMail");
				 flexTable2.setText(0, 1, "Folgen");
				 flexTable2.setBorderWidth(5);
				 
				 
				 /**
				  * Style für die CSS
				  */
				 
				 flexTable2.addStyleName("nabo");
				 flexTable2.getCellFormatter().addStyleName(0, 0, "naboColumn");
				 flexTable2.getCellFormatter().addStyleName(0, 1, "naboColumn");
				
		
				 /**
					 * Liest alle Daten aus der DB und füllt sie in die FlexTable
					 */
				 
				 myAsync.getAllNutzer(new AsyncCallback<ArrayList<Nutzer>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(ArrayList<Nutzer> result) {
						int zeileCounter = 1;
						
						for (final Nutzer nutzer : result){
							
							if(nutzer.getEmail() != Cookies.getCookie("userMail")){
								Button brechts = new Button("Folgen");
								
								Label nickname = new Label (String.valueOf(nutzer.getNickname()));
								
								flexTable2.setWidget(zeileCounter, 0, nickname);
								flexTable2.setWidget(zeileCounter, 1, brechts);
							
								zeileCounter ++;
								
								brechts.addClickHandler(new ClickHandler() {
									
									@Override
									public void onClick(ClickEvent event) {
										Nutzer beobachtete = new Nutzer();
										int id = Integer.parseInt(Cookies.getCookie("userID"));
										beobachtete.setId(id);
										folgenExistiert(beobachtete, nutzer);
									 
									}				
																
									});
								}
							}
						}
				});
				 
				
				 /**
				  * neue Flextable für Anzeigen der Follower
				  */
				 
				 final FlexTable flexTable3 = new FlexTable();
				//flexTable3.setText(0, 0, "Nickname");
				flexTable3.setText(0, 3, "Hier sehen Sie Ihre Follower");
				
				myAsync.getAllNutzerabonnements(Cookies.getCookie("userID"), new AsyncCallback<ArrayList<Nutzerabonnement>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Fehler bei getAllNutzeraboonementsByBeobachteteID " + caught);
						
					}

					@Override
					public void onSuccess(ArrayList<Nutzerabonnement> result) {

						//Window.alert(result.size()+""); 
						int zeileCounter = 1;
						for (Nutzerabonnement na : result){
							
							//Label beoID = new Label(na.getNutzerNickname().getNickname());
							Label followID = new Label(na.getNutzerNickname().getNickname());
							
							
							
//							flexTable.setText(zeileCounter, 0, Cookies.getCookie("userMail"));
							flexTable3.setWidget(zeileCounter, 1, followID);
							
							zeileCounter ++;
							
						}
						
					}
				});
		
				
				/**
				 * Hier werden die einzelnen Panel zusammengeführt und das Hauptpanel zurück geben
				 */
				
				links.add(flexTable);
				rechts.add(flexTable2);
				rechts.add(flexTable3);
				hauptP.add(links);
				hauptP.add(rechts);
				
				
				return hauptP;
				
	}

			
			/**
			 * 
			 * Methode die überprüft, ob man den Follower schon folgt
			 * 
			 */
	
	private void folgenExistiert(final Nutzer derBeobachteteId, final Nutzer follower){
		myAsync.findAboByNutzerID(derBeobachteteId.getId(),follower.getId(), new AsyncCallback<Nutzerabonnement>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler folgenExistiert " + caught);
				
			}

			@Override
			public void onSuccess(Nutzerabonnement result) {
				
				if(result.getId() == 0){
					folgen(derBeobachteteId, follower);
				}
				
			}
		});
	}
	
	
	/**
	 * 
	 * Methode die es ermöglicht zu Folgen
	 * 
	 */
	
	private void folgen(Nutzer derBeobachteteId, Nutzer follower) {
		myAsync.createNutzerabonnement(derBeobachteteId, follower, new AsyncCallback<Nutzerabonnement>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler bei " + caught);

			}

			@Override
			public void onSuccess(Nutzerabonnement result) {

			}
		});
		
	}
			
//			private void loeschenAbo(Nutzer nutzer) {
//				myAsync.delete(nutzer, new AsyncCallback<Void>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						Window.alert("Fehler beim loeschen Nutzerfolgen" + caught);
//						
//					}
//
//					@Override
//					public void onSuccess(Void result) {
//						Window.alert("Fehler");
//						
//					}
//				});	
//		}
	
	/**
	 * 
	 * Methode die ein Nutzer Abonnement aufhebt
	 * 
	 */
	
	public void loeschenFollower(Nutzerabonnement nutzerAbo){
		myAsync.delete(nutzerAbo, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Fehler bei loeschenFollower in NutzeraboTabelle "+ caught);
						
					}

					@Override
					public void onSuccess(Void result) {

						Window.alert("gel�scht!");
						
					}
		});
	}
}