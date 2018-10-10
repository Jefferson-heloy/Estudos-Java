/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo.bancoMYSQL;

/**
 *
 * @author Jefferson
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConectaMySql {
	
	
	private static final String url="jdbc:mysql://localhost/caminhominimo";
	private static final String user="root";
	private static final String senha="";
	
	
	public static Connection obtemConexao() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return DriverManager.getConnection(url, user, senha);
		
	}
}

