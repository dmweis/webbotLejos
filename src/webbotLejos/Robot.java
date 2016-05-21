package webbotLejos;

import lejos.nxt.LCD;
import lejos.nxt.NXTRegulatedMotor;

/**
 * simple encapsulation class for controlling the robot's basic functionality
 * 
 * @author DWEIS
 *
 */
public class Robot {
	private NXTRegulatedMotor rightMotor;
	private NXTRegulatedMotor leftMotor;

	/** 
	 * @param rightMotor motor on the right side of the robot
	 * @param leftMotor  motor on the left side of the robot
	 */
	public Robot(NXTRegulatedMotor rightMotor, NXTRegulatedMotor leftMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
	}

	/**
	 * sets both motors to go forward
	 */
	public void forward() {
		rightMotor.forward();
		leftMotor.forward();
	}

	/**
	 * sets both motors to go backwards
	 */
	public void backward() {
		rightMotor.backward();
		leftMotor.backward();
	}

	/**
	 * sets both motors to turn left
	 */
	public void turnLeft() {
		rightMotor.forward();
		leftMotor.backward();
	}

	/**
	 * sets both motors to turn right
	 */
	public void turnRight() {
		leftMotor.forward();
		rightMotor.backward();
	}

	/**
	 * stops both motors and hold position
	 */
	public void stop() {
		leftMotor.stop(true); 
		rightMotor.stop(true);
	}

	/**
	 * stops supplying power to the motors to stop them but doesn't prevent manual turning of the wheels
	 */
	public void flt() {
		leftMotor.flt(true);
		rightMotor.flt(true);
	}

	/**
	 * method for printing text on the LCD display
	 * @param text
	 */
	public void display(String text) {
		LCD.clearDisplay();
		int index = 0;
		int line = 0;
		while (index < text.length()) {
			LCD.drawString(text.substring(index, Math.min(index + 16, text.length())), 0, line);
			line += 1;
			index += 16;
		}
	}
}
