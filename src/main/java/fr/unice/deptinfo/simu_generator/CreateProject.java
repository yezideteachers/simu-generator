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
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreateProject {
	
	public static int nbrCreature=10;
	public static boolean visu= false;
	public static int vitesseSimu = 10 ;
	public static Map<String,Method> map = new HashMap<String, Method>(); 
	
	
	/*create folder src , bin*/
	public void createProject(String folder){
		new File(folder).mkdir();
		
	}
	
	public void generateMethode(){
		
	}
/*	public void generateVitesse(String ch){
		String value = map.get(ch);
		if(())
	}
	*/
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
	public void editConst(String oldFileName){
		  
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
	            	line = line.replace(s[1], nbrCreature+" ; ");
	            	System.out.println(Arrays.toString(s));
	            }
	           
	            if (line.contains("visu=")){
	            	String [] s = line.split("=");          	
	            	line = line.replace(s[1], visu + " ;"); 
	            }
	            if (line.contains("vitesseSimulator=")){
	            	String [] s = line.split("=");          	
	            	line = line.replace(s[1],vitesseSimu+" ;"); 
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
	
	public static int randomNb(int min , int max)
	{
		Random rand = new Random();

	    
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public static void generate(Collection<String> selected)
	{
		CreateProject cr= new CreateProject();
		cr.createProject("generated");
		cr.createProject("generated/src");
		cr.createProject("generated/src/simulator");
		cr.createProject("generated/src/main");
		Map map = new  HashMap<String, String>();
		for (String c : selected)
		{
			
			if (c.equals("Dizaine"))
			{
				nbrCreature=randomNb(10 , 99);	
			}
			if (c.equals("Centaine"))
			{
				nbrCreature=randomNb(100 , 999);		
			}
			if (c.equals("Milliers"))
			{
				nbrCreature=randomNb(1000 , 9999);	
			}
			if (c.equals("Visu"))
			{
				visu=true;	
			}
			if (c.equals("VLent"))
			{
				vitesseSimu=30;	
			}
			if (c.equals("VRapide"))
			{
				vitesseSimu=3;	
			}
			
		}
	
		cr.generateFile("Ressources/src/simulator/Simulator.java", "generated/src/simulator/Simulator.java");
		cr.generateFile("Ressources/src/simulator/ISimulationListener.java", "generated/src/simulator/ISimulationListener.java");
		cr.generateFile("Ressources/src/simulator/IActionable.java", "generated/src/simulator/IActionable.java");
		cr.editConst("Ressources/src/main/Constante.java");
		cr.generateFile("Ressources/src/main/Constante.java", "generated/src/main/Constante.java");
	}
	
	
	/*
	public static void RandNbD()
	{
		nbrCreature=randomNb(10 , 99);	 
	}
	
	public static void RandNbC()
	{
		nbrCreature=randomNb(100 , 999);		 
	}
	
	public static void RandNbM()
	{
		nbrCreature=randomNb(1000 , 9999);	
	}
	
	
		public static void generate(Collection<String> selected)
	{
		CreateProject cr= new CreateProject();
		cr.createProject("generated");
		cr.createProject("generated/src");
		cr.createProject("generated/src/simulator");
		cr.createProject("generated/src/main");
		map.put("Dizaine", RandNbD());
		map.put("Centaine",RandNbC());
		map.put("Milliers", RandNbM());
		for (String c : selected)
		{
			
			if (c.equals("Dizaine"))
			{
				nbrCreature=randomNb(10 , 99);	
			}
			if (c.equals("Centaine"))
			{
				nbrCreature=randomNb(100 , 999);		
			}
			if (c.equals("Milliers"))
			{
				nbrCreature=randomNb(1000 , 9999);	
			}
			Method method = map.get(c);
			method.;
			//if (c.equals(""))
			
		}
		
		cr.generateFile("Ressources/src/simulator/Simulator.java", "generated/src/simulator/Simulator.java");
		cr.generateFile("Ressources/src/simulator/ISimulationListener.java", "generated/src/simulator/ISimulationListener.java");
		cr.generateFile("Ressources/src/simulator/IActionable.java", "generated/src/simulator/IActionable.java");
		cr.editConst("Ressources/src/main/Constante.java", "false", "60");
		cr.generateFile("Ressources/src/main/Constante.java", "generated/src/main/Constante.java");
	}
	*/
	void generateSimulator(){
		
	}


}
