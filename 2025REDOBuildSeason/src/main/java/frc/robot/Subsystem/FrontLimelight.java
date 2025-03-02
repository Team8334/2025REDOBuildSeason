package frc.robot.Subsystem;

import frc.robot.Devices.Limelight;

public class FrontLimelight extends Limelight implements Subsystem{
    
    private static FrontLimelight instance = null;

    public static FrontLimelight getInstance()
    {
        if (instance == null)
        {
            instance = new FrontLimelight();
        }
        return instance;
    }

    private FrontLimelight()
    {   
        super("limelight-front", 0, "limelight-front", 1); //figure out actual port
        this.limelightMountAngleDegrees = 0;
        this.limelightLensHeightMeters = 0;
        SubsystemManager.registerSubsystem(this);
    }

    public String getFrontTargetName(){
        return findTagName();
    }

    public void update(){
        limelightUpdate();
    };

    public void initialize(){

    };

    public void log(){
        logToSmartDashboard();
    };

    public boolean isEnabled(){
        return true;
    };

    public String getName(){
        return "Front Limelight";
    };
}

