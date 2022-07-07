package it.polito.tdp.food.model;

public class Event implements Comparable<Event>{
	
	public enum EventType {
		INIZIO_PREPARAZIONE, //viene assegnato cibo a stazione
		FINE_PREPARAZIONE,
	}
	private EventType type;
	private Double time; //in minuti
	private Stazione stazione;
	private Food food;
	public Event(EventType type, Double time, Stazione stazione, Food food) {
		super();
		this.type=type;
		this.time = time;
		this.stazione = stazione;
		this.food = food;
	}
	
	public EventType getType() {
		return type;
	}

	public Double getTime() {
		return time;
	}
	public Stazione getStazione() {
		return stazione;
	}
	public Food getFood() {
		return food;
	}
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.time.compareTo(o.time);
	}
	
	
	
	
/*	
	0[ f1 ] 1[ f2 ] 2[ f3 ]
			
			FINE_PREPARAZIONE(F2, stazione 1) T
			 INIZIO_PREPARAZIONE (f2 cibo concluso, stazione 1) T //ha finito il 2 e inizia 4
			   scelgo il prossimo cibo 
			   calcolo durata
			   schedulo evento Fine(cibo nuovo appena scelto)
			 FINE_PREP(f4, stazione 1) T+delta  delta(peso di f2--f4)
			   rischedulo evento di inizio allo stesso istante, ricordando il cibo terminato
			  
*/	
	
}
