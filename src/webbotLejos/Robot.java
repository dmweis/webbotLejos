package webbotLejos;

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
      leftMotor.stop();
      rightMotor.stop();
   }
   
   public void flt()
   {
      leftMotor.flt();
      rightMotor.flt();
   }
}
