/*
 * Name: Jessie Lu

 * Student id: 20115012
 * Date: February 25,2019
 * 
 * Description: This program reads file HPO.txt and stores individual term data in instances of the Term class.
 * Instances are stored in nodes of a node tree, allowing a path of term information to be printed from specified queried
 * node to the root node
 * 
 * TA COMMENTS: Should include class header comment at top of each class, 
 * ready query files in HPO EXplorer and then create multiple query instances and solve them 
 * Constructor should initizlize the attibutes
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner; 
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class HPOExplorer {
	
	//initializing static variables
	static String lineRead; //tracks line being read from file
	static ArrayList < Term > termList = new ArrayList<Term>(); //list of term objects
	static String termString=""; //Stores raw string of term data
	static Term head; //stores head of linked list of Term objects
	static Query query = new Query(); //creates new instance of Query class
	static Integer maxIndex =0;
	

	//Main method
	public static void main(String[] args) throws FileNotFoundException {
		
		File file = new File("/Users/jessielu/Documents/eclipse-workspace/HPOExplorer/InputFiles/HPO.txt"); 
		Scanner sc = null ; //scanner that reads file
		fileReader(file, sc);//reads file, stores object

		//finds Term parents and adds to list
		parentAdder();

		//prints Query data in results.txt
		printQuery();
		
		//printes maxpath in maxpath.txt
		maxPathFinder();

	}

	
	//reads file, creates termObjects
		public static void fileReader(File filePath, Scanner sc) throws FileNotFoundException {
			
			sc = new Scanner(filePath);
			
			//While there are still lines in document
			while (sc.hasNextLine()) {		
				lineRead = sc.nextLine();
				
				//start adding to string when "term" is found
				if(lineRead.contains("[Term]")) {
					
					termString ="";		
					lineRead = sc.nextLine();
					//Keep adding information to termString until blank is reached
					while(lineRead.length()!=0) {

						termString +=lineRead + ",";
						lineRead = sc.nextLine();
					}
					
					if((!termString.contains("is_obsolete")==true)) {
						termList.add(new Term(termString));	
					
					}
					//add collected term info and create new Term object
				}						
			} 	
			
		}
	
	
	//This method scans through the length of the path of each node to root and stores longest value
	public static void maxPathFinder() throws FileNotFoundException {
		//int maxIndex = 0;
		int maxPathLength =0;
		int pathLength = 0;
		
		for (int i=0; i< termList.size();i++) {
			
			pathLength = 0; //resetting length to zero

			head = termList.get(i); //setting head value of linked list
			
			//while linked list has a following node, increase length of path by 1
			while(head!= null) {
				pathLength += 1;
				head = head.getNext();
				
			}
			
			//if length of path is longer than longest path so far, replace. 
			if(pathLength>=maxPathLength) {
				
				maxPathLength = pathLength;
				maxIndex = i;//updates index of term with longest path in termList array
				
			}
			
		}
	
		//prints terms info line by line
		printMaxPath(maxPathLength);
	}
	
	
	public static void printMaxPath(int maxPathLength) throws FileNotFoundException {
		File file = new File("C:/Users/jessielu/Documents/eclipse-workspace/HPOExplorer/maxpath.txt");
		maxPathLength -= 1;
		PrintWriter printWriters = new PrintWriter("maxpath.txt");
		
		//Displays max path value on console
		printWriters.println("[Max_path="+ maxPathLength + "]");
		head = termList.get(maxIndex);
		
		//stop when root node is reached
		while(head!= null) {
			
			printTermData(head,printWriters);
	
			head = head.getNext();
			
		}
		
		printWriters.close();
		
	}
	
	//This method prints query answers into file 
	public static void printQuery() throws FileNotFoundException {
		
		File file = new File("C:/Users/jessielu/Documents/eclipse-workspace/HPOExplorer/results.txt");
		
		PrintWriter printWriter = new PrintWriter("results.txt");

		
		for (int i=0; i< query.getQueryList().size();i++) {
			//System.out.println(query.getQueryList().get(i));
			head = termFinder((int) query.getQueryList().get(i));
			
			printWriter.println ("[query_answer] ");
			while(head!= null) {
				
				printTermData(head,printWriter);
				//System.out.println(head.)

				head = head.getNext();
				
			}
		}
		
		printWriter.close ();
		
	}
	
	
	public static void printTermData(Term term,PrintWriter printWriter) {
		
		//prints query path for every queried id requested in Query.txt file
		for(int i=0; i<term.getData().size();i++) {
			printWriter.println (term.getData().get(i));
		}
		
		//prints
		printWriter.println ();
		
	}
	
	
	//Goes through Terms' list of parent IDs, and adds the parentTerms to list
	public static void parentAdder() {
		
		//goes through every index of termList
		for(int i=0;i<termList.size();i++) {
			termList.get(i).findParents();
			
			for(int k =0; k<termList.get(i).getParentIDList().size();k++) {

				termList.get(i).addParent(termFinder((int) termList.get(i).getParentIDList().get(k)));
			}
			
			//System.out.println(termList.get(i).getParentList().get(0));
			if(termList.get(i).getParentList().size()>0) {
				termList.get(i).setNext();
			}
			
		}
	}
	
	
	//int parameter takes in ID and returns Term object with that id
	public static Term termFinder(int parentID) {
		//System.out.println( parentID);
		for(int i=0;i<termList.size();i++) {
			if(termList.get(i).getID()==parentID) {
				//System.out.println(termList.get(i).getData());
				return termList.get(i);
			}
		}
		return null;
	}
	
	
	
	

	
}































