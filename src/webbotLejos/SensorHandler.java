package webbotLejos;

import java.io.OutputStream;

import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.SensorPort;
import lejos.nxt.TemperatureSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;

/**
 * @author DWEIS
 * class that handles input from the sensors and sends data over Bluetooth
 */
public class SensorHandler implements Runnable{

	private Thread pollThread; //thread used for polling the sensors and transmitting data
	private OutputStream output; //out stream used to send data
	private int delay; //delay between sensor updates
	
	//instances of all used sensors
	private UltrasonicSensor ultraSensor;
	private TemperatureSensor tempSensor;
	private TouchSensor touchSensor;
	private ColorSensor colorSensor;
	
	/**
	 * @param ultraPort SensorPort for the UltrasonicSensor
	 * @param tempPort SensorPort for the TemperatureSensor
	 * @param touchPort SensorPort for the TouchSensor
	 * @param colorPort SensorPort for the ColorSensor
	 */
	public SensorHandler(SensorPort ultraPort, SensorPort tempPort, SensorPort touchPort, SensorPort colorPort) {
		this.ultraSensor = new UltrasonicSensor(ultraPort);
		this.tempSensor = new TemperatureSensor(tempPort);
		this.touchSensor = new TouchSensor(touchPort);
		this.colorSensor = new ColorSensor(colorPort);
		
		this.tempSensor.setResolution(TemperatureSensor.RESOLUTION_9BIT);//this is to minimize the polling time by lowering the resolution 
		
		this.pollThread = new Thread(this, "Sensor Polling Thread");
		this.output = null;
		this.delay = 500;
	}
	
	/**
	 * assigns an output stream to send sensor data over
	 * @param output out stream that should be used to transmit data
	 * @param delay delay between sensor reports
	 */
	public void startSending(OutputStream output, int delay)
	{
		this.delay = delay;
		if (this.output == null) {
			this.output = output;
			this.pollThread = new Thread(this, "Sensor Polling Thread");
			this.pollThread.start();			
		}
	}
	
	/**
	 * blocking method that holds until the sensor output ends
	 */
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
			String data = distance + " " + touch + " " + temperature + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " " + colorName + "\n";
			try {
				output.write(data.getBytes());
				output.flush();
			} catch (Exception e) {
				output = null;
			}
			Delay.msDelay(delay);
		}
	}
	
	/**
	 * this method returns String that describes color it takes as argument
	 * @param color
	 * @return string descriptor
	 */
	private String getColorName(Color color){
		switch (color.getColor()){
		case Color.BLACK : return "Black";
		case Color.BLUE : return "Blue";
		case Color.CYAN : return "Cyan";
		case Color.DARK_GRAY : return "Dark gray";
		case Color.GRAY : return "Gray";
		case Color.GREEN : return "Green";
		case Color.LIGHT_GRAY : return "Light gray";
		case Color.MAGENTA : return "Magenta";
		case Color.ORANGE : return "Orange";
		case Color.PINK : return "Pink";
		case Color.RED : return "Red";
		case Color.WHITE : return "White";
		case Color.YELLOW : return "Yellow";
		case Color.NONE : return "None";
		default : return "Unknown";
		}
	}

}
