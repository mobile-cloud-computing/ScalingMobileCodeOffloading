package fi.cs.ubicomp.traces;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Loader {
	
	
	private LinkedList<DataTrace> traces;
	
	private String filePath;
	
	public Loader(String filePath){
		this.traces = new LinkedList<DataTrace>();
		this.filePath = filePath;
		
		parseCSVFile();
		
	}
	
	
	public void parseCSVFile(){
		String csvFile = filePath;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

			     
				String[] row = line.split(cvsSplitBy);
				
				
				traces.add(new DataTrace(row[0], row[1], row[2]));


			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Loaded: " + traces.size() + " users");
	}
	
	
	public LinkedList<DataTrace> getTraces(){
		return this.traces;
	}
	

}
