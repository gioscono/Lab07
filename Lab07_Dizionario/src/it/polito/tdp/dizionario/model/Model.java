package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

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
}
