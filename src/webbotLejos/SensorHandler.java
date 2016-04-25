package webbotLejos;

import java.io.OutputStream;

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
			String data = distance + " " + touch + " " + temperature + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue() + "\n";
			try {
				output.write(data.getBytes());
				output.flush();
			} catch (Exception e) {
				output = null;
			}
			Delay.msDelay(delay);
		}
	}

}
