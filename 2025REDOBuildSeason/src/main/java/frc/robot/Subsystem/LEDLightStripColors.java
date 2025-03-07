package frc.robot.Subsystem;
import frc.robot.Devices.NEOSparkMaxMotor;

public class LEDLightStripColors {
    NEOSparkMaxMotor ledLightStrip;

    public void normalColor(){
        ledLightStrip.set(0.89); // supposed to be Blue Violet
    }
    public void coralColor(){
        ledLightStrip.set(0.93); // supposed to be White
    }
}
