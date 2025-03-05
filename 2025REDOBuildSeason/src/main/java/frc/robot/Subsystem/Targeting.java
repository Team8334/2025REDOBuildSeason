package frc.robot.Subsystem;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.Devices.Limelight;
import frc.robot.Subsystem.FrontLimelight;
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

    private PIDController xPID = new PIDController(.15, 0, 0.005);
    private PIDController areaPID = new PIDController(1, 0, 0);
    private PIDController rotationPID = new PIDController(.1, 0, 0.005);

    private Gyro gyro = Gyro.getInstance();

    double currentAngle = (gyro.getAngleDegrees()*(Math.PI/180));
    double desiredAngle;
    int pipeline;

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
        if(alliance == "Red"){
            pipeline = 0;
        }
        else{
            pipeline = 1;
        }
        frontLimelight.setPipeline(pipeline);
    }

    public double frontLockOnX(String target, double desiredX) //0 as Desired X for aligning with the middle
    {
        frontTags = frontLimelight.findTagName();
        if (frontTags == target){
            frontLockOnState = "Locking on to target";
            return (xPID.calculate(frontLimelight.getX(), desiredX));//25 is an arbitrary speed divisor. Increase/decrease as needed.
            System.out.println(xPID.calculate(frontLimelight.getX(), desiredX));
        }
        else{
            frontLockOnState = "Cannot see target";
            return 0.0;
        }
    }

    public double frontAngleAlign(String target){
        desiredAngle = ((Math.abs(frontLimelight.getTargetRotation()-180))*(Math.PI/180));
        frontTags = frontLimelight.findTagName();
        if (frontTags == target){
            frontLockOnState = "Locking on to target";
            return (rotationPID.calculate(currentAngle, desiredAngle));
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
            return -(areaPID.calculate(frontLimelight.getArea(), desiredArea) / 50);//50 is an arbitrary speed divisor. Increase/decrease as needed.
        }
        else{
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
        SmartDashboard.putNumber("Current Angle", currentAngle);
        SmartDashboard.putNumber("Desired Angle", desiredAngle);
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
