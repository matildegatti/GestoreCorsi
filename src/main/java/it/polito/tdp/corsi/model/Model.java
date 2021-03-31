package it.polito.tdp.corsi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {
	
	private CorsoDAO corsoDao;
	
	public Model() {
		corsoDao=new CorsoDAO();
	}
	
	public List<Corso> getCorsiByPeriodo(Integer pd){
		return corsoDao.getCorsiByPeriodo(pd);
	}
	
	public Map<Corso,Integer> getIscrittiByPeriodo(Integer pd){
		return corsoDao.getIscrittiByPeriodo(pd);
	}
	
	public List<Studente> getStudentiByCorso(String codice){
		return corsoDao.getStudentiByCorso(new Corso(codice,null,null,null));
	}

	public boolean esisteCorso(String codice) {
		return corsoDao.esisteCorso(new Corso(codice,null,null,null));
	}
	
			//cds e numero studenti di quel cds
	public Map<String,Integer> getDivisioneCDS(String codice) {   //potrei fare anche una lista di oggetti creai apposta per avere questa divisione, tipo classe DivisioneCDS
		//dato il corso con codice ABC ci aspettiamo ad esempio il numero di gestionali (cds) iscritti a quel corso
	
		//SOLUZIONE 1
		/*
		Map<String,Integer> divisione=new HashMap<String,Integer>();
		List<Studente> studenti=this.getStudentiByCorso(codice);
		
		for(Studente s:studenti) {
			if(s.getCDS()!=null && !s.getCDS().equals("")) {  //per studenti senza cds
				if(divisione.get(s.getCDS()) == null) //il cds non è ancora presente nella mappa
					divisione.put(s.getCDS(), 1);
				else
					divisione.put(s.getCDS(), divisione.get(s.getCDS())+1);
			}
		}
		return divisione;
		*/
		
		//SOLUZIONE 2, ci facciamo dare direttamente la soluzione dal database

		return corsoDao.getDivisioneStudenti(new Corso(codice,null,null,null));
	}
}
