import java.util.ArrayList;
import java.util.List;

public class InstructionList {
	
	List<Instructions>  instructionList;
	
	/*
	 * Construction, creates array list. 
	 */
	public InstructionList()
	{
		instructionList = new ArrayList(); 
	}
	
	/*
	 * Adds New Instruction
	 */
	public void newInstructions(Instructions instruction)
	{
		instructionList.add(instruction); 
	}
	
	/*
	 * Returns entire instruction list
	 */
	public List<Instructions> getInstruction()
	{
		return instructionList; 
	}
}
