package webbotLejos;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class MainTest {

	public static void main(String[] args) {
		Robot robot = new Robot(Motor.B, Motor.A);
		SensorHandler sensorHandler = new SensorHandler(SensorPort.S3, SensorPort.S4, SensorPort.S2, SensorPort.S1);
		SensorUpdaterThread sensorUpdaterThread = new SensorUpdaterThread(sensorHandler);
		while(true){
			RemoteController remote = new RemoteController(robot, sensorUpdaterThread);
			remote.connect();
			remote.run();
		}
	}

}
