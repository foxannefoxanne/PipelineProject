
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
		
		
		public String getIntName(int i){
			return intName[i]; 
		}
		
	
		public int getIntValue(int i){
			return intValue[i];
		}
		
		public void setIntValue(int value, int i){
			intValue[i] = value;
			intInit[i] = true; 
		}
		
		public boolean getIntInit(int i){
			return intInit[i];
		}
		
		
		
		public String getFPName(int i){
			return fpName[i]; 
		}
				
		public Double getFPValue(int i){
			return fpValue[i];
		}
		
		public void setFPValue(Double value, int i){
			fpValue[i] = value;
			fpInit[i] = true; 
		}
		
		public boolean getFPInit(int i){
			return fpInit[i];
		}
		
		
		
		public int getMemName(int i){
			return memName[i]; 
		}
				
		public Double getMemValue(int i){
			return memValue[i];
		}
		
		public void setMemValue(Double value, int i){
			memValue[i] = value;
			memInit[i] = true; 
		}
		
		public boolean getMemInit(int i){
			return memInit[i];
		}
}
