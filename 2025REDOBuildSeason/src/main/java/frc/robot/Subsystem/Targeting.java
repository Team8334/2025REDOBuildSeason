package frc.robot.Subsystem;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.Devices.Limelight;
import frc.robot.Subsystem.FrontLimelight;

public class Targeting implements Subsystem // This class contains functions for finding and
                                            // locking onto elements of the field using
                                            // their April Tags.
{
    private static Targeting instance = null;

    public static Targeting getInstance()
    {
        if (instance == null)
        {
            instance = new Targeting();
        }
        return instance;
    }

    private PIDController xPID = new PIDController(1, 0, 0);
    private PIDController areaPID = new PIDController(1, 0, 0);

    Limelight limelight;
    FrontLimelight frontLimelight;

    String alliance;
    String frontLockOnState = "Not locking on to target";
    String frontFollowState = "Not following target";
    String frontTags;

    public Targeting()
    {
        frontLimelight = FrontLimelight.getInstance();
        try
        {
            alliance = DriverStation.getAlliance().orElseThrow(() -> new Exception("No alliance")).toString();
        }
        catch (Exception e)
        {
            // Handle the exception, for example:
            System.out.println("Exception occurred: " + e.getMessage());
        }
        frontLimelight.setAlliance(alliance);
    }

    public double frontLockOn(String target)
    {
        frontLimelight.setPipeline(0);
        frontTags = frontLimelight.findTagName();
        if (frontTags == target)
        {
            frontLockOnState = "Locking on to target";
            return (xPID.calculate(frontLimelight.getX(), 0) / 150.0);//150 is an arbitrary speed divisor. Increase/decrease as needed.
        }
        else
        {
            frontLockOnState = "Cannot see target";
            return 0.0;
        }
    }

    public double frontFollow(String target) // Setting "forward" in Mecanum.drive or Mecanum.driveWithSpeed as this function will cause the robot to follow the target. 
                           // USE AT OWN RISK. Feel free to increase the speed divisor value to make it even slower.
    {
        frontLimelight.setPipeline(0);
        frontTags = frontLimelight.findTagName();
        if (frontTags == target)
        {
            frontFollowState = "Following target";
            return (areaPID.calculate(frontLimelight.getArea(), 25) / 50);//50 is an arbitrary speed divisor. Increase/decrease as needed.
        }
        else
        {
            frontFollowState = "Cannot see target";
            return 0.0;
        }
    }

    public void log()
    {
        SmartDashboard.putString("AprilTag in sight (Front)", frontLimelight.findTagName());
        SmartDashboard.putString("Front Limelight Lock On", frontLockOnState);
        SmartDashboard.putString("Front Limelight Follow", frontFollowState);
        SmartDashboard.putString("Current alliance", alliance);
    }

    public void update()
    {

    }

    public void initialize(){

    }

    public boolean isEnabled(){
        return true;
    }

    public String getName(){
        return "Targeting";
    }
}