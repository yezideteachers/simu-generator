package fr.unice.deptinfo.simu_generator;

import java.util.Scanner;

import fr.unice.deptinfo.familiar_interpreter.FMEngineException;
import fr.unice.deptinfo.familiar_interpreter.impl.FamiliarInterpreter;
import fr.unice.deptinfo.maven_compiler.FileTool;
import fr.unice.polytech.modalis.familiar.interpreter.VariableNotExistingException;
import fr.unice.polytech.modalis.familiar.parser.VariableAmbigousConflictException;
import fr.unice.polytech.modalis.familiar.variable.FeatureModelVariable;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	String fmName = "simul";
    	String FM = fmName+" = FM(SimuTechno: [ICreature] [Visu] Moteur;"+
			  "ICreature: Deplacement ComportementAuBord Couleur Nombre;"+
			  "Deplacement: (Hasard|Troupeau);"+
			  "Couleur: (Cube|Groupe|Unique);"+
			  "ComportementAuBord: (Torique|Circulaire|Ferme);"+
			  "Nombre: (NAleatoire|Fixe);"+
			  "NAleatoire: (Dizaine|Centaine|Milliers);"+
			  "Moteur: VitesseSimu Action;"+
			  "Action: Sequentiel;"+
			 "VitesseSimu: (VLent|VRapide|VNormal);)";
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
	        	if (s.equals("finish"))
	        	{
	        		CreateProject.generate(fi.getSelectedFeature(configName));
	        		break;
	        	}
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
		        	System.out.println("The configuration is complete : "+fi.getConfigurationVariable(configName).isComplete());
	        	}
	        	
	        } while (!s.equals("exit"));
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
