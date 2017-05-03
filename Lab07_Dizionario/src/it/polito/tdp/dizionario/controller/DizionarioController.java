package it.polito.tdp.dizionario.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionario.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioController {

	Model model;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private TextArea txtResult;
	@FXML
	private TextField inputNumeroLettere;
	@FXML
	private TextField inputParola;
	@FXML
	private Button btnGeneraGrafo;
	@FXML
	private Button btnTrovaVicini;
	@FXML
	private Button btnTrovaGradoMax;
	
	@FXML
	private Button btnTrovaTuttiVicini;

	@FXML
	void doReset(ActionEvent event) {
		txtResult.clear();
		inputNumeroLettere.clear();
		inputParola.clear();
	}

	@FXML
	void doGeneraGrafo(ActionEvent event) {

		try {
			int numero = Integer.parseInt(inputNumeroLettere.getText());
			ArrayList<String> parole = (ArrayList<String>) model.createGraph(numero);

			txtResult.appendText("PAROLE TROVATE CON LUNGHEZZA " + numero + ": " + parole.size() + "\n");
			txtResult.appendText("Caricato dizionario.\n");

		} catch (RuntimeException re) {
			txtResult.setText("ERRORE, inserire un numero.");
		}
	}

	@FXML
	void doTrovaGradoMax(ActionEvent event) {

		try {
			String ris = model.findMaxDegree();
			txtResult.appendText("Vertice con maggior numero di collegamenti: " + ris);

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void doTrovaVicini(ActionEvent event) {

		try {
			String parola = inputParola.getText();
			if (parola.compareTo("") == 0 || parola.matches("[0-9]*")
					|| parola.length() != Integer.parseInt(inputNumeroLettere.getText())) {
				txtResult.appendText("Parola non valida.\n");
				return;
			} else {
				ArrayList<String> vicini = (ArrayList<String>) model.displayNeighbours(parola);
				if (vicini == null) {
					txtResult.appendText("Parola non presente nel dizionario.");
				} else {
					txtResult.appendText("La parola " + parola + " e' collegata a:\n");
					for (String s : vicini) {
						txtResult.appendText(s + "\n");
					}
				}
			}

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}
	@FXML
    void doTrovaTuttiVicini(ActionEvent event) {

		try {
			String parola = inputParola.getText();
			int lunghezza = parola.length();
			model.createGraph(lunghezza);
			List<String> vicini = model.trovaTuttiVicini(parola);
			txtResult.appendText("NODI RAGGIUNGIBILI:\n");
			for(String s: vicini){
				txtResult.appendText(s+"\n");
			}
			
			List<String> viciniManuali = model.trovaTuttiViciniManualmente(parola);
			txtResult.appendText("NODI RAGGIUNGIBILI MANUALI :\n");
			for(String s: viciniManuali){
				txtResult.appendText(s+"\n");
			}
			
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}
	
	

	@FXML
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert inputNumeroLettere != null : "fx:id=\"inputNumeroLettere\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert inputParola != null : "fx:id=\"inputParola\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnGeneraGrafo != null : "fx:id=\"btnGeneraGrafo\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaGradoMax != null : "fx:id=\"btnTrovaTutti\" was not injected: check your FXML file 'Dizionario.fxml'.";
	}

	public void setModel(Model model) {
		this.model = model;

	}
}