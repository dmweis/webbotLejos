package webbotLejos;

import java.io.IOException;
import java.io.OutputStream;

public class SensorUpdaterThread implements Runnable{

	private SensorHandler sensorHandler;
	private OutputStream output;
	private Thread executionThread;
	private int delay;
	
	public SensorUpdaterThread(SensorHandler sensorHandler, OutputStream output,int delay){
		this.sensorHandler = sensorHandler;
		this.output = output;
		this.delay = delay;
		executionThread = new Thread(this);
		executionThread.start();
	}
	
	private byte[] dataInByte()
	{
		int distance = sensorHandler.getDistance();
		boolean touch = sensorHandler.getTouch();
		float temperature = sensorHandler.getTemperature();
		int[] RGB = sensorHandler.getColor();
		String data = distance + " " + touch + " " + temperature + " " + RGB[0] + " " + RGB[1] + " " + RGB[2] + "\n";
		return data.getBytes();
	}
	@Override
	public void run() {
		while(true)
		{
			byte[] data = dataInByte();
			try {
				output.write(data);
				output.flush();
			} catch (IOException e) {
			}
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			}
		}
	}

}
