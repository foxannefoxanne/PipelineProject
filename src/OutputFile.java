import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.io.InputStream;


public class OutputFile {
	Scanner scanner = new Scanner(System.in); 


	public void registerOutput(Registers regs){
	
	
		try{
			System.out.println("Please choose a name for your register file:");
			String fileName = scanner.nextLine(); 
		
			fileName = fileName.concat(".txt"); 
			
			File registerFile = new File(fileName);
		
			if (!registerFile.exists()) {
					registerFile.createNewFile();
			}
			
			
			FileWriter fw = new FileWriter(registerFile.getAbsoluteFile()); 
			BufferedWriter bw = new BufferedWriter(fw);
			
			String fpTitle = "";
			String fpData = ""; 
			
			for(int i = 0; i < 32; i++){
				if(regs.getFPInit(i))
				{
				fpTitle = fpTitle.concat(regs.getFPName(i) + "    "); 
				fpData = fpData.concat(regs.getFPValue(i) + "  ");   			
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
	
	public void timingOutput(List<Instructions> instructions, int finalCC)
	{
		try{
			System.out.println("Please choose a name for your timing diagram file:"); 
			String fileName = scanner.nextLine(); 
		
			fileName = fileName.concat(".txt"); 
		
			File timingFile = new File(fileName);
		
			if (!timingFile.exists()) {
				timingFile.createNewFile();
			}
			
			FileWriter fw = new FileWriter(timingFile.getAbsoluteFile()); 
			BufferedWriter bw = new BufferedWriter(fw);

			String instCount = "    ";
			for(int i = 0; i < instructions.size(); i++)
			{
				int j = i + 1; 
				instCount = instCount.concat("I#" + j + "  "); 
			}
			
			bw.write(instCount);
			bw.newLine(); 

			for(int i = 0; i < finalCC; i++)
			{			
				String ccTracker = "";
					int l = i + 1;
					ccTracker = ccTracker.concat("C#" + l + "  ");
					for(int j = 0; j < instructions.size(); j++)
					{
						for(int k = 0; k < instructions.get(j).getPipelineCount().size(); k++)
						{
							if(instructions.get(j).getPipelineCount().get(k) == i)
							{
								String newInstruct = instructions.get(j).getInstructName().get(k);
								  ccTracker = ccTracker + "  " + newInstruct + "  ";  

							}
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
}
