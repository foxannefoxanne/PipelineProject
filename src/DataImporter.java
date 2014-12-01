import java.util.ArrayList;
import java.util.List;

public class DataImporter {
	private Registers newReg;
	InstructionList instructionList;
	StallCalculator stallSearch; 
	OutputFile output;
	int maxCC; 
	
	
	/*
	 *  Constructor. Main hub for other clkasses
	 */
	public DataImporter(){
		newReg = new Registers();
		instructionList = new InstructionList(); 
		stallSearch = new StallCalculator(); 
		output = new OutputFile();
	}
	
	
	/*
	 *  Calls output creator. 
	 */
	public void outputConstructor(){
			output.timingOutput(instructionList.getInstruction(), maxCC);
			output.registerOutput(newReg); 
	}
	
	/*
	 *  Counts to final clock cycle
	 */
	public void finalClockCycle(){
	
		maxCC = 0; 
		
		for(int i = 0; i < instructionList.getInstruction().size(); i++)
		{
			for(int j = 0; j < instructionList.getInstruction().get(i).getPipelineCount().size(); j++){
				if(instructionList.getInstruction().get(i).getPipelineCount().get(j) > maxCC)
				{
					maxCC = instructionList.getInstruction().get(i).getPipelineCount().get(j); 
				}
			}
		}
	}
	/*
	 *  Finds all stalls. 
	 */
	public void stallFinder(){
		int instructionSize = instructionList.getInstruction().size();
		
		//goes through all instructions, dividing them up by specific type
		for(int i = 0; i < instructionSize; i++){ 
			int[] stallTracker = new int[instructionSize]; 

			if(instructionList.getInstruction().get(i).getName().equals("L.D") || instructionList.getInstruction().get(i).getName().equals("S.D"))
			{
				//checks for offset
				int idOffset = stallSearch.idStarter(instructionList.getInstruction(),i);
				instructionList.getInstruction().get(i).setPipelineListLoadStore(0, idOffset);
				//checks for RAW, then rewrites
				int findRAW = stallSearch.findRAW(instructionList.getInstruction(), i); 				
				instructionList.getInstruction().get(i).setPipelineListLoadStore(findRAW, idOffset);
				//checks for WAW, then writes a final time
				int findWAW = stallSearch.findWAW(instructionList.getInstruction(), i); 				
				instructionList.getInstruction().get(i).setPipelineListLoadStore(findRAW + findWAW, idOffset);
			}
	
			else if(instructionList.getInstruction().get(i).getName().equals("MUL.D"))
			{	
				//checks for offset
				int idOffset = stallSearch.idStarter(instructionList.getInstruction(),i);
				instructionList.getInstruction().get(i).setPipelineListMul(0, idOffset);
				//checks for RAW, then rewrites
				int findRAW = stallSearch.findRAW(instructionList.getInstruction(), i); 				
				instructionList.getInstruction().get(i).setPipelineListMul(findRAW, idOffset);
				//checks for WAW, then writes a final time
				int findWAW = stallSearch.findWAW(instructionList.getInstruction(), i); 		
				instructionList.getInstruction().get(i).setPipelineListMul(findRAW + findWAW, idOffset);

			}
			else if(instructionList.getInstruction().get(i).getName().equals("ADD.D") || instructionList.getInstruction().get(i).getName().equals("SUB.D"))
			{
				//checks for offset
				int idOffset = stallSearch.idStarter(instructionList.getInstruction(),i);
				instructionList.getInstruction().get(i).setPipelineListMul(0, idOffset);
				//checks for RAW, then rewrites
				int findRAW = stallSearch.findRAW(instructionList.getInstruction(), i); 				
				instructionList.getInstruction().get(i).setPipelineListAddSub(findRAW, idOffset);
				//checks for WAW, then writes a final time
				int findWAW = stallSearch.findWAW(instructionList.getInstruction(), i);
				instructionList.getInstruction().get(i).setPipelineListAddSub(findRAW + findWAW, idOffset);
			}
		}
		
		finalClockCycle(); 
	}

	/*
	 *  Processes integers. Checks they are in the correct format and within register range. If not, returns message that the file cannot be properly processed. 
	 */
	public boolean integerCreator(String regAssignment, String regData){

		//check that all of the registers names begin with F
		if(regAssignment.substring(0,1).equals("R")){
			String whichReg = regAssignment.substring(1);
		   
			//Try-Catch ensures there is no issue parsing integers
			try{
		        int regValue = Integer.parseInt(whichReg);
			    int intData = Integer.parseInt(regData); 
			  
				//checks to ensure register is in range. 
			    if(regValue < 32){
			    	newReg.setIntValue (intData, regValue); 
			    	return true; 
			    }
			    else{
			    	System.out.println("You have entered an incorrect integer value. Please review your input file.");
			    	return false; 
			    }
		    }
		    catch(NumberFormatException e){
		    	System.out.println("You have entered an incorrect integer value. Please review your input file.");
		    	return false; 
		    }
		}
		else{
			System.out.println("You have entered an incorrect integer value. Please review your input file."); 
			return false; 
		}
	}
	
