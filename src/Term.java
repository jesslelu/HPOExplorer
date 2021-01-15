import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Term {

	//Initializing class variables 
	private ArrayList < Integer > parentIDList = new ArrayList< Integer >(); //stores list of parent Term IDs
	private ArrayList < Term > parentList = new ArrayList< Term >(); //stores list of Instances of parentTerms 
	private String termDataString;//static variable, holds initial intake of info

	// StoresTermID
	private  int termID;

	//stores termData that has been manipulated for easy splicing
	List <String> termDataList;
	
	//stores term Data by line
	List <String> data;
	
	//sets next node in linked list null
	Term next=null;
	
	
	//constructor for Term Class
	public Term(String info) {
		
		//takes in String of term info
		this.termDataString = info;
		
		//splices string of term data into a list seperated by line and by ':'
		termDataList = new ArrayList<String>(Arrays.asList(termDataString.replace(": ", ",").split(",")));
		
		//splces string of term data by line
		this.data = new ArrayList<String>(Arrays.asList(termDataString.split(",")));
		
		//finds termID in data and stores it
		setTermID();
		
	}
	
	
	//returns list of parent terms
	public ArrayList getParentList() {
		return parentList;
	}
	
	//returns the parent node (next in linked list_
	public Term getNext() {
		return next;
	}
	
	
	//returns list of term data 
	public List<String> getData() {
		return data; //returns term dats in rows
	}
	
	
	//returns own id of term
	public int getID() {
		return termID;
	}
	
	
	//adds parent term to list
	public void addParent(Term newParent) {
		//System.out.println("added");
		parentList.add(newParent);
	}
	
	//sets the parent Term of this term
	public void setNext() {
		this.next = this.parentList.get(0);
	}
	
	
	//find id of term
	public void setTermID() {
		String numIsolater;
		numIsolater = termDataList.get(1).substring(3,termDataList.get(1).length());
		this.termID = Integer.parseInt(numIsolater);
		
	}
	
	
	//finds parent ids of an individial term,
	public void findParents() {
		
		for (int i=0;i<termDataList.size();i++) {
			//System.out.println(i+": "+termDataList.get(i));
			
			
			if(termDataList.get(i).contains("is_a")&& !termDataList.get(i).contains("is_an")) {
				//numParents++;
				//System.out.println("THIS INDEX: " + i);
				
				addParentID(i+1);
			}
		}
		//System.out.println(numParents);
	}
	
	
	//find parent id and stores value
	public void addParentID(int index) {
		String numIsolater;
		
		int blankIndex = termDataList.get(index).indexOf(" ") ;
	
		numIsolater = termDataList.get(index).substring(3,blankIndex);

		//adds parent to instance list
		parentIDList.add(Integer.parseInt(numIsolater));

	}
	
	
	public ArrayList getParentIDList() {
		return parentIDList;
	}
	

	
	
	
}
