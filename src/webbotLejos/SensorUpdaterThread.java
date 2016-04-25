package webbotLejos;

import java.io.OutputStream;

public class SensorUpdaterThread implements Runnable {

	private SensorHandler sensorHandler;
	private OutputStream output;
	private Thread executionThread;
	private int delay;

	public SensorUpdaterThread(SensorHandler sensorHandler) 
	{
		this.sensorHandler = sensorHandler;
		this.output = null;
		this.delay = 0;
		executionThread = new Thread(this, "SensorUpdaterThread");
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
	
	public void start(OutputStream output, int delay)
	{
		this.delay = delay;
		if(!executionThread.isAlive())
		{
			this.output = output;
			executionThread.start();
		}
	}
	
	public boolean isRunning()
	{
		return executionThread.isAlive();
	}

	@Override
	public void run() {
		while (true) {
			byte[] data = dataInByte();
			try {
				output.write(data);
				output.flush();
			} catch (Exception e) {
				output = null;
				return;
			}
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			}
		}
	}

}
