package outils;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImportCSV {

	private String selectedFilePath;

	// Extraction du csv
	public void choisirChemin() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

		int result = fileChooser.showSaveDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			selectedFilePath = selectedFile.getAbsolutePath();
		}
	}

	public String getSelectedFilePath() {
		return this.selectedFilePath;
	}
}
