package it.polito.tdp.dizionario.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {

	/*
	 * Ritorna tutte le parole di una data lunghezza che differiscono per un solo carattere
	 */
	public List<String> getAllSimilarWords(String parola, int numeroLettere) {
		
		List<String> parole = new ArrayList<String>();
		
		for(int i = 0; i<numeroLettere; i++){
			String nuova = parola.substring(0,i)+'_'+parola.substring(i+1);
			//System.out.println(nuova);
		
			
			Connection conn = DBConnect.getInstance().getConnection();
			String sql = "SELECT nome FROM parola WHERE nome LIKE ?";
			PreparedStatement st;
			
			try {

				st = conn.prepareStatement(sql);
				st.setString(1, nuova);
				ResultSet res = st.executeQuery();

				while (res.next()){
					String ris = res.getString("nome");
					if(!ris.equals(parola))
						parole.add(ris);
				
				}
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Errore connessione al database");
				throw new RuntimeException("Error Connection Database");
			}
		}
		
		return parole;
	}

	/*
	 * Ritorna tutte le parole di una data lunghezza
	 */
	public List<String> getAllWordsFixedLength(int numeroLettere) {

		Connection conn = DBConnect.getInstance().getConnection();
		String sql = "SELECT nome FROM parola WHERE LENGTH(nome) = ?;";
		PreparedStatement st;

		try {

			st = conn.prepareStatement(sql);
			st.setInt(1, numeroLettere);
			ResultSet res = st.executeQuery();

			List<String> parole = new ArrayList<String>();

			while (res.next())
				parole.add(res.getString("nome"));

			conn.close();

			return parole;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

}
