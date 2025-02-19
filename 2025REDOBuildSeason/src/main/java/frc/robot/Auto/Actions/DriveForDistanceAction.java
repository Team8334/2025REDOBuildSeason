
package frc.robot.Auto.Actions;

import frc.robot.Subsystem.Mecanum;
import frc.robot.Devices.PositionEstimator;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * This action moves the robot forward a certain distance
 * for a chosen number of seconds
 * Need encoders from newest Mecanum code? Put in position estimator
 * DO NO USE FOR SCRIMMAGE
 */
public class DriveForDistanceAction implements Actions
{
    Timer timer;
    private double currentDistance;
    private double desiredDistance;
    private double targetDistance;
    private double forward;
    private double speed;
    private double seconds;

    private PositionEstimator position;
    private Mecanum mDrive;

    public DriveForDistanceAction(double distance, double seconds) //double speed
    {
        this.seconds = seconds;
        desiredDistance = distance;
        mDrive = Mecanum.getInstance();
        position = PositionEstimator.getInstance();
        //this.speed = speed;
    }

    @Override
    public void start()
    {
        timer = new Timer();
        timer.start();
        //currentDistance = position.getDistance();
        targetDistance = (currentDistance + desiredDistance);
        SmartDashboard.putString("Current Action", "DriveForDistanceAction Started");
    }

    @Override
    public void update()
    {
        mDrive.drive(speed, 0, 0);
        SmartDashboard.putNumber("targetDistance", targetDistance);
        SmartDashboard.putNumber("currentDistance", currentDistance);
        SmartDashboard.putNumber("forward", forward);

    }

    @Override
    public boolean isFinished()
    {
        if (currentDistance == targetDistance || timer.get() >= seconds){

            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void done()
    {
        mDrive.drive(0, 0, 0);
    }
}
