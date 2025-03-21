package frc.robot.Auto.Actions;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystem.Mecanum;
import frc.robot.Subsystem.FrontLimelight;
import frc.robot.Subsystem.Mecanum;
import frc.robot.Data.Debug;
import frc.robot.Subsystem.Alignment;

public class FrontAlignAction implements Actions
{
    private Mecanum mecanum;

    private Alignment alignment;
    private FrontLimelight frontLimelight;
    private String target;
    private String direction;

    Timer timer;
    double seconds;

    /**
     * Run code once when the action is started, for setup
     */
    public FrontAlignAction(String target, String direction, double seconds)
    {
        this.target = target;
        this.direction = direction;
        alignment = Alignment.getInstance();
        frontLimelight = FrontLimelight.getInstance();
        mecanum = Mecanum.getInstance();
        frontLimelight.setPipeline(0);

        this.seconds = seconds;
    }

    @Override
    public void start()
    {
        timer = new Timer();
        timer.start();

        if(Debug.debug){
        SmartDashboard.putString("Current Action", "FrontLockOnAction Started");
        }
    }

    /**
     * Called by runAction in AutoModeBase iteratively until isFinished returns
     * true. Iterative logic lives in this
     * method
     */
    @Override
    public void update()
    {
        if ((frontLimelight.findTagName() != "Unknown") && direction == "Left" && frontLimelight.getPipeline() == 0)
        {
            alignment.alignLeft(target);
        }
        if((frontLimelight.findTagName() != "Unknown") && direction == "Right" && frontLimelight.getPipeline() == 0)
        {
            alignment.alignRight(target);
        }
        else
        {
            mecanum.driveWithSpeed(0, 0, 0);
        }
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
        mecanum.driveWithSpeed(0, 0, 0);
    }
}