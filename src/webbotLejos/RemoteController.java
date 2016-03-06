package webbotLejos;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

public class RemoteController
{
      private NXTConnection connection;
      private DataOutputStream output;
      private DataInputStream input;
      
      public RemoteController(){
         this.connection = null;
         this.output = null;
         this.input = null;
      }
      
      public void connect(){
         print("Waiting for connection");
         connection = Bluetooth.waitForConnection(0, NXTConnection.RAW);
         print("Connected");
         input = connection.openDataInputStream();
         output = connection.openDataOutputStream();
      }
      
      private void print(String text){
         LCD.clear();
         LCD.drawString(text, 0, 0);
      }
}
