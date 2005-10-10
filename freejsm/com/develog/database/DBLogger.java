/*
 * DBLogger.java
 *
 * Created on 13 janvier 2004, 19:27
 */

package com.develog.database;

import java.lang.Character;
import java.lang.Thread;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
/**
 *
 * @author  alexandre
 */
public class DBLogger {
   
    private DBLoop dbloop   = null;
    
    /** Creates a new instance of DBLogger */
    public DBLogger(String from,Connection connection) {
            dbloop = new DBLoop(from,connection);
    }
    
    public void log(String log) {
        dbloop.notifyMessage(new Message(log,"L"));
    }
    
    public void warn(String warn) {
        dbloop.notifyMessage(new Message(warn,"W"));
    }
    
    public void error(String err) {
        dbloop.notifyMessage(new Message(err,"E"));
    }
}

class DBLoop extends Thread
{
	protected static final long insertDelay = 5000;  //5 secondes
	private Connection db   = null;
	private Vector queue    = new Vector();
        private String from     = null;
	private String query    =
		new String("insert into LOG (MESSAGE,TYPE,APPLI) values "
					+"(?,?,?)");
	public DBLoop(String f,Connection conn)
	{
            db = conn;
            from = new String(f);
            start();
	}	
	
	public int notifyMessage(Message message)
	{
		queue.insertElementAt(message,0);
		return queue.size();		
	}
	
	public void run()
	{
            try {
                PreparedStatement pstmt = db.prepareStatement(query);
                synchronized (this) {
                    while (true) {
                        while (queue.size() > 0) {
                            ResultSet rset = null;

                            //On vide la queue
                            Message m = (Message) queue.elementAt(0);
                            pstmt.setString(1,m.content);
                            pstmt.setString(2,m.type);
                            pstmt.setString(3,from);
                            try {
                                rset = pstmt.executeQuery();
                            } catch (SQLException SQLe)
                            { System.out.println("[INSERTION] SQLException :"+SQLe);}

                            //On supprime le message de la queue
                            queue.removeElementAt(0);
                        }
                        //Tous les fiches ont ete inserees, on passe en attente
                        try
                        { 
                                wait(insertDelay); 
                        }catch (InterruptedException e) { }
                    }
                }
            }
            catch(Exception e)
            {							
                    System.out.println("RUN ERROR !!!!!!!!!!:"+e);
            }
    }
}

class Message {
            
    public String content   = null;
    public String type      = null;
                    
    public Message() {
    }
            
    public Message(String cont,String typ) {
        content = new String(cont);
        type = typ;
    }
}
