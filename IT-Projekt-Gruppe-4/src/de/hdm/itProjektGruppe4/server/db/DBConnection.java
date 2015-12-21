package de.hdm.itProjektGruppe4.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import com.google.appengine.api.utils.SystemProperty;


public class DBConnection {

	//Variablen für den Verbindungsaufbau
	private static Connection con = null;
	private static String googleUrl = null;
	// nur Billy
    private static String localUrl = "jdbc:mysql://www.db4free.net:3306/messaging_admin"; // "jdbc:mysql://localhost:3306/messaging_administration?user=root";

    public static Connection connection() {
    	System.out.println("Connection aufgerufen!");
        // Wenn es bisher keine Conncetion zur DB gab, ...
        if (con == null) {
            String url = "";
            try {
//                if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
//                    Class.forName("com.mysql.jdbc.GoogleDriver");
//                    url = googleUrl;
//                } else {
            	try{
                    Class.forName("com.mysql.jdbc.Driver");
                    url = localUrl;
                    System.out.println("URL von DB: "+url);
                }catch(Exception e){
                    System.err.println("Treiber konnte nicht erstellt werden.");
                	con = null;
                    e.printStackTrace();
                }
            	//nur Billy
                con = DriverManager.getConnection(url, "vo002", "050392");
                
            } catch (Exception e) {
                System.err.println("Datenbank konnte nicht geladen werden");
            	con = null;
                e.printStackTrace();
            }
        }
        return con;
    }
}
