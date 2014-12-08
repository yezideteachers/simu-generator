package main;

import creatures.IColorStrategy;
import creatures.visual.*;

public class Constante {
	public static int NB_CREATURE=76 ; 
	public static boolean visu=false ;
	//public static int COULEUR;
	public static int vitesseSimulator=30 ;
	
	public static  IColorStrategy getColor(){
			return new ColorCube(50) ;
	}

}
