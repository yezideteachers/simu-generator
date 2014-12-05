package fr.unice.deptinfo.simu_generator;

import java.io.FileFilter ;
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
import java.util.Iterator;
import java.util.Map;
import java.util.Random;







import fr.unice.deptinfo.maven_compiler.FileTool;

public class CreateProject {
	
	public static int nbrCreature=10;
	public static boolean visu= false;
	public static int vitesseSimu = 10 ;
	public static Map<String,Method> map = new HashMap<String, Method>(); 
	public static Map mapNbCreat = new  HashMap<String, Integer>();
	public static Map mapVitSimul = new  HashMap<String, Integer>();
	public static Map mapStrategy = new  HashMap<String, String>();
	/*create folder src , bin*/
	public void createProject(String folder){
		new File(folder).mkdir();
		
		mapNbCreat.put("Dizaine", randomNb(10, 99));
		mapNbCreat.put("Centaine", randomNb(100, 999));
		mapNbCreat.put("Milliers", randomNb(1000, 9999));
		
		mapVitSimul.put("VLent", 30);
		mapVitSimul.put("VRapide", 5);
		
		mapStrategy.put("Torique", "StrategyTorique.class");
		mapStrategy.put("Hasard", "StrategyHasard.class");
		mapStrategy.put("Circulaire", "StrategyCirculaire.class");
		mapStrategy.put("Troupeau", "StrategyTroupeau.class");
		mapStrategy.put("Ferme", "StrategyFerme.class");
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
	
		System.out.println("selected generer :  " + selected);
		
		for (String c : selected)
		{
			
			if(mapNbCreat.get(c) != null)
			{
				nbrCreature = (Integer) mapNbCreat.get(c);
			}
			if(mapVitSimul.get(c) != null)
			{
				vitesseSimu = (Integer) mapVitSimul.get(c);
			}
			
			if (c.equals("Visu"))
			{
				visu=true;
			}
			
			
		}
		FileFilter fileFilter=MyFilterMyplugins(selected);
		
		cr.editConst("Ressources/src/main/Constante.java");
		
		
		try {
			FileTool.copyFilesRecursively(new File("Ressources"), new File("Ressources/"), new File("generated"),fileFilter);
			fileFilter=MyFilterCreatures(selected);
			new File("generated/myplugins/repository/").mkdir();
			FileTool.copyFilesRecursively(new File("Ressources/myplugins/repository/creatures"), new File("Ressources/myplugins/repository/creatures/"),
					new File("generated/myplugins/repository/creatures"),fileFilter);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static FileFilter MyFilterMyplugins(final Collection<String> selected){
		FileFilter fileFilter=new FileFilter(){
			public boolean accept(File file)
			{
				for (String c : selected) {
			
				if (file.getName().equals("repository"))
				{
					return false;
				}}
				return true;
			}
			
			public String getDescription() {
				// TODO Auto-generated method stub
				return "File les fichier de strategy non voulu dans la configuration";
			}
			
		};
		return fileFilter;
	}
	
	public static FileFilter MyFilterCreatures(final Collection<String> selected){
		FileFilter fileFilter=new FileFilter(){
			public boolean accept(File file)
			{
				for (String c : selected) {
				
				if ((mapStrategy.get(c) != null)&&(file.getName().equals(mapStrategy.get(c))||
						file.getName().equals((((String)mapStrategy.get(c)).split(".class"))[0]+"Test.class")))
				{
					return true;
				}}
				return false;
			}
			
			public String getDescription() {
				// TODO Auto-generated method stub
				return "File les fichier de strategy non voulu dans la configuration";
			}
			
		};
		return fileFilter;
	}
	
	


}
