package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;
public class Model {
	
	private List<Food> cibi ;
	private Graph<Food, DefaultWeightedEdge> grafo ; 
	private FoodDao dao;
	
	public Model() {
		this.dao = new FoodDao() ;
	}
	
	public List<Food> getFoods(int portions) {
		
		this.cibi = dao.getFoodsByPortions(portions) ;

		// Crea un grafo nuovo e vuoto
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class) ;

		// Aggiungi i vertici
		Graphs.addAllVertices(this.grafo, this.cibi) ;
		
		//aggiungi gli archi
		
		for(Food f1:this.cibi) {
			for(Food f2:this.cibi) {
				if(!f1.equals(f2) && f1.getFood_code()<f2.getFood_code()) {
					//seconda cosa è perche e non orientato quindi mi basta solo 1
					Double peso=dao.calorieCongiunte(f1, f2);
					if(peso!=null) {
						Graphs.addEdge(this.grafo, f1, f2, peso);
					}
					
				}
			}
		}
		System.out.println(this.grafo);
		return cibi;
	}
	
	public List<FoodCalories>elencoCibiConnessi(Food f) {
		List<Food> vicini = Graphs.neighborListOf(this.grafo, f);
		List<FoodCalories> result = new ArrayList<>();
			for (Food v : vicini) {
				Double calorie=this.grafo.getEdgeWeight(this.grafo.getEdge(f, v));
				result.add(new FoodCalories(v, calorie));
			}
			Collections.sort(result);
			return result;
	}
	
	public String simula(Food cibo, int K) {
		Simulatore sim= new Simulatore(this.grafo, this);
		sim.setK(K);
		sim.init(cibo);
		sim.run();
		String msg=String.format("Preparati %d cibi in %f minuti\n", sim.getCibiPreparati(), sim.getTempoPreparazione());
		
		return msg;
			
			
			
		
	}
}
