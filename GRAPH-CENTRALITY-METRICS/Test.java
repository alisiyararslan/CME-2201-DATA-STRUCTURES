import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Test {

	public static void graphProcess(String txtName) {
		Graph graph = new Graph();
		try {// file reading operations
			File myObj = new File(txtName);// file path
			Scanner myReader = new Scanner(myObj, "UTF-8");// first reader for add stack
			while (myReader.hasNextLine()) {
				String nextLine = myReader.nextLine();
				String[] splitted = nextLine.split(" ");
				graph.addEdge(splitted[0], splitted[1]);

			}
			myReader.close();

		} catch (FileNotFoundException e) {// error catch
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		String[] splitted = txtName.split("[._]+");
		graph.calculateBetweenness_Closeness();
	
		Vertex v = graph.highestBetweenness();
		for (int i = 0; i < splitted.length - 1; i++) {
			System.out.print(splitted[i] + " ");
		}
		System.out.println(" The Highest Node for Betweennes is " + v.getName() + " value   " + v.getBetweenness());

		v = graph.highestCloseness();
		for (int i = 0; i < splitted.length - 1; i++) {
			System.out.print(splitted[i] + " ");
		}
		System.out.println(" The Highest Node for Closeness is " + v.getName() + " value   " + v.getCloseness());

	}

	public static void main(String[] args) {

		System.out.println("2019510017 Ali Þiyar ARSLAN");


		//graphProcess("facebook_social_network.txt");//Betweennes is 223 value 74087 Closeness is 1045 value Infinity
		
		 graphProcess("karate_club_network.txt");//Betweennes is 1 value 293 Closeness is 1 value 0.017241379310344827
		 


	}

}
