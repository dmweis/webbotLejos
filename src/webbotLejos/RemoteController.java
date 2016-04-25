package webbotLejos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

public class RemoteController {
	private SensorHandler sensorHandler;
	private Robot robot;

	private NXTConnection connection;
	private DataOutputStream output;
	private InputStream input;

	public RemoteController(SensorHandler sensorHandler, Robot robot) {
		this.sensorHandler = sensorHandler;
		this.robot = robot;
		
		this.connection = null;
		this.output = null;
		this.input = null;
	}

	public void connect() {
		robot.display("Waiting for connection");
		connection = Bluetooth.waitForConnection(0, NXTConnection.RAW);
		robot.display("Connected");
		input = connection.openInputStream();
		output = connection.openDataOutputStream();
	}
	
	public void run()
	{
		while(true)
		{
			try {
				int command = input.read();
				switch (command)
				{
				case 0 : robot.display("Reading not implemented yet"); break;
				case 1 : robot.forward(); break;
				case 2 : robot.backward(); break;
				case 3 : robot.turnLeft(); break;
				case 4 : robot.turnRight(); break;
				case 5 : robot.stop(); break;
				case 6 : robot.flt(); break;
				}
			} catch (IOException e) {}
		}
	}
}
