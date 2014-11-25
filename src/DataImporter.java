import java.util.ArrayList;
import java.util.List;

public class DataImporter {
	private Registers newReg;
	InstructionList instructionList;
	StallCalculator stallSearch; 
	OutputFile output;
	int maxCC; 
	
	public DataImporter(){
		newReg = new Registers();
		instructionList = new InstructionList(); 
		stallSearch = new StallCalculator(); 
		output = new OutputFile();
	}
	
	public void outputConstructor(){
		output.registerOutput(newReg); 
		output.timingOutput(instructionList.getInstruction(), maxCC);
	}
	
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
	public void stallFinder(){
		int instructionSize = instructionList.getInstruction().size();
		
		for(int i = 0; i < instructionSize; i++){ 
			int[] stallTracker = new int[instructionSize]; 
			if(instructionList.getInstruction().get(i).getName().equals("L.D") || instructionList.getInstruction().get(i).getName().equals("S.D"))
			{
				int idOffset = stallSearch.idStarter(i);

				instructionList.getInstruction().get(i).setPipelineListLoadStore(0, idOffset);
				int stallAdder = stallSearch.stallLooks(instructionList.getInstruction(), i); 				
				instructionList.getInstruction().get(i).setPipelineListLoadStore(stallAdder, idOffset);
			}
	
			else if(instructionList.getInstruction().get(i).getName().equals("MUL.D"))
			{
				int idOffset = stallSearch.idStarter(i);
				instructionList.getInstruction().get(i).setPipelineListMul(0, idOffset);
				int stallAdder = stallSearch.stallLooks(instructionList.getInstruction(), i); 
				instructionList.getInstruction().get(i).setPipelineListMul(stallAdder, idOffset);

			}
			else if(instructionList.getInstruction().get(i).getName().equals("ADD.D") || instructionList.getInstruction().get(i).getName().equals("SUB.D"))
			{
				int idOffset = stallSearch.idStarter(i);

				instructionList.getInstruction().get(i).setPipelineListMul(0, idOffset);
				int stallAdder = stallSearch.stallLooks(instructionList.getInstruction(), i); 
				instructionList.getInstruction().get(i).setPipelineListAddSub(stallAdder, idOffset);
			}
		}
		
		finalClockCycle(); 
	}

	public void integerCreator(String regAssignment, String regData)
	{
			
		    String whichReg = regAssignment.substring(1);
		    int regValue = Integer.parseInt(whichReg);
		    int intData = Integer.parseInt(regData); 
		    newReg.setIntValue (intData, regValue); 
		   		 
	}
	
	public void floatingPointCreator(String regAssignment, String regData){

	    String whichReg = regAssignment.substring(1);
	    int regValue = Integer.parseInt(whichReg);
	    double doubleData = Double.parseDouble(regData); 
	    newReg.setFPValue(doubleData, regValue); 
	}

	public void memoryCreator(String regAssignment, String regData){
	    int regValue = Integer.parseInt(regAssignment);
	    double doubleData = Double.parseDouble(regData); 
	    newReg.setMemValue(doubleData, regValue);
	}
	
	
	                                                                                          
	public void loadCreator(String input1, String input2){
		Instructions instructions = new Instructions(); 

		
		input1 = input1.replace(",","");
		instructions.setLoader(input1, input2);
		instructionList.newInstructions(instructions);

		int openBracketDetector = input2.indexOf("(");
		int closeBracketDetector = input2.indexOf(")");
	   

		String offset = input2.substring(0, openBracketDetector); 
		int offsetValue = Integer.parseInt(offset); 
		
		String registerPull = input2.substring(openBracketDetector + 1,closeBracketDetector);	
		String whichReg = registerPull.substring(1);
	    int regValue = Integer.parseInt(whichReg);
		
	    int memValue = newReg.getIntValue(regValue); 
	    
		memValue = memValue + offsetValue; 
		double data = newReg.getMemValue(memValue);
		
		whichReg = input1.substring(1);
	    regValue = Integer.parseInt(whichReg);

		newReg.setFPValue(data, regValue);
	}
	
	public void storeCreator(String input1, String input2){
		Instructions instructions = new Instructions(); 

		input1 = input1.replace(",","");
		instructions.setStorer(input1, input2);
		instructionList.newInstructions(instructions);
		

		int openBracketDetector = input1.indexOf("(");
		int closeBracketDetector = input1.indexOf(")");
		
		String offset = input1.substring(0, openBracketDetector); 
		int offsetValue = Integer.parseInt(offset); 
		
		String registerPull = input1.substring(openBracketDetector + 1,closeBracketDetector);		
		String whichReg = registerPull.substring(1);
	    int regValue = Integer.parseInt(whichReg);

	    int memValue = newReg.getIntValue(regValue); 
	    
		memValue = memValue + offsetValue; 
		double data = newReg.getMemValue(memValue);
		
		double storeRequest = fpSearch(input2); 
		
		newReg.setMemValue(storeRequest, regValue); 
	}
	
	public void addCreator(String result, String add1, String add2){
		Instructions instructions = new Instructions(); 

		result = result.replace(",","");
		add1= add1.replace(",","");
		instructions.setAdder(result, add1, add2);
		instructionList.newInstructions(instructions);

		double firstOperand = fpSearch(add1);
		double secondOperand = fpSearch(add2); 
		double resultValue = firstOperand + secondOperand; 
		
	    String whichReg = result.substring(1);
	    int regValue = Integer.parseInt(whichReg);

		newReg.setFPValue(resultValue, regValue); 
	} 
	
	public void subCreator(String result, String sub1, String sub2){
		Instructions instructions = new Instructions(); 
		
		result = result.replace(",","");
		sub1 = sub1.replace(",","");
		instructions.setSubtractor(result, sub1, sub2);
		instructionList.newInstructions(instructions);

		double firstOperand = fpSearch(sub1);
		double secondOperand = fpSearch(sub2); 
		double resultValue = firstOperand - secondOperand; 
		
	    String whichReg = result.substring(1);
	    int regValue = Integer.parseInt(whichReg);

		newReg.setFPValue(resultValue, regValue); 

	}
	
	public void mulCreator(String result, String mul1, String mul2){		
		Instructions instructions = new Instructions(); 
				
		result = result.replace(",","");
		mul1 = mul1.replace(",","");
		instructions.setMultiplier(result, mul1, mul2);
		instructionList.newInstructions(instructions);

		double firstOperand = fpSearch(mul1);
		double secondOperand = fpSearch(mul2); 

	    
		double resultValue = firstOperand * secondOperand; 

	     String whichReg = result.substring(1);
	     int regValue = Integer.parseInt(whichReg);

		newReg.setFPValue(resultValue, regValue); 
	
	}
	
	public double fpSearch(String registerName){
		double regValue = 0; 
		for(int i = 0; i < 32; i++)
		{
			if(newReg.getFPName(i).equals(registerName))
			{
				regValue = newReg.getFPValue(i);
				break;
			}
		}
		return regValue; 
	}
	
	public int integerSearch(String registerName){
		int regValue = 0; 
		for(int i = 0; i < 32; i++){
			if(newReg.getIntName(i).equals(registerName)){
				regValue = newReg.getIntValue(i);
				break;
			}
		}
		return regValue; 
	}
	
	
	
}
