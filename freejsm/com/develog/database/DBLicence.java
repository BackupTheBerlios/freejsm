/*
 * DBLicence.java
 *
 * Created on 7 juin 2004, 14:27
 */

package com.develog.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author  yann
 */
public class DBLicence {
    
    static public boolean sStillLicencesAvalables(Connection dbConnection)throws Exception {
        //On verifie qu'il reste des licences dans la table adequate
            //Si il en reste, on verifie le challenge
                //Si le challenge est verifie, on verouille tous les champs contenant ce challenge
                //de sorte qu'il n'apparaisse plus pour les autres clients
                //et que le fait de recopier une ligne ne rajoute pas une licence
        //Un echec a l'une de ces etapes entraine lengthrefut de connexion
        return false;
    }
    
}
