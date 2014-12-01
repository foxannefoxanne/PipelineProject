import java.util.ArrayList;
import java.util.List;

public class StallCalculator {

	List<Instructions> insts;

	/*
     *   Constructor. Initiate instruction list.   
	 */  
	public StallCalculator(){
		insts = new ArrayList<Instructions>(); 
	}
	
	
	/*
     *   Check offset of ID.   
	 */  
	public int idStarter(List<Instructions> instructions, int i){
		insts = instructions; 
		int idOffset = 0; 
		if(i != 0)
		{
			//look for ID of previous instruction. Set offset to that level. 
			int j = i - 1;
			for(int k = 0; k < insts.get(j).getTotalCycle(); k++){
				if(insts.get(j).getInstructName().get(k).equals("ID")){
					idOffset = insts.get(j).getPipelineCount().get(k);
				}
			}
		}
		return idOffset;
	}
	

	/*
     *   Check pipeline for read-after-write (data dependency) issues
	 */ 
	public int findRAW(List<Instructions> instructions, int i){
		insts = instructions;
		int newStalls = 0; 
		 	//search for multiply, subtraction, add errors
			for(int j = 0; j < i; j++){ 
				if(insts.get(i).getName().equals("MUL.D") || insts.get(i).getName().equals("SUB.D") || insts.get(i).getName().equals("ADD.D")){
					if(insts.get(j).getResultValue().equals(insts.get(i).getFirstMath()) || insts.get(j).getResultValue().equals(insts.get(i).getSecondMath())){
						 newStalls = ccTouch(j, i, "MEM", "ID");

					}

				}
				//search for store or load issues
				else if(insts.get(i).getName().equals("S.D") || insts.get(i).getName().equals("L.D")){
					if(insts.get(j).getResultValue().equals(insts.get(i).getMemPull())){
						 newStalls = ccTouch(j, i, "MEM", "ID");

					}
			}
		}
		
		return newStalls;

	}
	
	/*
     *   Check pipeline for read-after-write (data dependency) issues
	 */ 
	public int findWAW(List<Instructions> instructions, int i){
		insts = instructions;
		int newStalls = 0; 
			for(int j = 0; j < i; j++){ 
				if(insts.get(j).getResultValue().equals(insts.get(i).getResultValue())){
					newStalls = ccTouch(j, i, "WB", "WB") + 1; 
				}
			}
			

		
		return newStalls;

	}
	/*
     *   Check to see if corresponding values bump into each other on pipeline (ie if the former instruction goes further than the latter
	 */ 
	public int ccTouch(int instNumOne, int instNumTwo, String pl1, String pl2){
		int doTouch = 0; 
		int pl1Location = 0;
		int pl2Location = 0; 	
			for(int i = 0; i < insts.get(instNumOne).getTotalCycle(); i++)
			{ 
				if(insts.get(instNumOne).getInstructName().get(i).equals(pl1))
				{
					 pl1Location = insts.get(instNumOne).getPipelineCount().get(i); 
				} 	
			}
			for(int i = 0; i < insts.get(instNumTwo).getTotalCycle(); i++)
			{ 
				if(insts.get(instNumTwo).getInstructName().get(i).equals(pl2))
				{
					 pl2Location = insts.get(instNumTwo).getPipelineCount().get(i); 

				} 	
			}
			if(pl1Location > (pl2Location))
			{
				doTouch = pl1Location - pl2Location; 
			}
		
		
		return doTouch;
		
	}
}