	/*
	 *  Processes floating point values. Checks they are in correct format and within register range. If not, returns message that the file cannot be properly processed. 
	 */
	public boolean floatingPointCreator(String regAssignment, String regData){

		//check that all of the registers names begin with F
		if(regAssignment.substring(0,1).equals("F")){
			String whichReg = regAssignment.substring(1);
			
			//Try-Catch ensures there is no issue parsing integers. 
			try{
				int regValue = Integer.parseInt(whichReg);
				double doubleData = Double.parseDouble(regData);
	
				//checks to ensure register is in range. 
				if(regValue < 32){
			    	newReg.setFPValue(doubleData, regValue);
					return true;
			    }
			    else{
			    	System.out.println("You have entered an incorrect integer value. Please review your input file.");
			    	return false; 
			    }  
			}
			catch(NumberFormatException e){
				System.out.print("You have entered an incorrect floating point value. Please review your input file."); 
				return false;
			}
		}
		else{
			System.out.println("You have entered an incorrect floating point value. Please review your input file."); 
			return false; 
		}
	}

	
	
	/*
	 *  Processes memory values. Checks they are in correct format and within memory range. If not, returns message that the file cannot be properly processed. 
	 */
	public boolean memoryCreator(String regAssignment, String regData){
	   
		//ensures input is a correct value. 
		try{

			int regValue = Integer.parseInt(regAssignment);

			double doubleData = Double.parseDouble(regData); 

			//ensures that value is within range of memory
			if(regValue < 993){
				newReg.setMemValue(doubleData, regValue);
				return true; 
			}
			else{
				System.out.println("You have entered an incorrect memory value. Please review your input file");
				return false; 
			}
		}
		catch(NumberFormatException e){
			System.out.println("You have entered an incorrect memory value. Please review your input file.");
			return false; 
		}
	}
	
	
	/*
     *   Processes loads instructions. 
	 */                                                                                          
	public boolean loadCreator(String input1, String input2){
	
		Instructions instructions = new Instructions(); 

		//removes any formatting between instructions
		input1 = input1.replace(",","");
		instructions.setLoader(input1, input2);
		instructionList.newInstructions(instructions);

		//separates value within second instruction from regular input. 
		int openBracketDetector = input2.indexOf("(");
		int closeBracketDetector = input2.indexOf(")");
		String offset = input2.substring(0, openBracketDetector); 
	
		//find where to place value
		String registerPull = input2.substring(openBracketDetector + 1,closeBracketDetector);	
		String whichReg = registerPull.substring(1);
		
		//ensures values to be parsed are numbers
		try{
			int offsetValue = Integer.parseInt(offset); 
			int regValue = Integer.parseInt(whichReg);
		
			int memValue = newReg.getIntValue(regValue); 
			memValue = memValue + offsetValue; 
		
			//ensure mem value is in range
			if(memValue > -1 && memValue < 32){
				double data = newReg.getMemValue(memValue);
		
				//reassigns value in floating point register. 
				whichReg = input1.substring(1);
				regValue = Integer.parseInt(whichReg);
				newReg.setFPValue(data, regValue);
				return true;
			}
			else{
				System.out.println("You have entered an incorrect load instruction. Please review your input file.");
				return false;
			}
		}
		catch(NumberFormatException e){
			System.out.println("You have entered an incorrect load instruction. Please review your input file.");
			return false; 
		}
	}
	
	
	/*
     *   Processes store instructions. 
	 */          
	public boolean storeCreator(String input1, String input2){
		
		Instructions instructions = new Instructions(); 

		//removes any formatting between instructions
		input1 = input1.replace(",","");
		instructions.setStorer(input1, input2);
		instructionList.newInstructions(instructions);
		
		//separates value within second instruction from regular input. 
		int openBracketDetector = input1.indexOf("(");
		int closeBracketDetector = input1.indexOf(")");
		String offset = input1.substring(0, openBracketDetector); 
		
		//find where to place value
		String registerPull = input1.substring(openBracketDetector + 1,closeBracketDetector);		
		String whichReg = registerPull.substring(1);
		
		try{
			int offsetValue = Integer.parseInt(offset); 
	    	int regValue = Integer.parseInt(whichReg);

	    	int memValue = newReg.getIntValue(regValue); 
	    
			memValue = memValue + offsetValue;
			if(memValue > -1 && memValue < 32){
				double data = newReg.getMemValue(memValue);
		
				//stores to memory
				double storeRequest = fpSearch(input2); 
				newReg.setMemValue(storeRequest, regValue); 
				return true;
			}
			
			else{
				System.out.println("You have entered an incorrect store instruction. Please review your input file.");
				return false; 
			}
		}
		catch(NumberFormatException e){
			System.out.println("You have entered an incorrect store instruction. Please review your input file.");
			return false; 
		}
	}
	
