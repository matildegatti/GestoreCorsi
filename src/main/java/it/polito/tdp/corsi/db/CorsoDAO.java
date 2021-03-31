package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {

	public List<Corso> getCorsiByPeriodo(Integer periodo){
		String sql="SELECT * FROM corso WHERE pd=?";
		List<Corso> result=new ArrayList<Corso>();
		
		try {
			Connection conn=DBConnect.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);  //ci permette di inserire il parametro nella nostra stringa, così da non doverlo concatenare
			st.setInt(1, periodo);  //1=primo parametro metto il periodo passato
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				Corso c=new Corso(rs.getString("codins"),rs.getInt("crediti"),rs.getString("nome"),rs.getInt("pd"));
				result.add(c);
			}
			
			st.close();
			rs.close();
			conn.close();
		
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public Map<Corso,Integer> getIscrittiByPeriodo(Integer periodo){
		String sql="SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) AS tot "
				+ "FROM corso c, iscrizione i "
				+ "WHERE c.codins = i.codins AND c.pd=? "
				+ "GROUP BY c.codins, c.nome, c.crediti, c.pd "
				+ "ORDER BY tot desc";
		
		Map<Corso, Integer> result=new HashMap<Corso, Integer>();
		
		try {
			Connection conn=DBConnect.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);  //ci permette di inserire il parametro nella nostra stringa, così da non doverlo concatenare
			st.setInt(1, periodo);  //1=primo parametro metto il periodo passato
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				Corso c=new Corso(rs.getString("codins"),rs.getInt("crediti"),rs.getString("nome"),rs.getInt("pd"));
				Integer n=rs.getInt("tot");
				result.put(c,n);
			}
			
			st.close();
			rs.close();
			conn.close();
		
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	
	public List<Studente> getStudentiByCorso(Corso corso){
		String sql="SELECT s.matricola, s.cognome, s.nome, s.CDS FROM studente s, iscrizione i WHERE s.matricola = i.matricola AND i.codins= ?";
		List<Studente> studenti=new LinkedList<Studente>();
		
		try {
			Connection conn=DBConnect.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());  
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				Studente s=new Studente(rs.getInt("matricola"),rs.getString("nome"),rs.getString("cognome"),rs.getString("CDS"));
				studenti.add(s);
			}
			
			rs.close();
			st.close();
			conn.close();
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return studenti;
	}
	
	public Map<String,Integer> getDivisioneStudenti(Corso corso){
		String sql="SELECT s.CDS, COUNT(*) AS tot FROM studente s, iscrizione i WHERE s.matricola = i.matricola AND i.codins = ? AND s.cds <> \"\" GROUP BY s.cds";
		Map<String,Integer> result=new HashMap<String,Integer>();
		
		try {
			Connection conn=DBConnect.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());  
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				result.put(rs.getString("CDS"), rs.getInt("tot"));
			}	
	
			rs.close();
			st.close();
			conn.close();
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
	
		}
		
		return result;
	}

	public boolean esisteCorso(Corso corso) {
		String sql="SELECT * FROM corso WHERE codins = ?";
		try {
			Connection conn=DBConnect.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());  
			
			ResultSet rs=st.executeQuery();
			
			if(rs.next()) {  //vuol dire che c'è un elemento e quindi il corso esiste
				rs.close();
				st.close();
				conn.close();
				return true;
			}
			else {   //chiudo tutte le volte che c'è un return
				rs.close();
				st.close();
				conn.close();
				return false;
			}	
	
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
