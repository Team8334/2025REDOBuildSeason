package frc.robot.Auto.Actions;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystem.Mecanum;
import frc.robot.Subsystem.Targeting;
import frc.robot.Subsystem.FrontLimelight;
import frc.robot.Subsystem.Mecanum;

public class FrontLockOnAction implements Actions
{
    private double forward;
    private double rotation;

    private double toleranceDistance = 0.0025;

    private Mecanum mecanum;

    private Targeting targeting;
    private FrontLimelight limelight;
    private String target;
    private boolean driveTo;

    Timer timer;
    double seconds;
    double neededArea;

    public FrontLockOnAction(String target, boolean driveTo, double seconds)
    {
        this.driveTo = driveTo;
        this.target = target;
        targeting = Targeting.getInstance();
        limelight = FrontLimelight.getInstance();
        mecanum = Mecanum.getInstance();

        forward = targeting.frontFollow(target);
        rotation = targeting.frontLockOn(target);
                                        
        limelight.setPipeline(0);

        this.seconds = seconds;
    }

    @Override
    public void start()
    {
        timer = new Timer();
        timer.start();
        SmartDashboard.putString("Current Action", "FrontLockOnAction Started");
    }

    @Override
    public void update()
    {
        if ((limelight.findTagName() != "Unknown") && driveTo && limelight.getPipeline() == 0)
        {
            mecanum.drive(forward, 0, rotation);

        }
        else if ((limelight.findTagName() != "Unknown") && !driveTo && limelight.getPipeline() == 0)
        {
            mecanum.drive(0.0, 0, rotation);
        }
        else
        {
            mecanum.drive(0, 0, 0);
        }
        SmartDashboard.putNumber("forward", forward);
        SmartDashboard.putNumber("rotation", rotation);
    }

    @Override
    public boolean isFinished()
    {
        if (timer.get() >= seconds) 
        {
            return true;
        } 
        else 
        {
            return false;
        }
    }

    @Override
    public void done()
    {
        mecanum.drive(0, 0, 0);
    }

}

