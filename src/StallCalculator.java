import java.util.ArrayList;
import java.util.List;

public class StallCalculator {

	List<Instructions> insts;

	public StallCalculator(){
		insts = new ArrayList(); 
	}
	public int idStarter(int i){
		int idOffset = 0; 
		if(i != 0)
		{
			int j = i - 1;
			for(int k = 0; k < insts.get(j).getTotalCycle(); k++){
			if(insts.get(j).getInstructName().get(k).equals("ID")){
				idOffset = insts.get(j).getPipelineCount().get(k);
			}
		}
		}
		return idOffset;
	}
	
	public int stallLooks(List<Instructions> instructions, int i){
		int totalStalls = 0; 
		insts = instructions; 
		//findWAR();
		totalStalls = totalStalls + findRAW(i) + findWAW(); 
		
		return totalStalls;
	}
	
	//valid? 
	public int findWAR(int i){
		int newStalls = 0; 
		
			for(int j = 0; j < i; j++){ 
				if(insts.get(j).getName().equals("MUL.D") || insts.get(j).getName().equals("SUB.D") || insts.get(j).getName().equals("ADD.D")){
					if(insts.get(i).getResultValue().equals(insts.get(j).getFirstMath()) || insts.get(i).getResultValue().equals(insts.get(j).getSecondMath())){
						 newStalls = ccTouch(j, i, "MEM", "ID");
					}
				}
				else if(insts.get(j).getName().equals("S.D") || insts.get(j).getName().equals("L.D")){
					if(insts.get(i).getResultValue().equals(insts.get(j).getMemPull())){
						ccTouch(j, i, "MEM", "ID"); 
						 newStalls = ccTouch(j, i, "MEM", "ID"); 
					}
			}
				
		}
		return newStalls;
	}
	
	public int findRAW(int i){
		int newStalls = 0; 
		 
			for(int j = 0; j < i; j++){ 
				if(insts.get(i).getName().equals("MUL.D") || insts.get(i).getName().equals("SUB.D") || insts.get(i).getName().equals("ADD.D")){
					if(insts.get(j).getResultValue().equals(insts.get(i).getFirstMath()) || insts.get(j).getResultValue().equals(insts.get(i).getSecondMath())){
						newStalls = ccTouch(j, i, "WRITE", "WRITE") + 1; 

					}

				}
				else if(insts.get(i).getName().equals("S.D") || insts.get(i).getName().equals("L.D")){
					if(insts.get(j).getResultValue().equals(insts.get(i).getMemPull())){
						newStalls = ccTouch(j, i, "WRITE", "WRITE") + 1; 

					}
			}
		}
		
		return newStalls;

	}
	
	public int findWAW(){
		int numOfStalls = 0; 
		for(int i = 0; i < insts.size(); i++){
			for(int j = 0; j < i; j++){ 
					if(insts.get(j).getResultValue().equals(insts.get(i).getResultValue())){
					 
					}

				}
			
			}
		
		return numOfStalls;

	}
	
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
		//		System.out.println("Whoohoo!" + doTouch); 
			}
		
		
		return doTouch;
		
	}
}
