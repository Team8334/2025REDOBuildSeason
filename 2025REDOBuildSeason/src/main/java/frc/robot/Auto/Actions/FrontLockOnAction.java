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

    /**
     * Run code once when the action is started, for setup
     */
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

    /**
     * Called by runAction in AutoModeBase iteratively until isFinished returns
     * true. Iterative logic lives in this
     * method
     */
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

    /**
     * Returns whether or not the code has finished executaprilTagLockOnementing
     * this interface, this method is used by
     * the runAction method every cycle to know when to stop running the action
     *
     * @return boolean
     */
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

    /**
     * Run code once when the action finishes, usually for clean up
     */
    @Override
    public void done()
    {
        mecanum.drive(0, 0, 0);
    }

}

