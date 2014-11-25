import java.io.*;
import java.util.Scanner;
import java.io.InputStream;

public class Main {
	
	/*
	 * Constructor
	 * @param model: model controller will connect with
	 * @param view: view controller will connect with
	 */
	public static void main (String[] args) 
	{
		Scanner scanner = new Scanner(System.in); 
		DataImporter newData = new DataImporter();
	
		System.out.println("Please enter a file name: "); 
		String fileName = scanner.nextLine(); 
		
		try{
			
			readFile(newData, fileName); 
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		
		newData.stallFinder();
		newData.outputConstructor();
	}
	
	
	/*
	 * Constructor
	 * @param model: model controller will connect with
	 * @param view: view controller will connect with
	 */
	public static void readFile(DataImporter newData, String fileName) throws IOException{
		Scanner reader = new Scanner(new FileInputStream(fileName));			   
		
		while(reader.hasNext()){
			Registers register = new Registers(); 

			String ints = reader.next();
			if(ints.equals("I-REGISTERS")){ 
				
				while(reader.hasNext()){
					String intInfo = reader.next();
					
			    	if(intInfo.equals("FP-REGISTERS")){
			    	
			    		while(reader.hasNext()){
			    		   String fpInfo = reader.next(); 
		   
			    		   if(fpInfo.equals("MEMORY")){
			    			   while(reader.hasNext()){
			    				   String memInfo = reader.next(); 
			    				   
			    				   if(memInfo.equals("CODE")){
					    			   while(reader.hasNext()){
					    				   String instruction = reader.next();
					    				   if(instruction.equals("L.D")){
					    					   String resultValue = reader.next();
					    					   String memPull = reader.next(); 
					    					   newData.loadCreator(resultValue, memPull); 
					    				   }
					    				   else if(instruction.equals("S.D")){
					    					  String memSaver = reader.next();
					    					  String saveValue = reader.next(); 
					    					  newData.storeCreator(memSaver,saveValue);
					    				   }
					    				   else if(instruction.equals("ADD.D"))
					    				   {
					    					   String resultValue = reader.next();
					    					   String adderOne = reader.next();
					    					   String adderTwo = reader.next();
					    					   
					    					   newData.addCreator(resultValue, adderOne, adderTwo);
					    				   }
					    				   else if(instruction.equals("SUB.D"))
					    				   {
					    					   String resultValue = reader.next();
					    					   String subOne = reader.next();
					    					   String subTwo = reader.next();
					    					   
					    					   newData.subCreator(resultValue, subOne, subTwo);
					    				   }
					    				   else if(instruction.equals("MUL.D"))
					    				   {
					    					   String resultValue = reader.next(); 
					    					   String multOne = reader.next();
					    					   String multTwo = reader.next(); 
					    					   
					    					   newData.mulCreator(resultValue, multOne, multTwo); 
					    					   
					    				   }
					    			   }
					    		   }
			    				   
			    				   else{
			    				   String memData = reader.next();
			    				   newData.memoryCreator(memInfo, memData);
			    				   }
			    			   }
			    		   }
			    	   else{
			    		   String fpData = reader.next(); 
			    		   newData.floatingPointCreator(fpInfo, fpData);
			    	   }
			    	}
			    	} 
			    	else{
			    		String intData = reader.next();
				    	newData.integerCreator(intInfo, intData); 
			    	}
				}		
				
			}
		}
  }
}
