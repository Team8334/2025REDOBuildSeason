package frc.robot.Auto.Actions;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Subsystem.Mecanum;

public class DriveForDistanceAction implements Actions{
    private Timer timer;

    private double currentDistance; // current distance according to encoders
    private double desiredDistance; // how much we want to go
    private double targetDistance; // how far in total we need to go
    private double forward;
    private double toleranceDistance = 0.0025;
    private double seconds;

    private Mecanum mecanum;

    public DriveForDistanceAction(double distance, double seconds)
    {
        mecanum = Mecanum.getInstance();
        seconds = seconds;
        desiredDistance = distance;
    }

    @Override
    public void start()
    {
        timer = new Timer();
        timer.start();
        targetDistance = (currentDistance + desiredDistance);
        SmartDashboard.putString("Current Action", "DriveForDistanceAction Started");
    }

    @Override
    public void update()
    {
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
        mecanum.driveWithSpeed(0, 0, 0);
    }
}
