/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo.bancoMYSQL;

import grafo.Metrica;
import grafo.bancoMYSQL.Pop;
import grafo.bancoMYSQL.ConectaMySql;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Jefferson
 */
public class buscarPop {

    public buscarPop() {
    }
    
    public ArrayList<Pop> buscarTodosUsuarios(){
	 ArrayList<Pop> lista = new ArrayList<Pop>();
	 
	 try{
		 java.sql.Connection con =ConectaMySql.obtemConexao();
			
			String queryInserir ="SELECT * FROM pop";
			
			java.sql.PreparedStatement ppStm =con.prepareStatement(queryInserir);
			
			ResultSet rSet= (ResultSet) ppStm.executeQuery();
			
			while(rSet.next()){
				Pop usr = new Pop();
				
				usr.setId(rSet.getInt(1));
				usr.setPop1(rSet.getString(2));
				usr.setPop2(rSet.getString(3));
				lista.add(usr);
			}
			
			con.close();
			
			}catch(Exception e){
				e.printStackTrace();
				
			}
	 
	 return lista;
 }
    
      public ArrayList<Metrica> Metrica(){
	 ArrayList<Metrica> lista = new ArrayList<Metrica>();
	 
	 try{
		 java.sql.Connection con =ConectaMySql.obtemConexao();
			
			String queryInserir ="SELECT * FROM enlace";
			
			java.sql.PreparedStatement ppStm =con.prepareStatement(queryInserir);
			
			ResultSet rSet= (ResultSet) ppStm.executeQuery();
			
			while(rSet.next()){
				Metrica usr = new Metrica();
				
                                usr.setId(rSet.getInt(1));
				usr.setA(rSet.getInt(2));
				usr.setB(rSet.getInt(3));
				usr.setC(rSet.getInt(4));
				lista.add(usr);
			}
			
			con.close();
			
			}catch(Exception e){
				e.printStackTrace();
				
			}
	 
	 return lista;
 }

}
