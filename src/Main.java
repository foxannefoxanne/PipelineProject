import java.io.*;
import java.util.Scanner;
import java.io.InputStream;

public class Main {
	
	

	/*
	 * Main file to initiate program
	 */
	
	public static void main (String[] args) 
	{
		
		Scanner scanner = new Scanner(System.in); 
		DataImporter newData = new DataImporter();
	
		System.out.println("Please enter a file name: "); 
		String fileName = scanner.nextLine(); 
		
		//calls read file method with try-catch to see if file name is incorrect
		try{
			
			readFile(newData, fileName); 

			
			newData.stallFinder();
			newData.outputConstructor();
			
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		
		
	}
	
	
	/*
	 *  Reads in and processes file. IF-ELSE loop checks for required components. I components are not properly initialized,program will exit. 
	 */
	public static void readFile(DataImporter newData, String fileName) throws IOException{

		Scanner reader = new Scanner(new FileInputStream(fileName));			   
		//boolean correctInput = true; 
		
		while(reader.hasNext()){

			Registers register = new Registers(); 
			String ints = reader.next();
			
			//start I-REGISTER input
			if(ints.equals("I-REGISTERS")){ 		
				while(reader.hasNext()){
					String intInfo = reader.next();

					//start FP-Register input
					if(intInfo.equals("FP-REGISTERS")){
			    		while(reader.hasNext()){
			    		   String fpInfo = reader.next(); 

			    		   //start memory input
			    		   if(fpInfo.equals("MEMORY")){
			    			   while(reader.hasNext()){
			    			   String memInfo = reader.next(); 

			    			   //start code input
			    				if(memInfo.equals("CODE")){
			    				   while(reader.hasNext()){
					    			   
			    					   String instruction = reader.next();
					
			    					   //process load instruction
					    			   if(instruction.equals("L.D")){
					   					   String resultValue = reader.next();
					   					   String memPull = reader.next(); 
					   					    newData.loadCreator(resultValue, memPull); 
					   				   }
					    			
					    			   //process store instructions
					    			   else if(instruction.equals("S.D")){
					    				  String memSaver = reader.next();
					    				  String saveValue = reader.next(); 
					    				  newData.storeCreator(memSaver,saveValue);
					    			   }
					    			   
					    			   //process add instructions
					    			   else if(instruction.equals("ADD.D"))
					    			   {
					    				   String resultValue = reader.next();
					    				   String adderOne = reader.next();
					   					   String adderTwo = reader.next();
					    				newData.addCreator(resultValue, adderOne, adderTwo);
					   				   }
					    			 //process subtract instructions
					    			   else if(instruction.equals("SUB.D"))
					    			   {
					    				   String resultValue = reader.next();
					    				   String subOne = reader.next();
					    				   String subTwo = reader.next();
					    				 newData.subCreator(resultValue, subOne, subTwo);
					    			   }
					    			 //process multiply instructions
					    			   else if(instruction.equals("MUL.D"))
					    			  {
					    				   String resultValue = reader.next(); 
					    				   String multOne = reader.next();
					    				   String multTwo = reader.next(); 
					    				newData.mulCreator(resultValue, multOne, multTwo); 
					    					   
					    			   }
					    			   else
					    			   {
					    				   System.out.println("You have entered an incorrect instruction name. Please review your input file."); 
					    		
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
