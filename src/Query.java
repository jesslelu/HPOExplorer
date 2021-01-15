import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Query {
	
	static String lineRead;
	static ArrayList <Integer> queryIDList = new ArrayList <Integer>();
	
	
	//Constructor for Query class
	public Query() {
		
		//accesses file throguh file path
		File file = new File("/Users/jessielu/Documents/eclipse-workspace/HPOExplorer/InputFiles/queries.txt"); 
		Scanner sc=null;
		
		//passes file and scanner to reader
		fileReader(file,sc);
		
	}
	
	
	//returns list of query ids 'queryIDList'
	public ArrayList getQueryList() {
		return queryIDList;
	}
	
	
	//reads query.txt file and stores queryID in a a static list 'queryIDList'
	public void fileReader(File file, Scanner scanner) {
		
		try {
			scanner = new Scanner(file);
			
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			e.printStackTrace();
		}
		
		//goes through every line in the query file 
		while (scanner.hasNextLine()) {		
			
			lineRead = scanner.nextLine();
			
			//adds spliced queried id to a list
			queryIDList.add(splicer(lineRead));

		}
		
	}
	
	//This method splices the line in the query.txt file to isolate id
	public Integer splicer(String line) {
		return Integer.parseInt(line.substring(10,17)); //returns id in integer form 
	}
	
	
	
}