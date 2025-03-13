package frc.robot.Devices;

import frc.robot.Data.ButtonMap;
import frc.robot.Data.PortMap;
import frc.robot.Devices.NEOSparkMaxMotor;

import com.studica.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://www.revrobotics.com/content/docs/REV-11-1105-UM.pdf
// go here for info on the colors of the LED light strip.

public class LEDLightStrip {
  private static LEDLightStrip instance = null;
  private NEOSparkMaxMotor ledLightStrip;

  public double blueViolet = 0.89;
  private double white = 0.93;

  public static LEDLightStrip getInstance() {
    if (instance == null) 
    {
      instance = new LEDLightStrip();
    }
    return instance;
  }

  public LEDLightStrip()
  {
    this.ledLightStrip = new NEOSparkMaxMotor(PortMap.LED_LIGHT_STRIP);
    SmartDashboard.putNumber("LED color", 0);
  }

  public void normalColor() {
    ledLightStrip.set(blueViolet); // supposed to be Blue Violet
    System.out.println("Color: blue violet");
  }

  public void coralColor() {
    ledLightStrip.set(white); // supposed to be White
    System.out.println("Color: white");
  }
}
