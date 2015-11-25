package de.hdm.itProjektGruppe4.shared;

import java.sql.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itProjektGruppe4.shared.bo.*;

/**
 * 
 * @author Yücel
 *
 */

public interface MessagingAdministration extends RemoteService {
		
//Methodenkörper
public void deleteAbonnement (Abonnement Abo) throws IllegalArgumentException;
public void setNachrichtEditedBy (Nachricht NachrichtEditedBy)throws IllegalArgumentException;
public void saveNachricht (Nachricht Nachricht) throws IllegalArgumentException;
public void deleteHashtag (Hashtag Hashtag)throws IllegalArgumentException;
public void createNachricht (Nachricht Nachricht) throws IllegalArgumentException;
public void createHashtag (Hashtag Hashtag) throws IllegalArgumentException;
public void saveAbonnement (Abonnement Abo) throws IllegalArgumentException;
public void createNutzer (Nutzer Nutzer) throws IllegalArgumentException;
public void senden (Nachricht Senden) throws IllegalArgumentException;
public void saveUnterhaltung (Unterhaltung Unterhaltung) throws IllegalArgumentException;
public void deleteNutzer (Nutzer Nutzer) throws IllegalArgumentException;
public void createUnterhaltung (Unterhaltung Unterhaltung) throws IllegalArgumentException;
public void setDateOfNachricht(Date DateOfNachricht) throws IllegalArgumentException;
public void saveHashtag (Hashtag Hashtag) throws IllegalArgumentException;
public void empfangen (Nachricht Empfangen) throws IllegalArgumentException;
public void createAbonnement (Abonnement Abo) throws IllegalArgumentException;
public void deleteNachricht (Nachricht Nachricht) throws IllegalArgumentException;
public void createAboNutzer (Nutzerabonnement NutzerAbo)throws IllegalArgumentException;
public void deleteAboNutzer (Nutzerabonnement NutzerAbo)throws IllegalArgumentException;
public void saveAboNutzer (Nutzerabonnement NutzerAbo)throws IllegalArgumentException;
public void createAboHashtag (Hashtagabonnement HashtagAbo)throws IllegalArgumentException;
public void deleteAboHastag (Hashtagabonnement HashtagAbo)throws IllegalArgumentException;
public void saveAboHashtag (Hashtagabonnement HashtagAbo)throws IllegalArgumentException;

}