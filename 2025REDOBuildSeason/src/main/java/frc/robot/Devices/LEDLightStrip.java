package frc.robot.Devices;

import frc.robot.Devices.NEOSparkMaxMotor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//    https://www.revrobotics.com/content/docs/REV-11-1105-UM.pdf
//go here for info on the colors of the LED light strip.

public class LEDLightStrip {
    private static LEDLightStrip instance = null;
    
    private NEOSparkMaxMotor ledLightStrip;
    
    private double color = 0;
    public enum Color
    {
      RED (0),
      GREEN (1),
      BLUE (2);
      
      public int color;
      private Color(int color) //constructor
      {
          this.color = color;
      }
    }
  
    // public static LEDLightStrip getInstance()
    // {
    //   if (instance == null)
    //   {
    //     instance = new LEDLightStrip();
    //   }
    //   return instance;
    // }
    
    public void set(double color)
    {
      ledLightStrip.set(color);
    }
  
      public void set(Color color)
    {
      ledLightStrip.set(color.color);
    }
}
