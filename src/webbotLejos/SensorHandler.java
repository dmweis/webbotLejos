package webbotLejos;

import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.SensorPort;
import lejos.nxt.TemperatureSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;

public class SensorHandler implements Runnable{

	private Thread pollThread;
	
	private UltrasonicSensor ultraSensor;
	private TemperatureSensor tempSensor;
	private TouchSensor touchSensor;
	private ColorSensor colorSensor;
	
	private int distance;
	private float temperature;
	private boolean touch;
	private int[] color;
	
	public SensorHandler(SensorPort ultraPort, SensorPort tempPort, SensorPort touchPort, SensorPort colorPort) {
		this.ultraSensor = new UltrasonicSensor(ultraPort);
		this.tempSensor = new TemperatureSensor(tempPort);
		this.touchSensor = new TouchSensor(touchPort);
		this.colorSensor = new ColorSensor(colorPort);
		
		this.distance = 0;
		this.temperature = 0f;
		this.touch = false;
		this.color = new int[] {0, 0, 0};
		
		this.tempSensor.setResolution(TemperatureSensor.RESOLUTION_9BIT);//this is to minimize the polling time
		
		this.pollThread = new Thread(this, "Sensor Polling Thread");
		pollThread.start();
	}
	
	@Override
	public void run() {
		int distance = ultraSensor.getDistance();
		this.distance = distance;
		float temperature = tempSensor.getTemperature();
		this.temperature = temperature;
		boolean touch = touchSensor.isPressed();
		this.touch = touch;
		Color color = colorSensor.getRawColor();
		this.color[0] = color.getRed();
		this.color[1] = color.getGreen();
		this.color[2] = color.getBlue();
	}
	
	public int getDistance() {
		return distance;
	}
	
	public float getTemperature() {
		return temperature;
	}
	
	public boolean getTouch() {
		return touch;
	}
	
	public int[] getColor() {
		return color.clone();
	}


}
