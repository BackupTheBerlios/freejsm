/*
 * PrintManager.java
 *
 * Created on 16 mai 2004, 19:49
 */

package classes;
import com.develog.utils.report.engine.*;
import com.develog.utils.report.engine.data.*;
import com.develog.utils.report.view.*;
import java.util.*;
import java.io.*;
import java.sql.*;

/**
 *
 * @author  alexandre
 * Cette classe vide sert juste d'interface en jasper et l'application.
 */
public class PrintManager {
    
    private String fileName = "";
    
    /** Creates a new instance of PrintManager */
    public PrintManager(String nomDuRapport , HashMap param , Connection connectExt ) throws Exception {
        
        nomDuRapport = "/rapports/" + nomDuRapport + ".jasper";
        JasperPrint print = null;
        //String fileName = this.getClass().getResource(nomDuRapport).getPath();
        InputStream fileName = this.getClass().getResourceAsStream(nomDuRapport);
        print = JasperFillManager.fillReport(fileName,param,connectExt);
        JasperViewer jasperViewer = new JasperViewer(print,false);
        jasperViewer.setTitle("Aperçu avant impression");
        jasperViewer.show();
    }
    
        /** Creates a new instance of PrintManager */
    public PrintManager(String nomDuRapport , HashMap param , ResultSet RSInt ) throws Exception {
        
        nomDuRapport = "/rapports/" + nomDuRapport + ".jasper";
        JRResultSetDataSource JRRInt = new JRResultSetDataSource(RSInt);
        JasperPrint print = null;
        //String fileName = this.getClass().getResource(nomDuRapport).getPath();
        InputStream fileName = this.getClass().getResourceAsStream(nomDuRapport);
        print = JasperFillManager.fillReport(fileName,param,JRRInt);
        JasperViewer jasperViewer = new JasperViewer(print,false);
        jasperViewer.setTitle("Aperçu avant impression");
        jasperViewer.show();
    }
}
