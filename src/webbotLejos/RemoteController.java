package webbotLejos;

import java.io.InputStream;
import java.io.OutputStream;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

/**
 * @author DWEIS
 * class that handles remote controlling of the Robot
 */
public class RemoteController{
	private Robot robot;
	private SensorHandler sensorHandler;

	private NXTConnection connection;
	private OutputStream output;
	private InputStream input;

	/**
	 * @param robot instance of Class Robot
	 * @param sensorHandler Sensor handler used poll data
	 */
	public RemoteController(Robot robot, SensorHandler sensorHandler) {
		this.robot = robot;
		this.sensorHandler = sensorHandler;
		
		this.connection = null;
		this.output = null;
		this.input = null;
	}
	
	/** method waits for Bluetooth connection
	 * @return true is connection was successfully initialized
	 */
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
	
	/**
	 * thread for receiving and dispatching commands for the robot
	 */
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
				case 0 : robot.display("this function was deprecated"); break;
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
	
	/**
	 * this method starts the sensor update thread
	 */
	private void startSensorUpdates()
	{
		sensorHandler.startSending(output, 500);
	}
}
