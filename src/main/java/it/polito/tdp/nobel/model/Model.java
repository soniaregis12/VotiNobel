package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	
	private List<Esame> esami;
	private double bestMedia = 0.0;
	private Set<Esame> bestSoluzione = null;

	public Model() {
		EsameDAO dao = new EsameDAO();
		this.esami = dao.getTuttiEsami();
	}
	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		
		Set<Esame> parziale = new HashSet<Esame>();
		cerca1(parziale, 0, numeroCrediti);
		
		return this.bestSoluzione;
	}
	
	public void cerca1(Set<Esame> parziale, int L, int m) {
		
		// Casi terminali
		
		int crediti = sommaCrediti(parziale);
		
		if(crediti > m) {
			return;
		}
		
		if(crediti == m) {
			double media = calcolaMedia(parziale);
			if(media > bestMedia) {
				this.bestMedia = media;
				this.bestSoluzione = new HashSet<Esame>(parziale);
			}
		}
		
		if(L == this.esami.size()) {
			return;
		}
		
		// Generiamo i sottoproblemi
		// Decidiamo se inserire o meno esami[L] e vado avanti con entrambi le strade
				
		// Caso 1: aggiungo
		parziale.add(esami.get(L));
		cerca1(parziale, L+1, m);
		parziale.remove(esami.get(L));
				
		// Caso 2: non aggiungo
		cerca1(parziale, L+1, m);
		
	}
	
	/* public void cerca2(Set<Esame> parziale, int L, int m) {		// Questa soluzione fa cagare, ci mette una vita
		
		// Casi terminali
		
		int crediti = sommaCrediti(parziale);
		
		if(crediti > m) {
			return;
		}
		
		if(crediti == m) {
			double media = calcolaMedia(parziale);
			if(media > bestMedia) {
				this.bestMedia = media;
				this.bestSoluzione = new HashSet<Esame>(parziale);
			}
		}
		
		if(L == this.esami.size()) {
			return;
		}
		
		for(Esame s : esami) {
			if(!parziale.contains(s)) {
				parziale.add(s);
				cerca1(parziale, L+1, m);
				parziale.remove(s);
			}	
		}
				
	}
	*/
	
	
	public double calcolaMedia(Set<Esame> parziale) {		// Media pesata
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : parziale) {
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	private int sommaCrediti(Set<Esame> parziale) {
		int somma = 0;
		
		for(Esame e: parziale) {
			somma += e.getCrediti();
		}
		return somma;
	}

}
