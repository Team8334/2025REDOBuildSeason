package frc.robot.Devices;

import frc.robot.Interfaces.Vision;
import frc.robot.Interfaces.Devices;
import frc.robot.Data.ExternalLibraries.LimelightHelpers;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight extends LimelightHelpers implements Vision, Devices{

    int pipeline;
    int targetID;
    int limelightID;

    double goalHeightMeters;

    String limelightName;

    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta;
    NetworkTableEntry tl;

    double x;
    double y;
    double area;
    double d;
    double l;

    private NetworkTable table;
    private String tableName;

    // how many degrees back is your limelight rotated from perfectly vertical? NEED VALUE FOR ROBOT
    protected double limelightMountAngleDegrees = 0; 

    // distance from the center of the Limelight lens to the floor NEED VALUE FOR ROBOT
    protected double limelightLensHeightMeters = 0.2; 

	private String alliance = "Red";
    
    public Limelight(String limelightName, int port, String tableName, int limelightID){
        this.limelightName = limelightName;
        this.limelightID = limelightID;
        this.tableName = tableName;

        table = NetworkTableInstance.getDefault().getTable(tableName);
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        tl = table.getEntry("tl");
        
        table.getEntry("ledMode").setNumber(1); //0=default; 1=off; 2=blinking; 3 = on
    }
    
    
    public String getName() {
        return "Limelight" + limelightID;
    }

    public void setPipeline(int pipeline)
    {
        this.pipeline = pipeline;
        table.getEntry("pipeline").setNumber(pipeline);
    }

    public int getPipeline()
    {
        return pipeline;
    }

    public int getId() //finds April Tag ID. This is a variable, not a function.
    {
        return (int)LimelightHelpers.getFiducialID(tableName);
    }

    public double getX(){
        return x;
    }

    public void setAlliance(String alliance)
    {
        this.alliance = alliance;
    }

    public String findTagName()
    {
        switch (getId())
        {
            case 12, 13 -> {
                goalHeightMeters = 1.35; 
                return alliance.equals("Blue") ? "Coral Station" : "Opponent's Coral Station";
            }
            case 4, 14 -> {
                goalHeightMeters = 1.78; 
                return alliance.equals("Blue") ? "Barge" : "Opponent's Barge"; //Note: This is above the MIDDLE CAGE
            }
            case 3 -> {
                goalHeightMeters = 1.17; 
                return alliance.equals("Blue") ? "Processor" : "Opponent's Processor";
            }
            case 17, 18, 19, 20, 21, 22 -> {
                goalHeightMeters = 0.17; 
                return alliance.equals("Blue") ? "Reef" : "Opponent's Reef";
            }
            case 5, 15 -> {
                goalHeightMeters = 1.78; 
                return alliance.equals("Red") ? "Barge" : "Opponent's Barge"; //Note: This is above the MIDDLE CAGE
            }
            case 16 -> {
                goalHeightMeters = 1.17; 
                return alliance.equals("Red") ? "Processor" : "Opponent's Processor";
            }
            case 6, 7, 8, 9, 10, 11 -> {
                goalHeightMeters = 0.17; 
                return alliance.equals("Red") ? "Reef" : "Opponent's Reef";
            }
            case 1, 2 -> {
                goalHeightMeters = 1.35; 
                return alliance.equals("Red") ? "Coral Station" : "Opponent's Coral Station";
            }
            default -> {
                return "Unknown";
            }
        }
    }
    
    public boolean isOperational(){
        if(limelightName == null){
            return false;
        }
        else{
            return true;
        }
    }

    public double getDistanceFromTarget()
    {
        double targetOffsetAngleVertical = ty.getDouble(0.0);
    
        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngleVertical;
        double angleToGoalRadians = angleToGoalDegrees * (Math.PI / 180.0);
    
        //calculate distance
        return (goalHeightMeters - limelightLensHeightMeters) / Math.tan(angleToGoalRadians);
    }

    public void limelightUpdate() {
        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        area = ta.getDouble(0.0);
        l = tl.getDouble(0.0);
        d = getDistanceFromTarget();
    }

    public void logtoSmartDashboard() {
        SmartDashboard.putNumber("Limelight" + limelightID +"/Target X", x);
        SmartDashboard.putNumber("Limelight" + limelightID +"/Target Y", y);
        SmartDashboard.putNumber("Limelight" + limelightID +"/Target Area", area);
        SmartDashboard.putNumber("Limelight" + limelightID +"/Latency", l);
        SmartDashboard.putNumber("Limelight" + limelightID +"/Target Distance", d);
        SmartDashboard.putString("Limelight" +limelightID +"/Target Name", findTagName());
    }

    
}
