package webbotLejos;

import java.io.InputStream;
import java.io.OutputStream;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

public class RemoteController{
	private Robot robot;
	private SensorHandler sensorHandler;

	private NXTConnection connection;
	private OutputStream output;
	private InputStream input;

	public RemoteController(Robot robot, SensorHandler sensorHandler) {
		this.robot = robot;
		this.sensorHandler = sensorHandler;
		
		this.connection = null;
		this.output = null;
		this.input = null;
	}

	public boolean connect() {
		robot.display("Waiting for connection");
		connection = Bluetooth.waitForConnection(0, NXTConnection.RAW);
		robot.display("Connected");
		try {
			input = connection.openInputStream();
			output = connection.openOutputStream();
		} catch (Exception e){
			return false;
		}
		return true;
	}
	
	public void run()
	{
		while(true)
		{
			try {
				int command = input.read();
				robot.display("" + command);
				switch (command)
				{
				case -1 : return;
				case 0 : robot.display("Reading not implemented yet"); break;
				case 1 : robot.forward(); break;
				case 2 : robot.backward(); break;
				case 3 : robot.turnLeft(); break;
				case 4 : robot.turnRight(); break;
				case 5 : robot.stop(); break;
				case 6 : robot.flt(); break;
				case 7 : startSensorUpdates(); break;
				}
			} catch (Exception e) 
			{ 
				robot.stop();
				sensorHandler.stop();
				return; 
			}
		}
	}
	
	private void startSensorUpdates()
	{
		sensorHandler.startSending(output, 500);
	}
}
