package main;

import creatures.IColorStrategy;
import creatures.visual.ColorCube;

public class Constante {
	public static int NB_CREATURE=50 ; 
	public static boolean visu=false ;
	//public static int COULEUR;
	public static int vitesseSimulator=60 ;
	
	public static  IColorStrategy getColor(){
			return new ColorCube(50);
	}

}
