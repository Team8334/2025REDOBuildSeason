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

    public Gyro(){
        ahrs = new AHRS(AHRS.NavXComType.kMXP_SPI);
        ahrs.reset();
    }

    public double getAngleDegrees(){
        System.out.println("gyro yaw:" + ahrs.getAngle());
        return ahrs.getAngle();
    }

}


