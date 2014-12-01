import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.io.InputStream;


public class OutputFile {
	Scanner scanner = new Scanner(System.in); 
	
	/*
	 *  Print out register list
	 */
	public void registerOutput(Registers regs){
		
	
		try{
			//asks for register name.
			System.out.println("Please choose a name for your register file:");
			String fileName = scanner.nextLine(); 
		
			//add on .txt
			fileName = fileName.concat(".txt"); 
			File registerFile = new File(fileName);
		
			//creates new file if not one already there
			if (!registerFile.exists()){
				registerFile.createNewFile();
			}
			
			FileWriter fw = new FileWriter(registerFile.getAbsoluteFile()); 
			BufferedWriter bw = new BufferedWriter(fw);
			
			String fpTitle = "";
			String fpData = ""; 
			
			//helps align title
			int biggestTitle = findLargest(regs, 32); 
			
			for(int i = 0; i < 32; i++){
				if(regs.getFPInit(i)){
		
					//aligns title
					int titleAlignment = biggestTitle + 3 - regs.getFPName(i).length(); 
					String lengthFinder = Double.toString(regs.getFPValue(i));
					int length = lengthFinder.length();
					int  dataAlignment = biggestTitle + 3 - length; 
					String  titleSpace = String.format("%" + titleAlignment + "s", " "); 
					String  dataSpace = String.format("%" + dataAlignment + "s", " "); 

				
					fpTitle = fpTitle.concat(regs.getFPName(i) + titleSpace); 
					fpData = fpData.concat(regs.getFPValue(i) + dataSpace);   			
				}
			}
		
			bw.write(fpTitle);
			bw.newLine(); 
			bw.write(fpData);
			bw.close();
			
			System.out.println("Register file complete"); 
		
		} 
		
		catch(IOException e){
		System.out.println(e.getMessage());
		}

	}
	
	/*
	 *  Print out timing chart
	 */
	public void timingOutput(List<Instructions> instructions, int finalCC)
	{
		try{
			
			//allow user to name file
			System.out.println("Please choose a name for your timing diagram file:"); 
			String fileName = scanner.nextLine(); 
		
			fileName = fileName.concat(".txt"); 
		
			File timingFile = new File(fileName);
		
			if (!timingFile.exists()) {
				timingFile.createNewFile();
			}
			
			FileWriter fw = new FileWriter(timingFile.getAbsoluteFile()); 
			BufferedWriter bw = new BufferedWriter(fw);

			//print out instructions name
			String instCount = "     ";
			for(int i = 0; i < instructions.size(); i++){
				int j = i + 1; 
				instCount = instCount.concat(" I#" + j + "   "); 
			}
			
			bw.write(instCount);
			bw.newLine(); 
			
			
			//count clockCycles
			for(int i = 0; i <= finalCC; i++){			
				boolean titleInRow = false; 

				String ccTracker = "";
				int l = i + 1;
				//add on 0 if instruction is less than 9 (for alignment reasons)
				if(i < 9)
					ccTracker = ccTracker.concat("C#0" + l + "  ");
				else if(i >= 9)
					ccTracker = ccTracker.concat("C#" + l + "  ");
				
				//move through all instructions
				for(int j = 0; j < instructions.size(); j++){
					titleInRow = false; 
					
					//look for clock cycle in each instruction
					for(int k = 0; k < instructions.get(j).getPipelineCount().size(); k++){
						if(instructions.get(j).getPipelineCount().get(k) == i){
							
							//align values
							int ccAlignment = 6 - instructions.get(j).getInstructName().get(k).length();
							String ccSpace = String.format("%" + ccAlignment + "s", " "); 
							String newInstruct = instructions.get(j).getInstructName().get(k);

							//concatenate new data to string
							ccTracker = ccTracker +  newInstruct + " " + ccSpace;  
							titleInRow = true; 
							}
						}
					
						//add spaces if cc is empty
						if(!titleInRow){
							String ccSpace = String.format("%" + 7 + "s", " "); 

							ccTracker = ccTracker + ccSpace; 
							titleInRow = false; 
						}
					}
				
					bw.write(ccTracker); 
					bw.newLine(); 
				}
			
			bw.close();
			
			System.out.println("Timing file complete"); 
		} 
	
	catch(IOException e){
		System.out.println(e.getMessage());
		}
	}
	
	/*
	 *  Find largest register value
	 */
	public int findLargest(Registers regs, int inputSize){
		  int biggest = 3;
		  int length = 0;
		  for(int i = 0; i < inputSize; i++){
			  
			  String lengthFinder = Double.toString(regs.getFPValue(i));

			 length = lengthFinder.length();
			  if (biggest < length)
				  biggest = length; 
		  }
		return biggest; 
	  } 

	

}
