package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.dizionario.db.WordDAO;

public class Model {

	UndirectedGraph<String, DefaultEdge> grafo = null;

	List<String> parole = null;
	public List<String> createGraph(int numeroLettere) {

		
		WordDAO wdao = new WordDAO();
		parole =  wdao.getAllWordsFixedLength(numeroLettere);

		grafo = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
		for (String s : parole) {
			grafo.addVertex(s);
		}
//		for (String s : parole) {
//			List<String> vicini =  wdao.getAllSimilarWords(s, numeroLettere);
//			for (String vicino : vicini) {
//				if (!vicino.equals(s))
//					grafo.addEdge(s, vicino);
//			}
//		}
		for(String vertice : parole){
			List<String> listaVicini = prendiParoleSimili(vertice);
			
			for(String vicino : listaVicini){
				grafo.addEdge(vertice, vicino);
			}
		}
		//System.out.println(grafo);

		return parole;
	}

	public List<String> prendiParoleSimili(String vertice) {
		List<String> vicini = new ArrayList<String>();
		for(String diz : parole){
			int diff = 0;
			for(int i =0; i<vertice.length(); i++){
				if(vertice.charAt(i)!=diz.charAt(i))
					diff++;
			}
			if(diff==1)
				vicini.add(diz);
		}
		return vicini;
	}

	public List<String> displayNeighbours(String parolaInserita) {

		List<String> vicini = Graphs.neighborListOf(grafo, parolaInserita);

		return vicini;
	}

	public String findMaxDegree() {
		String vertexMax = "";
		int max = 0;
		for (String vertex : grafo.vertexSet()) {
			if (grafo.degreeOf(vertex) > max) {
				vertexMax = vertex;
				max = grafo.degreeOf(vertex);
			}
		}
		String ris = vertexMax + "\n";
		ris += "Grado: "+max+"\n";
		for (DefaultEdge e : grafo.edgeSet()) {
			if (grafo.getEdgeSource(e).equals(vertexMax) || grafo.getEdgeTarget(e).equals(vertexMax))
				ris += Graphs.getOppositeVertex(grafo, e, vertexMax) + "-";
		}
		return ris;

	}



	public List<String> trovaTuttiVicini(String parola) {
		
		List<String> vicini = new ArrayList<String>();
				
		BreadthFirstIterator<String, DefaultEdge> bfi = new  BreadthFirstIterator<String, DefaultEdge>(grafo, parola);
		
		while(bfi.hasNext()){
			vicini.add(bfi.next());
		}
			
		return vicini;
	}

	public List<String> trovaTuttiViciniManualmente(String parola) {
		
		Set<String> daVisitare = new HashSet<String>();
		Set<String> visitati = new HashSet<String>();
		
		daVisitare.add(parola);
		
		
		
		while(!daVisitare.isEmpty()){
			String temp = daVisitare.iterator().next();
			daVisitare.remove(temp);
			daVisitare.addAll(Graphs.neighborListOf(grafo,temp));
			daVisitare.removeAll(visitati);
			visitati.add(temp);
		}
		
		
		return new ArrayList<String>(visitati);
	}

}