	/*
     *   Processes add instructions. 
	 */  
	public boolean addCreator(String result, String add1, String add2){
		Instructions instructions = new Instructions(); 
	
		if(result.substring(0,1).equals("F") && add1.substring(0,1).equals("F") && add2.substring(0,1).equals("F")){
			//removes any formatting between instructions
			result = result.replace(",","");
			add1= add1.replace(",","");
		
			//create add instruction
			instructions.setAdder(result, add1, add2);
			instructionList.newInstructions(instructions);

			//search for two operands
			double firstOperand = fpSearch(add1);
			double secondOperand = fpSearch(add2); 
			
			if(firstOperand != -1 && secondOperand != -1){
				double resultValue = firstOperand + secondOperand; 
				try{
					String whichReg = result.substring(1);
				
					int regValue = Integer.parseInt(whichReg);
				
					if(regValue < 32){
						newReg.setFPValue(resultValue, regValue); 
						return true;
					}
					else{
						System.out.println("You have entered an incorrect add instruction. Please review your input file.");
						return false;
					}
				}
				catch(NumberFormatException e){
					System.out.println("You have entered an incorrect add instruction. Please review your input file.");
					return false; 
				}
			}
			else{
				System.out.println("You have entered an incorrect add instruction. Please review your input file.");
				return false; 
			}
		}
		else{
			System.out.println("You have entered an incorrect add instruction. Please review your input file.");
			return false; 
		}
	} 
	
	/*
     *   Processes subtract instructions. 
	 */
	public boolean subCreator(String result, String sub1, String sub2){
		Instructions instructions = new Instructions(); 
		if(result.substring(0,1).equals("F") && sub1.substring(0,1).equals("F") && sub2.substring(0,1).equals("F")){
			//removes any formatting between instructions
			result = result.replace(",","");
			sub1 = sub1.replace(",","");
			
			//create subtract instruction
			instructions.setSubtractor(result, sub1, sub2);
			instructionList.newInstructions(instructions);

			double firstOperand = fpSearch(sub1);
			double secondOperand = fpSearch(sub2); 
		
			if(firstOperand != -1 && secondOperand != -1){
				double resultValue = firstOperand - secondOperand; 
				String whichReg = result.substring(1);
				try{
					int regValue = Integer.parseInt(whichReg);
					if(regValue < 32){
						newReg.setFPValue(resultValue, regValue); 
						return true;
					}
					else{
						System.out.println("You have entered an incorrect subtract instruction. Please review your input file.");
						return false;
					}
				}
				catch(NumberFormatException e){
					System.out.println("You have entered an incorrect subtraction instruction. Please review your input file.");
					return false; 
				}
			}
			else{
				System.out.println("You have entered an incorrect subtraction instruction. Please review your input file.");
				return false; 
			}
		}
		else{
			System.out.println("You have entered an incorrect subtraction instruction. Please review your input file.");
			return false; 
		}
	}
	
	/*
     *   Processes multiply instructions. 
	 */
	public boolean mulCreator(String result, String mul1, String mul2){		
		
		Instructions instructions = new Instructions();
		
		if(result.substring(0,1).equals("F") && mul1.substring(0,1).equals("F") && mul2.substring(0,1).equals("F")){
			result = result.replace(",","");
			mul1 = mul1.replace(",","");
			instructions.setMultiplier(result, mul1, mul2);
			instructionList.newInstructions(instructions);

			double firstOperand = fpSearch(mul1);
			double secondOperand = fpSearch(mul2); 

			if(firstOperand != -1 && secondOperand != -1){
				double resultValue = firstOperand * secondOperand; 
				String whichReg = result.substring(1);
				try{
					int regValue = Integer.parseInt(whichReg);
					if(regValue < 32){
						newReg.setFPValue(resultValue, regValue); 
						return true;
					}
					else{
						System.out.println("1. You have entered an incorrect add instruction. Please review your input file.");
						return false;
					}
				}
				catch(NumberFormatException e){
					System.out.println("2. You have entered an incorrect multiply instruction. Please review your input file.");
					return false; 
				}
			}
			else{
				System.out.println("3. You have entered an incorrect multiply instruction. Please review your input file.");
				return false; 
			}
		}
		else{
			System.out.println("4. SYou have entered an incorrect multiply instruction. Please review your input file.");
			return false; 
		}
	}
	
	
	/*
     *   Processes searches for particular FP value
     *   
     */
	public double fpSearch(String registerName){
		double regValue = 0; 
		boolean foundReg = false; 
		for(int i = 0; i < 32; i++){
			if(newReg.getFPName(i).equals(registerName)){
				regValue = newReg.getFPValue(i);
				foundReg = true;
				break;
			}
		}
		if(foundReg)
			return regValue; 
		
		else return -1; 
	}
}
