package frc.robot.Devices;

import com.studica.frc.AHRS;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;

import frc.robot.Subsystem.Mecanum;

public class PositionEstimator {
    
    private static PositionEstimator instance = null;
    AHRS ahrs;

    private Mecanum mDrive;

    public static PositionEstimator getInstance()
    {
        if (instance == null)
        {
            instance = new PositionEstimator();
        }
        return instance;
    }

    public PositionEstimator(){

    }

    // public double getDistance(){
    //     return ((mDrive.() + mDrive.()) / 2);
    // }
}
