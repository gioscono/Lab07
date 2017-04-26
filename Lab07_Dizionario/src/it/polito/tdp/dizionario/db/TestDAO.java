package it.polito.tdp.dizionario.db;

public class TestDAO {
	
	public static void main(String[] args) {
		
		WordDAO wd = new WordDAO();
		
		System.out.println(wd.getAllWordsFixedLength(4));
		System.out.println(wd.getAllSimilarWords("casa", 4));
	}

}
