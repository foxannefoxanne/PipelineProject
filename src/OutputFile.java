import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.io.InputStream;


public class OutputFile {

	Scanner scanner = new Scanner(System.in); 
	
	public void registerOutput(List<Registers> register)
	{
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
			
			String intTitle = ""; 
			String fpTitle = "";
			String intData = "";
			String fpData = ""; 
			
			for(int i; i < 32; i++)
			{
				intTitle = intTitle.concat("R" + i + "  "); 
				fpTitle = fpTitle.concat("F" + i + "  "); 
				// intData = intData.concat( + "  "); there should be a difference between int and fp
				// fpData = fpData.concat( + "  ");   designed. add those in once you get there. 
			}
			bw.write(intTitle);
			bw.write(intData);
			bw.write(""); 
			bw.write(fpTitle);
			bw.write(fpData);
			bw.close();
			
			System.out.println("Register file complete"); 
		
		} 
		catch(IOException e){
			
		}
	}
	
	public void timingOutput(List<Instructions> instructions)
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

			String instCount = "";
			for(int i = 0; i < instructions.getinstCount(); i++)
			{
				int j = i + 1; 
				instCount = instCount.concat("I#" + j + "  "); 
			}
			
			//double for loop
			//first for all clock cycles
			//second to see if each instruction has something in it. 
			//for(int i = 0; i < instructions.clockCycleCount(); i++)
			//{
				
				
		///	}
		} 
		catch(IOException e){
			
		}
		
	}
}
