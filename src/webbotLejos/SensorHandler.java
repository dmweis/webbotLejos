package webbotLejos;

import java.io.OutputStream;
import java.util.HashMap;

import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.SensorPort;
import lejos.nxt.TemperatureSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;

public class SensorHandler implements Runnable{

	private Thread pollThread;
	private OutputStream output;
	private int delay;
	
	private UltrasonicSensor ultraSensor;
	private TemperatureSensor tempSensor;
	private TouchSensor touchSensor;
	private ColorSensor colorSensor;
	
	public SensorHandler(SensorPort ultraPort, SensorPort tempPort, SensorPort touchPort, SensorPort colorPort) {
		this.ultraSensor = new UltrasonicSensor(ultraPort);
		this.tempSensor = new TemperatureSensor(tempPort);
		this.touchSensor = new TouchSensor(touchPort);
		this.colorSensor = new ColorSensor(colorPort);
		
		this.tempSensor.setResolution(TemperatureSensor.RESOLUTION_9BIT);//this is to minimize the polling time
		
		this.pollThread = new Thread(this, "Sensor Polling Thread");
		this.output = null;
		this.delay = 500;
	}
	
	public void startSending(OutputStream output, int delay)
	{
		this.delay = delay;
		if (this.output == null) {
			this.output = output;
			this.pollThread = new Thread(this, "Sensor Polling Thread");
			this.pollThread.start();			
		}
	}
	
	public void stop()
	{
		try {
			pollThread.join();
		} catch (InterruptedException e) {}
	}
	
	@Override
	public void run() {
		while (output != null) {
			int distance = ultraSensor.getDistance();
			float temperature = tempSensor.getTemperature();
			boolean touch = touchSensor.isPressed();
			Color color = colorSensor.getColor();
			String colorName = getColorName(color);
			String data = distance + " " + touch + " " + temperature + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue() + colorName + "\n";
			try {
				output.write(data.getBytes());
				output.flush();
			} catch (Exception e) {
				output = null;
			}
			Delay.msDelay(delay);
		}
	}
	
	private String getColorName(Color color){
		switch (color.getColor()){
		case Color.BLACK : return "BLACK";
		case Color.BLUE : return "BLUE";
		case Color.CYAN : return "CYAN";
		case Color.DARK_GRAY : return "DARK_GRAY";
		case Color.GRAY : return "GRAY";
		case Color.GREEN : return "GREEN";
		case Color.LIGHT_GRAY : return "LIGHT_GRAY";
		case Color.MAGENTA : return "MAGENTA";
		case Color.ORANGE : return "ORANGE";
		case Color.PINK : return "PINK";
		case Color.RED : return "RED";
		case Color.WHITE : return "WHITE";
		case Color.YELLOW : return "YELLOW";
		case Color.NONE : return "NONE";
		default : return "UNKNOWN";
		}
	}

}
