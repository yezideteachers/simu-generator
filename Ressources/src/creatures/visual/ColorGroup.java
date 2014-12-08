package creatures.visual;

import java.awt.Color;

import creatures.IColorStrategy;

public class ColorGroup implements IColorStrategy {
	
	private int nbGroup;
	private int step; 
	private float r = 0.0f;
	private float g = 0.0f;
	private float b = 0.0f;
	int temp = 1;
	public ColorGroup(int nbGroup) {
		this.nbGroup = nbGroup;
		step = 0;
	}

	
	public Color getColor() {	
		if(nbGroup>1){
			if(step>=10){
				r+=0.5;
				if(r>1){r=0.0f;g+=0.5;
					if(g>1){g=0.0f;b+=0.5;
						if(b>1){b=0.0f;}
					}
				}
				nbGroup-=1;
				step=0;	
			}
			else{step+=1;}
		}
		return new Color(r, g, b);
	}

}
