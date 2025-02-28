package frc.robot.Auto.Actions;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Subsystem.Mecanum;
//import frc.robot.Subsystem.PositioinEstimation;

public class DriveForDistanceAction implements Actions{
        Timer timer;
    private double currentDistance; // current distance according to encoders
    private double desiredDistance; // how much we want to go
    private double targetDistance; // how far in total we need to go
    private double forward;
    private double toleranceDistance = 0.0025;
    private double seconds;

    private Mecanum mecanum;
    //private PositionEstimation position;

    public DriveForDistanceAction(double distance, double seconds)
    {
        mecanum = Mecanum.getInstance();
        //position = PositionEstimation.getInstance();
        seconds = seconds;
        desiredDistance = distance;
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
        //currentDistance = position.getDistance();
        forward = (targetDistance - currentDistance) / 128;
        mecanum.driveWithSpeed(forward, 0, 0);
        SmartDashboard.putNumber("targetDistance", targetDistance);
        SmartDashboard.putNumber("currentDistance", currentDistance);
        SmartDashboard.putNumber("forward", forward);

    }

    @Override
    public boolean isFinished()
    {
        return (timer.get() >= seconds);
    }

    @Override
    public void done()
    {
        SmartDashboard.putString("Current Action", "DriveForDistanceAction Ended");
        mecanum.drive(0, 0, 0);
    }
}
