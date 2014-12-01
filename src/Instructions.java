import java.util.ArrayList;
import java.util.List;

public class Instructions {

	//instruction data
	private String instName;
	private String resultValue; 
	
	private String math1; 
	private String math2; 
	
	
	private String memoryPull;
	
	private List<String> pipelineName;
	private List<Integer> pipelineValue; 
	
	int totalCycle; 
	
	/*
     *   Constructor. Initiate lists.   
	 */  
	public Instructions(){
		pipelineName = new ArrayList<String>();
		pipelineValue = new ArrayList<Integer>();  
		totalCycle = 0; 
	}
	
	/*
     *   Creates new loader 
	 */  
	public void setLoader(String input1, String input2){
	    instName = "L.D";
	    resultValue = input1;
	    memoryPull = input2;
	}
	
	/*
     *   Creates new storer
     */  
	public void setStorer(String input1, String input2)
	{
	    instName = "S.D"; 
	    resultValue = input1;
	    memoryPull = input2;
	    
	}
	
	/*
     *   Creates new adder
     */  
	public void setAdder(String result, String add1, String add2)
	{
	    instName = "ADD.D";
	    resultValue = result; 
	    math1 = add1;
	    math2 = add2;
	    
	}
	
	/*
     *   Create new subtractor
     */  
	public void setSubtractor(String result, String sub1, String sub2)
	{
	    instName = "SUB.D"; 
	    resultValue = result; 
	    math1 = sub1;
	    math2 = sub2;
	    
	}
	/*
     *   Create new multiplier
     */  
	public void setMultiplier(String result, String mul1,  String mul2)
	{
	    instName = "MUL.D"; 
	    resultValue = result; 
	    math1 = mul1;
	    math2 = mul2;
	    
	}
	
	
	/*
     *   Return name of instruction. 
     */  
	public String getName(){
		return instName; 
	}
	
	/*
     *   Return name of first operand instruction. 
     */  
	public String getFirstMath(){
		return math1;
	}
	
	/*
     *   Return name of second operand instruction. 
     */  
	public String getSecondMath(){
		return math2;
	}
	

	/*
     *   Return name of memory pull (for store and load)
     */  
	public String getMemPull(){
		return memoryPull;
	}
	
	/*
     *   Return name of result. 
     */  
	public String getResultValue(){
		return resultValue; 
	}
	
	/*
     *   Return instruction list. 
     */  
	public List<String> getInstructName(){
		return pipelineName;
	}
	
	/*
     *   Return name of instruction count. 
     */  
	public List<Integer> getPipelineCount(){
		return pipelineValue;
	}
	
	/*
     *   Return total clock cycles. 
     */  
	public int getTotalCycle(){
		return totalCycle; 
	}
	

	/*
     *   Sets up pipeline for five cycle store/load instruction 
     */  
	public void setPipelineListLoadStore(int stalls, int clockCycle)
	{
		
		pipelineName.clear();
		pipelineValue.clear(); 
	    pipelineName.add("IF");
	    pipelineValue.add(clockCycle); 
	    clockCycle++;
	    //checks for stalls
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
	    
	    totalCycle = stalls + 5; 

	}
	
	
	public void setPipelineListMul(int stalls, int clockCycle)
	{
		pipelineName.clear();
		pipelineValue.clear(); 
	    pipelineName.add("IF");
	    pipelineValue.add(clockCycle); 
	    clockCycle++;
	    //checks for stalls
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
	    //iterates throug seven M files
	    for(int i = 0; i < 7; i++)
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
	    totalCycle = stalls + 11; 

	}
	

	public void setPipelineListAddSub(int stalls, int clockCycle)
	{
		pipelineName.clear();
		pipelineValue.clear(); 
	    pipelineName.add("IF");
	    pipelineValue.add(clockCycle); 
	    clockCycle++;
	    //checks for stalls
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
	    //iterates through 4 add executions. 
	    for(int i = 0; i < 4; i++)
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
