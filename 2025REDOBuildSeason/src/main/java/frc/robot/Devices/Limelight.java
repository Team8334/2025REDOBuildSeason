package frc.robot.Devices;

import frc.robot.Interfaces.Vision;
import frc.robot.Interfaces.Devices;
import frc.robot.Data.Debug;
import frc.robot.Data.ExternalLibraries.LimelightHelpers;
import frc.robot.Data.ExternalLibraries.LimelightHelpers.LimelightResults;
import frc.robot.Data.ExternalLibraries.LimelightHelpers.LimelightTarget_Fiducial;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.math.util.Units;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight implements Vision, Devices {

    int pipeline;
    int targetID;
    int limelightID;

    double goalHeightMeters;

    // String limelightName; // Removed: Not needed, use tableName

    String tableName; // Now this is the primary name

    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta;
    NetworkTableEntry tl;
    NetworkTableEntry ledMode;

    double x;
    double y;
    double area;
    double l;
    double[] targetPose;
    double[] robotPose;
    Pose3d targetPose3d;
    double targetPoseX;
    double targetRotationRadians;
    int alliancePipeline;
    Rotation3d targetRotation3d;

    String limelightName;

    private NetworkTable table;

    // how many degrees back is your limelight rotated from perfectly vertical? NEED VALUE FOR ROBOT
    protected double limelightMountAngleDegrees = 0;

    // distance from the center of the Limelight lens to the floor NEED VALUE FOR ROBOT
    protected double limelightLensHeightMeters = 0.2;

    private String alliance = "Red";

    public Limelight(String limelightName, int port, String tableName, int limelightID) {
        this.limelightName = limelightName;
        this.limelightID = limelightID;
        this.tableName = tableName;

        table = NetworkTableInstance.getDefault().getTable(tableName);
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        tl = table.getEntry("tl");
        ledMode = table.getEntry("ledMode");

        ledMode.setDouble(0); // 0=default; 1=off; 2=blinking; 3 = on - MUST BE DOUBLE
    }

    public String getName() {
        return "Limelight" + limelightID;
    }

    public void setPipeline(int pipeline) {
        this.pipeline = pipeline;
        table.getEntry("pipeline").setDouble(pipeline); //MUST BE DOUBLE
        //LimelightHelpers.setPipelineIndex(tableName, pipeline); //If not using the local instances
    }

    public int getPipeline() {
        return pipeline;
    }

    public int getId() { // finds April Tag ID.
        return (int) LimelightHelpers.getFiducialID(tableName);
    }

    public double getX() {
        return x;
    }

    public double getTargetRotation() {
        return targetRotationRadians;
    }

    public double getArea() {
        return area;
    }

    public void setAlliance(String alliance) {
        this.alliance = alliance;
    }

    public String findTagName() {
        switch (getId()) {
            case 12, 13: {
                goalHeightMeters = 1.35;
                return alliance.equals("Blue") ? "Coral Station" : "Opponent's Coral Station";
            }
            case 4, 14: {
                goalHeightMeters = 1.78;
                return alliance.equals("Blue") ? "Barge" : "Opponent's Barge"; // Note: This is above the MIDDLE CAGE
            }
            case 3: {
                goalHeightMeters = 1.17;
                return alliance.equals("Blue") ? "Processor" : "Opponent's Processor";
            }
            case 17, 18, 19, 20, 21, 22: {
                goalHeightMeters = 0.17;
                return alliance.equals("Blue") ? "Reef" : "Opponent's Reef";
            }
            case 5, 15: {
                goalHeightMeters = 1.78;
                return alliance.equals("Red") ? "Barge" : "Opponent's Barge"; // Note: This is above the MIDDLE CAGE
            }
            case 16: {
                goalHeightMeters = 1.17;
                return alliance.equals("Red") ? "Processor" : "Opponent's Processor";
            }
            case 6, 7, 8, 9, 10, 11: {
                goalHeightMeters = 0.17;
                return alliance.equals("Red") ? "Reef" : "Opponent's Reef";
            }
            case 1, 2: {
                goalHeightMeters = 1.35;
                return alliance.equals("Red") ? "Coral Station" : "Opponent's Coral Station";
            }
            default: {
                return "Unknown";
            }
        }
    }
    
    public double getDesiredAngleDegrees(int targetTagID) {
        switch (targetTagID) {
            case 1, 9, 13, 22: {
                return 120;
            }
            case 2, 11, 12, 20: {
                return 240;
            }
            case 3, 16: {
                return 270;
            }
            case 4, 5, 7, 14, 15, 18: {
                return 0;
            }
            case 6, 19: {
                return 300;
            }
            case 8, 17: {
                return 60;
            }
            case 10, 21: {
                return 180;
            }
            default: {
                return 0;
            }
        }
    }

    public double getDesiredAngleRadians(String target){
        return 0;
    }

    public boolean isOperational() {
        return tableName != null; // Use tableName for operational check
    }

    public void limelightUpdate() {
        x = tx.getDouble(0.0); //only negative for MasterS
        y = ty.getDouble(0.0);
        area = ta.getDouble(0.0);
        l = tl.getDouble(0.0);
        targetPose = LimelightHelpers.getTargetPose_RobotSpace(limelightName);
        robotPose = LimelightHelpers.getBotPose_TargetSpace(tableName);
        targetPose3d = LimelightHelpers.getTargetPose3d_RobotSpace(limelightName);
        targetPoseX = targetPose3d.getX(); //only negative for MasterS
        targetRotation3d = LimelightHelpers.getTargetPose3d_RobotSpace(limelightName).getRotation();
        targetRotationRadians = targetRotation3d.getZ()-.0295;//subtract constant for accurate measurement. ? for Michela, .008 for MasterS
    }

    public void logToSmartDashboard() {

        if(Debug.debug){
        SmartDashboard.putNumber("Limelight" + limelightID + "/Target X", x);
        SmartDashboard.putNumber("Limelight" + limelightID + "/Target Y", y);
        SmartDashboard.putNumber("Limelight" + limelightID + "/Target Area", area);
        SmartDashboard.putNumber("Limelight" + limelightID + "/Latency", l);
        SmartDashboard.putString("Limelight" + limelightID + "/Target Name", findTagName());
        SmartDashboard.putNumber("Limelight" + limelightID + "/Target X Robot Space", targetPoseX);
        SmartDashboard.putNumberArray("Limelight" + limelightID + "/Target Pose", targetPose);
        SmartDashboard.putNumber("Limelight" + limelightID + "/Angle", targetRotationRadians);
        SmartDashboard.putString("Alliance", alliance);
        }
    }
}