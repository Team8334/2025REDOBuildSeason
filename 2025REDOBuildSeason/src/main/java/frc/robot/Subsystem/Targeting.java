package frc.robot.Subsystem;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.Devices.Limelight;
import frc.robot.Subsystem.FrontLimelight;
import frc.robot.Subsystem.Mecanum;

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

    private PIDController aprilTagXPID = new PIDController(1, 0, 0);
    private PIDController aPID = new PIDController(1, 0, 0);
    private PIDController noteXPID = new PIDController(1, 0, 0);

    Limelight limelight;
    FrontLimelight frontLimelight;
    Mecanum mecanum;

    String alliance;
    String target = "ALL";
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

    /*public double aprilTagLockOn()
    {
        frontLimelight.setPipeline(0);
        frontTags = frontLimelight.findTagName();
    }

    public double frontAprilTagLockOn(String target)
    {
       
        limelightFront.setPipeline(0);
        frontTags = limelightFront.findTagName();
        if (frontTags == target)
        {
            return (aprilTagXPID.calculate(limelightFront.x, 0) / 150.0);
        }
        else
        {
            return 0.0;
        }
    }

    public double follow() // Setting "forward" in DriveBase.drive in control as this function will cause the robot to follow the target. 
                           // USE AT OWN RISK. Feel free to increase the speed divisor value to make it even slower.
    {
        return (aPID.calculate(limelight.area, 25) / 50);
    }*/

    public void log()
    {
        SmartDashboard.putString("AprilTag Target (Front)", frontLimelight.findTagName());
        SmartDashboard.putString("AprilTag Target (Alliance)", alliance);
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