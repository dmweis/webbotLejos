package webbotLejos;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

public class RemoteController {
	private SensorHandler sensorHandler;
	private Robot robot;

	private NXTConnection connection;
	private DataOutputStream output;
	private DataInputStream input;

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
		input = connection.openDataInputStream();
		output = connection.openDataOutputStream();
	}
}
