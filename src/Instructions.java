import java.util.ArrayList;
import java.util.List;

public class Instructions {
  
	
	String instName;
	String resultValue; 
	
	String math1; 
	String math2; 
	
	
	String memoryPull;
	
	List<String> pipelineName;
	List<Integer> pipelineValue; 
	
	int totalCycle; 
	
	public Instructions(){
				
		pipelineName = new ArrayList<String>();
		pipelineValue = new ArrayList<Integer>();  
		totalCycle = 0; 
	}
	
	public void setLoader(String input1, String input2)
	{
	    instName = "L.D";
	    resultValue = input1;
	    memoryPull = input2;
	    	    
	}
	
	public void setStorer(String input1, String input2)
	{
	    instName = "S.D"; 
	    resultValue = input1;
	    memoryPull = input2;
	    
	}
	
	
	public void setAdder(String result, String add1, String add2)
	{
	    instName = "ADD.D";
	    resultValue = result; 
	    math1 = add1;
	    math2 = add2;
	    
	}
	
	public void setSubtractor(String result, String sub1, String sub2)
	{
	    instName = "SUB.D"; 
	    resultValue = result; 
	    math1 = sub1;
	    math2 = sub2;
	    
	}
	
	public void setMultiplier(String result, String mul1,  String mul2)
	{
	    instName = "MUL.D"; 
	    resultValue = result; 
	    math1 = mul1;
	    math2 = mul2;
	    
	}
	
	
	public String getName()
	{
		return instName; 
	}
	public String getFirstMath()
	{
		return math1;
	}
	
	public String getSecondMath()
	{
		return math2;
	}
	

	public String getMemPull()
	{
		return memoryPull;
	}
	
	public String getResultValue()
	{
		return resultValue; 
	}
	
	public List<String> getInstructName(){
	
		return pipelineName;
	}
	
	public List<Integer> getPipelineCount(){
		
		return pipelineValue;
	}
	
	public int getTotalCycle()
	{
		return totalCycle; 
	}
	

	
	public void setPipelineListLoadStore(int stalls, int clockCycle)
	{
		
		pipelineName.clear();
		pipelineValue.clear(); 
	    pipelineName.add("IF");
	    pipelineValue.add(clockCycle); 
	    clockCycle++;
	    if(stalls != 0)
	    {
	    	for(int i = 0; i < stalls; i++)
	    	{
	    		pipelineName.add("s");
	    		   pipelineValue.add(clockCycle); 
	    		    clockCycle++;
	    	}
	    }
	    pipelineName.add("ID"); 
	    pipelineValue.add(clockCycle); 
	    clockCycle++;
	    pipelineName.add("EXE");
	    pipelineValue.add(clockCycle); 
	    clockCycle++;
	    pipelineName.add("MEM");
	    pipelineValue.add(clockCycle); 
	    clockCycle++;
	    pipelineName.add("WB"); 
	    pipelineValue.add(clockCycle); 
	    clockCycle++;
	    totalCycle = clockCycle; 
	    totalCycle = stalls + 5; 

	}
	
	
	public void setPipelineListMul(int stalls, int clockCycle)
	{
		pipelineName.clear();
		pipelineValue.clear(); 
	    pipelineName.add("IF");
	    pipelineValue.add(clockCycle); 
	    clockCycle++;
	    if(stalls != 0)
	    {
	    	for(int i = 0; i < stalls; i++)
	    	{
	    		pipelineName.add("s");
	    		   pipelineValue.add(clockCycle); 
	    		    clockCycle++;
	    	}
	    }
	    pipelineName.add("ID"); 
	    pipelineValue.add(clockCycle); 
	    clockCycle++;
	    for(int i = 1; i < 7; i++)
	    {
	    	int j = i + 1; 
	    	String mult = "M" + j; 
	    	pipelineName.add(mult);
	    	   pipelineValue.add(clockCycle); 
	   	    clockCycle++;
	    }
	    pipelineName.add("MEM");
	    pipelineValue.add(clockCycle); 
	    clockCycle++;
	    pipelineName.add("WB");
	    pipelineValue.add(clockCycle); 
	    clockCycle++;
	    totalCycle = stalls + 10; 

	}
	

	public void setPipelineListAddSub(int stalls, int clockCycle)
	{
		pipelineName.clear();
		pipelineValue.clear(); 
	    pipelineName.add("IF");
	    pipelineValue.add(clockCycle); 
	    clockCycle++;
	    if(stalls != 0)
	    {
	    	for(int i = 0; i < stalls; i++)
	    	{
	    		pipelineName.add("s");
	    		   pipelineValue.add(clockCycle); 
	    		    clockCycle++;
	    	}
	    }
	    pipelineName.add("ID"); 
	    pipelineValue.add(clockCycle); 
	    clockCycle++;
	    for(int i = 1; i < 5; i++)
	    {
	    	int j = i + 1; 
	    	String adder = "A" + j; 
	    	pipelineName.add(adder);
	    	   pipelineValue.add(clockCycle); 
	   	    clockCycle++;
	    }
	    pipelineName.add("MEM");
	    pipelineValue.add(clockCycle); 
	    clockCycle++;
	    pipelineName.add("WB"); 
	    pipelineValue.add(clockCycle); 
	    clockCycle++;
	    totalCycle = clockCycle; 
	    totalCycle = stalls + 8; 

	}


}
