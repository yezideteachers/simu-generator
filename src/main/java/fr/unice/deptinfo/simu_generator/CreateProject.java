package fr.unice.deptinfo.simu_generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class CreateProject {
	
	/*create folder src , bin*/
	public void createProject(String folder){
		new File(folder).mkdir();
		
	}
	
	public void generateMethode(){
		
	}
	
	public void generateFile(String source, String dest){
		try {
			File destination = new File(dest);
			//writer.createNewFile(); 
			java.io.FileOutputStream destinationFile=null; 
			FileInputStream sourceFile = new java.io.FileInputStream(source);
			destinationFile = new java.io.FileOutputStream(destination);
			// Lecture par segment de 0.5Mo
			byte buffer[]=new byte[512*1024];
			int nbLecture;
			while( (nbLecture = sourceFile.read(buffer)) != -1 ) {
			destinationFile.write(buffer, 0, nbLecture); 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void editConst(String oldFileName,String nb , String vis, String vitesse){
		  
		 String tmpFileName = "temp.java";
	      BufferedReader br = null;
	      BufferedWriter bw = null;
	      try {
	         br = new BufferedReader(new FileReader(oldFileName));
	         bw = new BufferedWriter(new FileWriter(tmpFileName));
	         String line;
	         while ((line = br.readLine()) != null) {
	        	 
	            if (line.contains("NB_CREATURE=")){
	            	String [] s = line.split("=");          	
	            	line = line.replace(s[1], nb+" ; ");
	            	System.out.println(Arrays.toString(s));
	            }
	           
	            if (line.contains("visu=")){
	            	String [] s = line.split("=");          	
	            	line = line.replace(s[1],vis+" ;"); 
	            }
	            if (line.contains("vitesseSimulator=")){
	            	String [] s = line.split("=");          	
	            	line = line.replace(s[1],vitesse+" ;"); 
	            }
	            bw.write(line+"\n");
	         }
	         
	      } catch (Exception e) {
	    	  System.out.println("file not find !");
	         return;
	      } finally {
	         try {
	            if(br != null)
	               br.close();
	         } catch (IOException e) {
	            //
	         }
	         try {
	            if(bw != null)
	               bw.close();
	         } catch (IOException e) {
	            //
	         }
	      }
	      // Once everything is complete, delete old file..
	      File oldFile = new File(oldFileName);
	      oldFile.delete();

	      // And rename tmp file's name to old file name
	      File newFile = new File(tmpFileName);
	      newFile.renameTo(oldFile);

	}
	
	 
	
	public static void main(String[] args){
		CreateProject cr= new CreateProject();
		cr.createProject("generated");
		cr.createProject("generated/src");
		cr.createProject("generated/src/simulator");
		cr.createProject("generated/src/main");
	
		cr.generateFile("Ressources/src/simulator/Simulator.java", "generated/src/simulator/Simulator.java");
		cr.generateFile("Ressources/src/simulator/ISimulationListener.java", "generated/src/simulator/ISimulationListener.java");
		cr.generateFile("Ressources/src/simulator/IActionable.java", "generated/src/simulator/IActionable.java");
		cr.editConst("Ressources/src/main/Constante.java","50" , "false", "60");
		cr.generateFile("Ressources/src/main/Constante.java", "generated/src/main/Constante.java");
		
    }


}
