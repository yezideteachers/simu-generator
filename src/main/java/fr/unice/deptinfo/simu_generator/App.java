package fr.unice.deptinfo.simu_generator;

import java.util.Scanner;

import fr.unice.deptinfo.familiar_interpreter.FMEngineException;
import fr.unice.deptinfo.familiar_interpreter.impl.FamiliarInterpreter;
import fr.unice.deptinfo.maven_compiler.FileTool;
import fr.unice.polytech.modalis.familiar.interpreter.VariableNotExistingException;
import fr.unice.polytech.modalis.familiar.parser.VariableAmbigousConflictException;
import fr.unice.polytech.modalis.familiar.variable.FeatureModelVariable;


public class App 
{
    public static void main( String[] args )
    {
    	
    	String fmName = "simul";
    	String FM = fmName+" = FM(SimuTechno: [ICreature] [Visu] Moteur;"+
    			"ICreature: Deplacement ComportementAuBord Couleur Nombre Direction;"+
    			"Deplacement: (Hasard|Troupeau)+;"+
    			"Couleur: (Cube|Groupe|Unique);"+
    			"ComportementAuBord: (Torique|Circulaire|Ferme)+;"+
    			"Nombre: (NAleatoire|Fixe);"+
    			"NAleatoire: (Dizaine|Centaine|Milliers);"+
    			"Moteur: VitesseSimu Action;"+
    			"Action: Sequentiel;"+
    			"VitesseSimu: (VLent|VRapide|VNormal);"+
    			"Direction: (DAleatoire|DFixe);"+
    			"DFixe -> Troupeau;" + 
    			"DAleatoire -> Hasard;)";
    	String configName = "SimuTechno";
    	
    	FamiliarInterpreter fi = FamiliarInterpreter.getInstance();
    	
    	try {
			fi.eval(FM);
			FeatureModelVariable fmv = fi.getFMVariable(fmName);
	    	
	    	System.out.println("Instancied FM : "+fmv.getSyntacticalRepresentation());
	    	
	    	fi.eval(configName+" = configuration "+fmName);
	    	
	    	
	        Scanner scan = new Scanner(System.in);
	        String s = "";
	        String selectCmd = "select ";
	        
	  
	        do {
	        	System.out.println("Enter the name of features you wish to select, or type exit to exit , or type finish when you want the project to be generated");
	        	s = scan.nextLine();
	        	
	        	if (s.equals("Fixe"))
	        	{	String temp = s;
	        		System.out.println("Enter the number of creature : ");
	        		s = scan.nextLine();
	        		CreateProject.nbrCreature=Integer.valueOf(s);
	        		s=temp;
	        	}
	        	if (!s.equals("exit")) {
		        	fi.eval(selectCmd+s+" in "+configName);
		        	System.out.println("Selected features :"+fi.getSelectedFeature(configName));
		        	System.out.println("Deselected features :"+fi.getDeselectedFeature(configName));
		        	System.out.println("Unselected features :"+fi.getUnselectedFeature(configName));
		        	
		        	
	        	}
	        	
	        	if (s.equals("finish"))
	        	{	//Mise en place de la configuration complète .
	        		//Tout ce qui n'a pas etait selectionné est considerer comme deselectionner .
	        		for (String feature : fi.getUnselectedFeature(configName))
	        		{
	        			fi.eval("deselect "  + feature +" in "+configName);
	        			
	        		}
	        		
	        		
	        		//On ne lance la generation que si la configuration est valide et complete .	
	        		if((fi.getConfigurationVariable(configName).isComplete()) && (fi.getConfigurationVariable(configName).isValid())){
	        			System.out.println("The configuration is complete : "+fi.getConfigurationVariable(configName).isComplete());
	        			CreateProject.generate(fi.getSelectedFeature(configName));
	        			break;
	        		}
	        		else{
	        			
	        			 System.out.println("The config isn't complete yet . Please complete the configuration before generating it .");
	        		}
	        	}
	        	
	        }
	        while (!s.equals("exit"));
		} catch (FMEngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VariableNotExistingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VariableAmbigousConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
