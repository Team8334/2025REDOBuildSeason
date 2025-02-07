package frc.robot.Devices;

import com.studica.frc.AHRS;

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

    public double getAngleDegrees(){
        return ahrs.getAngle();
    }

}


/*
 * Gyro want:
 * current degrees = 0
 * turn until hit 90 degrees 
 * then stop
 */
