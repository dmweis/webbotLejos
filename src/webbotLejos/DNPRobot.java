package webbotLejos;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class DNPRobot {

	/**
	 * main method that starts connection and keeps restarting it if it fails
	 * @param args
	 */
	public static void main(String[] args) {
		Robot robot = new Robot(Motor.B, Motor.A);
		SensorHandler sensorHandler = new SensorHandler(SensorPort.S3, SensorPort.S4, SensorPort.S2, SensorPort.S1);
		RemoteController remote = new RemoteController(robot, sensorHandler);
		while(true){
			if (remote.connect()) {
				remote.run();
				robot.flt();
			}
		}
	}

}
