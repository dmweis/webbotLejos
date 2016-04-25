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
	
	@Override
	public void run() {
		while(true)
		{
			byte[] distance = new byte[]{(byte) sensorHandler.getDistance()};
			try {
				output.write(distance);
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
