package de.hdm.itProjektGruppe4.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;

import de.hdm.itProjektGruppe4.shared.bo.*;

/**
 * Mapper-Klasse, die <code>Nachricht</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * 
 * @author Thies
 * @author Kologlu
 * @author Oikonomou
 * @author Yücel
 */
public class NachrichtMapper {

	/**
	 * Die Klasse NachrichtMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 * @see NachrichtMapper()
	 */
	private static NachrichtMapper nachrichtMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected NachrichtMapper() {

	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>NachrichtMapper.nachrichtMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine
	 * einzige Instanz von <code>NachrichtMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> NachrichtMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>NachrichtMapper</code>-Objekt.
	 * @see nachrichtMapper
	 */
	public static NachrichtMapper nachrichtMapper() {
		if (nachrichtMapper == null) {
			nachrichtMapper = new NachrichtMapper();
		}
		return nachrichtMapper;

	}

	/**
	 * Diese Methode ermöglicht es eine Nachricht in der Datenbank anzulegen.
	 * 
	 * @param nachricht
	 * @return Nachricht
	 * @throws Exception
	 */
	public Nachricht insert(Nachricht nachricht)
			throws Exception{
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		PreparedStatement preStmt = null;
		try {
			System.out.println(nachricht.getText() + " " + nachricht.getNutzerID() + " "+ nachricht.getUnterhaltungID());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			nachricht.setErstellungsZeitpunkt(dateFormat.format(date).toString());
			
			String sql= "INSERT INTO `nachrichten` (`nachrichtID`, `text`, `datum`, `unterhaltungID`, `nutzerID`) VALUES (NULL, ?, ?, ?, ?);";

			preStmt = con.prepareStatement(sql);
			preStmt.setString(1, nachricht.getText());
			preStmt.setString(2, dateFormat.format(date));
			preStmt.setInt(3, nachricht.getUnterhaltungID());
			preStmt.setInt(4, nachricht.getNutzerID());
			preStmt.executeUpdate();
			//preStmt.close();
	
			// con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Catch NachrichtMapper " +e.getMessage());
			throw new IllegalArgumentException("Datenbank fehler!"+ e.toString());
		}finally {
			DBConnection.closeAll(null, preStmt, con );
		}
		return nachricht;
	}

	/**
	 * Diese Methode ermöglicht das Löschen einer Nachricht
	 * 
	 * @param nachricht
	 * @throws Exception
	 */
	public void delete(Nachricht nachricht) throws Exception{
		Connection con = DBConnection.connection();
		Statement stmt= null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM nachrichten "
					+ "WHERE nachrichten.nachrichtID=" + nachricht.getId());

		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!"+ e.toString());
		}finally {
			DBConnection.closeAll(null, stmt, con );
		}
	}

	/**
	 * Diese Methode ermöglicht es alle Nachrichten eines Nutzers in einer
	 * Liste auszugeben.
	 * 
	 * @return allNachrichten
	 * @throws Exception
	 */
	public ArrayList<Nachricht> findAllNachrichten()
			throws Exception{
		Connection con = DBConnection.connection();
		Statement stmt= null;
		ResultSet rs= null;
		ArrayList<Nachricht> allNachrichten = new ArrayList<Nachricht>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM nutzer INNER JOIN nachrichten "
					+ "ON nutzer.nutzerID = nachrichten.nutzerID");

			while (rs.next()) {
				Nutzer absender = new Nutzer();
				absender.setNickname(rs.getString("nickname"));
				absender.setEmail(rs.getString("email"));
				
				Nachricht nachricht = new Nachricht();
				nachricht.setId(rs.getInt("nachrichtID"));
				nachricht.setAbsender(absender);
				nachricht.setText(rs.getString("text"));
				nachricht.setErstellungsZeitpunkt(rs.getString("nachrichten.datum"));

				allNachrichten.add(nachricht);
			}
			stmt.close();
			rs.close();
			//con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!"+ e.toString());
		}finally {
			DBConnection.closeAll(rs, stmt, con );
		}
		return allNachrichten;
	}

	/**
	 * Diese Methode ermöglicht es einen Nutzer anhand seiner ID das Auszugeben.
	 * 
	 * @param id
	 * @return nachricht
	 * @throws Exception
	 */
	public Nachricht findNachrichtById(int id)
			throws Exception{
		Connection con = DBConnection.connection();
		Statement stmt= null;
		ResultSet rs= null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM nachrichten "+ "WHERE nachrichtID=" + id);

			if (rs.next()) {
				Nachricht nachricht = new Nachricht();
				nachricht.setId(rs.getInt("nachrichtID"));
				nachricht.setText(rs.getString("text"));
				nachricht.setErstellungsZeitpunkt(rs.getString("datum"));
				
				return nachricht;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!"+ e.toString());
		}finally {
			DBConnection.closeAll(rs, stmt, con );
		}
		return null;
	}

	/**
	 * Diese Methode ermöglicht es alle Nachrichten eines Nutzers auszugeben.
	 * 
	 * @param nutzer
	 * @return nachrichtenJeNutzer
	 * @throws Exception
	 */
	public ArrayList<Nachricht> alleNachrichtenJeNutzer(Nutzer nutzer)
			throws Exception{
		Connection con = DBConnection.connection();
		Statement stmt= null;
		ResultSet rs= null;
		ArrayList<Nachricht> nachrichtenJeNutzer = new ArrayList<Nachricht>();

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM nutzer INNER JOIN nachrichten "
					+ "ON nutzer.nutzerID = nachrichten.nutzerID WHERE nutzer.nutzerID=" +nutzer.getId());

			while (rs.next()) {
				
				Nutzer absender = new Nutzer();
				absender.setNickname(rs.getString("nickname"));
				
				Nachricht nachricht = new Nachricht();
				nachricht.setId(rs.getInt("nachrichtID"));
				nachricht.setAbsender(absender);
				nachricht.setText(rs.getString("text"));
				nachricht.setErstellungsZeitpunkt(rs.getString("nachrichten.datum"));

				nachrichtenJeNutzer.add(nachricht);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!"+ e.toString());
		}finally {
			DBConnection.closeAll(rs, stmt, con );
		}
		return nachrichtenJeNutzer;
	}

	/**
	 * Diese Methode ermöglicht es alle Nachrichten eines bestimmten Zeitraums zu übergeben auszugeben.
	 * 
	 * @param von
	 * @param bis
	 * @return nachrichtenJeZeitraum
	 * @throws Exception
	 */
	public ArrayList<Nachricht> alleNachrichtenJeZeitraum(String von, String bis)
			throws Exception{
		Connection con = DBConnection.connection();
		Statement stmt= null;
		ResultSet rs= null;
		ArrayList<Nachricht> nachrichtenJeZeitraum = new ArrayList<Nachricht>();

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM `nachrichten` WHERE `datum` BETWEEN '"+von +"' AND '"+bis+"'");

			while (rs.next()) {
				Nachricht nachricht = new Nachricht();
				nachricht.setText(rs.getString("text"));
				nachricht.setErstellungsZeitpunkt(rs.getString("datum"));

				nachrichtenJeZeitraum.add(nachricht);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!"+ e.toString());
		}finally {
			DBConnection.closeAll(rs, stmt, con );
		}
		return nachrichtenJeZeitraum;
	}
	
	/**
	 * Auslesen der letzten ID in der Nachricht.
	 * 
	 * @return n
	 * @throws Exception
	 */
	public Nachricht getMaxID() throws Exception{
		Connection con = DBConnection.connection();
		Statement stmt = null; 
		Nachricht n = new Nachricht();
		System.out.println("Beginn getMaxID");
		try{
			String sql = "SELECT MAX(`nachrichtID`) AS nachrichtID FROM `nachrichten`";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if(rs.next()){
				
				n.setId(rs.getInt("nachrichtID"));
				System.out.println("getMax ID in If in nachrichtMapper: "+rs); 
				//return u;
			}
			System.out.println("getMax ID nach IF in nachrichtMapper: "+rs); 
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("getMaxID " + e.toString());

			throw new IllegalArgumentException("Datenbank fehler!"
					+ e.toString());
			
		}finally{
			DBConnection.closeAll(null, stmt, con );
		}
		
		return n;
	}
	
	
}
