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
        super("Front Limelight", 0, "Front Limelight", 1); //figure out actual port
        this.limelightMountAngleDegrees = 25.0;
        this.limelightLensHeightMeters = 20.0;
    }

    public String getFrontTargetName(){
        return findTagName();
    }

    public void update(){

    };

    public void initialize(){

    };

    public void log(){

    };

    public boolean isEnabled(){
        return true;
    };

    public String getName(){
        return "Front Limelight";
    };
}

