
public class Registers {
		private int[] intValue = new int[32]; 
		private String[] intName = new String[32]; 
		private boolean[] intInit = new boolean[32];
		
		private double[] fpValue = new double[32]; 
		private String[] fpName = new String[32]; 
		private boolean[] fpInit = new boolean[32];
		
		private double[] memValue = new double[992];
		private int[] memName = new int[992];
		private boolean[] memInit = new boolean[992]; 
		

		/*
	     *   Constructor, create 32 spaces for integer and floating point registers, 992 for memory
	     */ 
		public Registers(){
			for(int i = 0; i < 32; i++){
				fpName[i] = "F" + i; 
				intName[i] = "R" + i;	
				
				fpInit[i] = false; 
				intInit[i] = false; 
			}
			
			for(int i = 0; i < 992; i++){
				memName[i] = i; 
				memInit[i] = false; 
			}
		}
		
		/*
	     *   Return name of integers. 
	     */ 
		public String getIntName(int i){
			return intName[i]; 
		}
		
	
		/*
	     *   Return integer value
	     */ 
		public int getIntValue(int i){
			return intValue[i];
		}
		
		
		/*
	     *   Set integer value
	     */ 
		public void setIntValue(int value, int i){
			intValue[i] = value;
			intInit[i] = true; 
		}
		
		/*
	     *   Return boolean of whether integer has been filled manually
	     */ 
		public boolean getIntInit(int i){
			return intInit[i];
		}
		
		
		/*
	     *   Return name of floating point 
	     */ 
		public String getFPName(int i){
			return fpName[i]; 
		}
			
		/*
	     *   Return value of floating point
	     */ 
		public Double getFPValue(int i){
			return fpValue[i];
		}
		

		/*
	     *   Set new floating point value 
	     */ 
		public void setFPValue(Double value, int i){
			fpValue[i] = value;
			fpInit[i] = true; 
		}
		
		/*
	     *   Return boolean of whether floating point has been filled manually
	     */ 
		public boolean getFPInit(int i){
			return fpInit[i];
		}
		
		
		/*
	     *   Set memory value
	     */ 
		public void setMemValue(Double value, int i){
			memValue[i] = value;
			memInit[i] = true; 
		}
		
		
		/*
	     *   Return boolean of whether memory has been filled manually. 
	     */ 
		public boolean getMemInit(int i){
			return memInit[i];
		}
		/*
	     *   Return name of memory. 
	     */ 
		public int getMemName(int i){
			return memName[i]; 
		}
		/*
	     *   Return value of memory. 
	     */ 		
		public Double getMemValue(int i){
			return memValue[i];
		}
	
}
