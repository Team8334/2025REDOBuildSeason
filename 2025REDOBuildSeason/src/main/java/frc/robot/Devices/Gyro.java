package frc.robot.Devices;

import com.studica.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;

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
    }

    public double getAngleDegrees(){
        System.out.println("gyro yaw:" + ahrs.getAngle());
        return ahrs.getAngle();
    }

}


/*
 * Gyro want:
 * current degrees = 0
 * turn until hit 90 degrees 
 * then stop
 */
