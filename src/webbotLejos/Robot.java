package webbotLejos;

import lejos.nxt.LCD;
import lejos.nxt.NXTRegulatedMotor;
// Hello David 

public class Robot
{
   private NXTRegulatedMotor rightMotor;
   private NXTRegulatedMotor leftMotor;
   
   public Robot(NXTRegulatedMotor rightMotor, NXTRegulatedMotor leftMotor)
   {
      this.leftMotor = leftMotor;
      this.rightMotor = rightMotor;
   }
   
   public void forward(){
      rightMotor.forward();
      leftMotor.forward();
   }

   public void backward(){
      rightMotor.backward();
      leftMotor.backward();
   }
   
   public void turnLeft()
   {
      rightMotor.forward();
      leftMotor.backward();
   }
   
   public void turnRight()
   {
      leftMotor.forward();
      rightMotor.backward();
   }
   
   public void stop()
   {
	   flt(); //this should fix the slow down effect
      leftMotor.stop();
      rightMotor.stop();
   }
   
   public void flt()
   {
      leftMotor.flt();
      rightMotor.flt();
   }
   
   public void display(String text)
   {
	   LCD.clearDisplay();
	   int index = 0;
	   int line = 0;
	   while(index < text.length())
	   {
		   LCD.drawString(text.substring(index, Math.min(index + 16, text.length())), 0, line);
		   line+=1;
		   index+=16;
	   }
   }
}
