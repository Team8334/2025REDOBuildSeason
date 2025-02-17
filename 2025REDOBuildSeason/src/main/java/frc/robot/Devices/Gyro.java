package frc.robot.Devices;

import com.studica.frc.AHRS;
// https://www.kauailabs.com/public_files/navx-mxp/apidocs/java/com/kauailabs/navx/frc/AHRS.html
    // This is the link of documentation
/*
 * Gyro does reset to 0 after a 360 degree turn
 * robot turning right is positive degrees
 */
public class Gyro {

    private static Gyro instance = null;
    AHRS ahrs;

    public static Gyro getInstance()
    {
        if (instance == null)
        {
            instance = new Gyro();
        }
        return instance;
    }

    public Gyro(){
        ahrs = new AHRS(AHRS.NavXComType.kMXP_SPI);
        ahrs.reset();
    }

    // gets current angle of the gyro
    public double getAngleDegrees(){
        //System.out.println("gyro yaw:" + ahrs.getAngle());
        return ahrs.getAngle();
    }

    // resets the gyro to 0?
    public void reset(){}

    public void setAngleAdjustment(){}

}


