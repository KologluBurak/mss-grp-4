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
 * Mapper-Klasse, die <code>NutzerAbo</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * geloescht werden koennen. Das Mapping ist bidirektional. D.h., Objekte
 * koennen in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * 
 * @author Thies
 * @author Kologlu
 * @author Oikonomou
 * @author Yücel
 */

public class NutzerAboMapper {

	/**
	 * Die Klasse NutzerAboMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 * @see NutzerAboMapper()
	 */
	private static NutzerAboMapper nutzerAboMapper = null;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected NutzerAboMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>NutzerAboMapper.nutzerAboMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine
	 * einzige Instanz von <code>NutzerAboMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> NutzerAboMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>NutzerAboMapper</code>-Objekt.
	 * @see nutzerAboMapper
	 */
	public static NutzerAboMapper nutzerAboMapper() {
		if (nutzerAboMapper == null) {
			nutzerAboMapper = new NutzerAboMapper();
		}
		return nutzerAboMapper;

	}

	/**
	 * Einfuegen eines <code>Nutzerabonnement</code>-Objekts in die Datenbank.
	 * Dabei wird auch der Primaerschluessel des uebergebenen Objekts
	 * geprueft und ggf. berichtigt.
	 * 
	 * @param nutzerabonnement
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Nutzerabonnement insert(Nutzerabonnement nutzerabonnement) 
			throws IllegalArgumentException {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
			Date date = new Date();
			String sql = "INSERT INTO `nutzerabonnements`(`nutzerAboID`, `datum`, `abonnementID`, `derBeobachteteID`, `followerID`) "
					+ "VALUES (NULL, ?, ?, ?, ?)";

			PreparedStatement preStmt;
			preStmt = con.prepareStatement(sql);
			preStmt.setString(1, dateFormat.format(date));
			preStmt.setInt(2, nutzerabonnement.getAbonnementID());
			preStmt.setInt(3, nutzerabonnement.getDerBeobachteteID());
			preStmt.setInt(4, nutzerabonnement.getFollowerID());
			preStmt.executeUpdate();
			preStmt.close();

			// con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!" + e.toString());
		}
		return nutzerabonnement;
	}

	/**
	 * Diese Methode ermoeglicht das Loeschen eines Nutzerabonnements
	 * 
	 * @param nutzerabonnement
	 */
	public void delete(Nutzerabonnement nutzerabonnement) 
			throws IllegalArgumentException {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM nutzerabonnements WHERE nutzerAboID=" + nutzerabonnement.getId());
			stmt.close();
			// con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!" + e.toString());
		}
	}
	
	/**
	 * Diese Methode ermoeglicht das Loeschen eines Abonnements und dessen
	 * Referenzen zu anderen Klassen
	 * 
	 * @param abonnement
	 */
	public void deleteAbonnementID(Abonnement abonnement) 
			throws IllegalArgumentException {
		
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM nutzerabonnements WHERE abonnementID=" + abonnement.getId());
			stmt.close();
			
			// con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!" + e.toString());
		}
	}
	
	/**
	 * Diese Methode ermoeglicht das Loeschen eines Beobachters und dessen
	 * Referenzen zu anderen Klassen
	 * 
	 * @param abonnement
	 */
	public void deleteDerBeobachteteID(Nutzer derBeobachtete) throws IllegalArgumentException {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM nutzerabonnements WHERE derBeobachteteID=" + derBeobachtete.getId());
			stmt.close();
			// con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!" + e.toString());
		}
	}

	/**
	 * Diese Methode ermoeglicht das Loeschen eines Follower und dessen
	 * Referenzen zu anderen Klassen
	 * 
	 * @param abonnement
	 */
	public void deleteFollowerID(Nutzer follower) throws IllegalArgumentException {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM nutzerabonnements WHERE followerID=" + follower.getId());
			stmt.close();
			// con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!" + e.toString());
		}
	}
	
	/**
	 * Diese Methode ermoeglicht es alle Nutzerabonnements aus der Datenbank in
	 * einer Liste auszugeben.
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Nutzerabonnement> findAllNutzerabonnements() 
			throws IllegalArgumentException {
		Connection con = DBConnection.connection();
		ArrayList<Nutzerabonnement> allNutzerabonnements = new ArrayList<Nutzerabonnement>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM nutzerabonnements ORDER BY nutzerAboID");

			while (rs.next()) {
				Nutzerabonnement nutzerabonnement = new Nutzerabonnement();
				nutzerabonnement.setId(rs.getInt("nutzerAboID"));
				nutzerabonnement.setAbonnementID(rs.getInt("abonnementID"));
				nutzerabonnement.setDerBeobachteteID(rs.getInt("derBeobachteteID"));
				nutzerabonnement.setFollowerID(rs.getInt("followerID"));
//				nutzerabonnement.setErstellungsZeitpunkt(rs.getString("datum"));

				allNutzerabonnements.add(nutzerabonnement);
			}
			stmt.close();
			rs.close();
			// con.close();

			return allNutzerabonnements;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!" + e.toString());
		}

	}

	/**
	 * Diese Methode ermoeglicht es ein Nutzerabonnement anhand ihrer ID zu
	 * finden und anzuzeigen.
	 * 
	 * @param id
	 * @return nutzerabo
	 * @throws IllegalArgumentException
	 */
	public Nutzerabonnement findNutzerAbonnementByID(int id) 
			throws IllegalArgumentException {
		
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM nutzerabonnements "
							+ "WHERE nutzerAboID= " + id);

			if (rs.next()) {
				Nutzerabonnement nutzerabonnement = new Nutzerabonnement();
				nutzerabonnement.setId(rs.getInt("nutzerAboID"));
				nutzerabonnement.setAbonnementID(rs.getInt("abonnementID"));
				nutzerabonnement.setDerBeobachteteID(rs.getInt("derBeobachteteID"));
				nutzerabonnement.setFollowerID(rs.getInt("followerID"));
//				nutzerabonnement.setErstellungsZeitpunkt(rs.getString("datum"));

				return nutzerabonnement;
			}
			stmt.close();
			rs.close();
			// con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!" + e.toString());
		}
		return null;
	}

	/**
	 * Diese Methode ermöglicht es eine Ausgabe ueber einen Nutzerabonnements
	 * in der Datenbank, anhand deren ID.
	 * 
	 * @param id
	 * @return
	 */

	public ArrayList<Nutzerabonnement> findNutzerAbonnementByAbonnementID(int id) 
			throws IllegalArgumentException {
		
		Connection con = DBConnection.connection();
		ArrayList<Nutzerabonnement> nutzerAboListe = new ArrayList<Nutzerabonnement>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM nutzerabonnements, abonnements "
					+ "WHERE abonnementID=" + id);

			while (rs.next()) {
				Nutzerabonnement nutzerabonnement = new Nutzerabonnement();
				nutzerabonnement.setId(rs.getInt("nutzerAboID"));
				nutzerabonnement.setAbonnementID(rs.getInt("abonnementID"));
				nutzerabonnement.setDerBeobachteteID(rs.getInt("derBeobachteteID"));
				nutzerabonnement.setFollowerID(rs.getInt("followerID"));
//				nutzerabonnement.setErstellungsZeitpunkt(rs.getString("datum"));

				nutzerAboListe.add(nutzerabonnement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!" + e.toString());
		}
		return nutzerAboListe;
	}

	/**
	 * Diese Methode ermoeglicht es eine Ausgabe ueber einen Nutzerabonnements
	 * in der Datenbank, anhand deren ID.
	 * 
	 * @param id
	 * @return
	 */

	public ArrayList<Nutzerabonnement> findNutzerAbonnementByDerBeobachteteID(int id) 
			throws IllegalArgumentException {
		Connection con = DBConnection.connection();
		ArrayList<Nutzerabonnement> nutzerAboListe = new ArrayList<Nutzerabonnement>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM nutzerabonnements, abonnements "
					+ "WHERE derBeobachteteID=" + id);

			while (rs.next()) {
				Nutzerabonnement nutzerabonnement = new Nutzerabonnement();
				nutzerabonnement.setId(rs.getInt("nutzerAboID"));
				nutzerabonnement.setAbonnementID(rs.getInt("abonnementID"));
				nutzerabonnement.setDerBeobachteteID(rs.getInt("derBeobachteteID"));
				nutzerabonnement.setFollowerID(rs.getInt("followerID"));
//				nutzerabonnement.setErstellungsZeitpunkt(rs.getString("datum"));

				nutzerAboListe.add(nutzerabonnement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!"
					+ e.toString());
		}
		return nutzerAboListe;
	}
	
	/**
	 * Diese Methode ermoeglicht es eine Ausgabe ueber einen Nutzerabonnement
	 * in der Datenbank, anhand deren FollowerID.
	 * @param id
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Nutzerabonnement> findNutzerAbonnementByFollowerID(int id) 
			throws IllegalArgumentException {
		Connection con = DBConnection.connection();
		ArrayList<Nutzerabonnement> nutzerAboListe = new ArrayList<Nutzerabonnement>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM nutzerabonnements "
					+ "WHERE followerID=" + id);

			while (rs.next()) {
				Nutzerabonnement nutzerabonnement = new Nutzerabonnement();
				nutzerabonnement.setId(rs.getInt("nutzerAboID"));
				nutzerabonnement.setAbonnementID(rs.getInt("abonnementID"));
				nutzerabonnement.setDerBeobachteteID(rs.getInt("derBeobachteteID"));
				nutzerabonnement.setFollowerID(rs.getInt("followerID"));
//				nutzerabonnement.setErstellungsZeitpunkt(rs.getString("datum"));

				nutzerAboListe.add(nutzerabonnement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!"+ e.toString());
		}
		return nutzerAboListe;
	}
}