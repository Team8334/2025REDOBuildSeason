package frc.robot.Subsystem;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.Devices.Limelight;
import frc.robot.Subsystem.FrontLimelight;
import frc.robot.Data.Debug;
import frc.robot.Devices.Gyro;

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

    private PIDController xPID = new PIDController(.0135, 0, 0); 
    private PIDController areaPID = new PIDController(.035, 0, 0);
    private PIDController rotationPID = new PIDController(10, 0, 0);

    private Gyro gyro = Gyro.getInstance();

    double currentAngle;
    int pipeline;

    Limelight limelight;
    FrontLimelight frontLimelight;
    double xCorrection;
    double angleCorrection;

    String alliance;
    String frontLockOnState = "Not locking on to target";
    String frontFollowState = "Not following target";
    String frontTags;

    public Targeting()
    {
        SubsystemManager.registerSubsystem(this);
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
        frontLimelight.setPipeline(0);
        xPID.setTolerance(0.3);
        rotationPID.setTolerance(0.05);
    }

    public double frontLockOnX(String target, double desiredX, double alignmentConstant) //0 as Desired X for aligning with the middle, 0 as alignmentConstant unless aligning with something
    {
        frontTags = frontLimelight.findTagName();
        xCorrection = xPID.calculate(frontLimelight.getX()+alignmentConstant, desiredX);
        if (frontTags == target){
            frontLockOnState = "Locking on to target";
            return xCorrection;
        }
        else{
            frontLockOnState = "Cannot see target";
            return 0.0;
        }
    }

    public double frontAngleAlign(String target){
        currentAngle = ((gyro.getAngleDegrees())*(Math.PI/180));
        frontTags = frontLimelight.findTagName();
        angleCorrection = rotationPID.calculate(currentAngle, frontLimelight.getDesiredAngleRadians());
        if (frontTags == target && frontLimelight.getX() < 15 && frontLimelight.getX() > -15){
            frontLockOnState = "Locking on to target";
            return angleCorrection;
        }
        else {
            frontLockOnState = "Cannot see target";
            return 0.0;
        }
    }

    public double frontFollow(String target, double desiredArea) // Setting "forward" in Mecanum.drive or Mecanum.driveWithSpeed as this function will cause the robot to follow the target. 
                           // USE AT OWN RISK. Feel free to increase the speed divisor value to make it even slower. 25 is a good area for going towards something
    {
        frontTags = frontLimelight.findTagName();
        if (frontTags == target){
            frontFollowState = "Following target";
            return -(areaPID.calculate(frontLimelight.getArea(), desiredArea));
        }
        else{
            frontFollowState = "Cannot see target";
            return 0.0;
        }
    }

    public void log()
    {
        if(Debug.debug){
        SmartDashboard.putString("AprilTag in sight (Front)", frontLimelight.findTagName());
        SmartDashboard.putString("Front Limelight Lock On", frontLockOnState);
        SmartDashboard.putString("Front Limelight Follow", frontFollowState);
        SmartDashboard.putString("Current alliance", alliance);
        }
    }

    public void update()
    {
        if(Debug.debug){
        SmartDashboard.putNumber("Targeting/Targeting Current Angle", currentAngle);
        SmartDashboard.putNumber("Targeting/Targeting Current X", frontLimelight.getX());
        SmartDashboard.putNumber("Targeting/X Correction", xCorrection);
        SmartDashboard.putNumber("Targeting/Angle Correction", angleCorrection);
        }
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
