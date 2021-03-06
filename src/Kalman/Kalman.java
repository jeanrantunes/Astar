package Kalman;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class Kalman {
	
	private double w, pos, meta, volta, marc;
	
	public Kalman(double v1, double v2, double meta){
		this.w = v2/(v1+v2);
		this.pos = 0;
		this.meta = meta;
		this.volta = 13.5;
		this.marc = this.volta/4;
	}
	
	public void filtroKalman(){
		double medUltrassom, medOdometro = 0, init, value;
		int anterior = 7, color;
		Motor.A.setSpeed(100);
		Motor.B.setSpeed(100);
	    Motor.C.setSpeed(100);
	    
	    UltrasonicSensor ultrasom = new UltrasonicSensor(SensorPort.S4);
        init = ultrasom.getDistance();
        
        Motor.A.rotate(36000, true);
	    Motor.B.rotate(-36000, true);
	    Motor.C.rotate(-36000, true);
	    
	    do {
	    		ColorSensor colorSensor = new ColorSensor(SensorPort.S1);
	    		color = colorSensor.getColorID();
	    		//System.out.println(color);
	    		if (color != 7 && anterior == 7){
	    			medOdometro += this.marc;
	    			anterior = color;
	    		}
	    		else
	    			anterior = 7;
	    		//System.out.println("Odometro:"+medOdometro);
    	 		SensorPort.S1.reset();
    	 		
    	 		value = ultrasom.getDistance();
	    		medUltrassom = init - value;
	    		//System.out.println("Ultrassom:"+medUltrassom);
	    		this.pos = medOdometro * this.w + (1-this.w) * medUltrassom;
				//System.out.println(this.pos);
			} while (this.pos < this.meta && Motor.B.isMoving());
	    
	    Motor.A.stop();
	    Motor.B.stop();
	    Motor.C.stop();
	    
	    System.out.println("Odometro:"+medOdometro);
	    System.out.println("Ultrassom:"+medUltrassom);
	}
	//KALMAN W = V�2/(V�1+V�2)

	 	/*pos = medicao odometro*w + (1-w)*medicao ultrasom*/
	/*
	double v1 = 0.587644, v2 = 0.536655, w, pos = 0, init, roda = 13.2, distance, value;
	int color, count = 0, anterior = 7;
	
	w = v2/(v1+v2);
	*/
	
}
