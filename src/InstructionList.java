import java.util.ArrayList;
import java.util.List;

public class InstructionList {
	
	List<Instructions>  instructionList;
	
	public InstructionList()
	{
		instructionList = new ArrayList(); 
	}
	
	public void newInstructions(Instructions instruction)
	{
		instructionList.add(instruction); 
	}
	
	public List<Instructions> getInstruction()
	{
		return instructionList; 
	}
}
