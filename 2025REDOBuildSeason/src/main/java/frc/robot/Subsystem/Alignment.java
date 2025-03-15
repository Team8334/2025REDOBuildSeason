package frc.robot.Subsystem;

import frc.robot.Subsystem.FrontLimelight;
import frc.robot.Subsystem.Mecanum;
import frc.robot.Subsystem.Targeting;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Alignment implements Subsystem{

    private static Alignment instance = null;

    private FrontLimelight limelight;
    private Mecanum mecanum;
    private Targeting targeting;
    private double x;
    private double area;
    private double areaConstant;

    public Alignment(){
        SubsystemManager.registerSubsystem(this);
        
        limelight = FrontLimelight.getInstance();
        mecanum = Mecanum.getInstance();
        targeting = Targeting.getInstance();
    }

    public static Alignment getInstance() {
        if (instance == null) {
            instance = new Alignment();
        }
        return instance;
    }

    public void alignLeft(String target){
        if(x > -23){
            mecanum.driveWithSpeed(0.0, targeting.frontLockOnX(target, -23, -(areaConstant)),0.0);
        }
        else{
            mecanum.driveWithSpeed(0.4,0,0);
        }
    }

    public void alignRight(String target){ //desired seems to be 23 for Michela
        if(x < 23){
            mecanum.driveWithSpeed(0.0, targeting.frontLockOnX(target, 23, areaConstant),0.0);
        }
        else{
            mecanum.driveWithSpeed(0.4,0,0);
        }
    }

    public void alignXAngle(String target){
        if(x != 0){
            mecanum.driveWithSpeed(0, targeting.frontLockOnX(target, 0, 0), targeting.frontAngleAlign(target));
        }
        else{
            mecanum.driveWithSpeed(0, 0, 0);
            System.out.println("x = 0");
        }
        
    }

    public void alignAngle(String target){
        mecanum.driveWithSpeed(0, 0, targeting.frontAngleAlign(target));
    }

    public void driveTo(String target){
        if(area > 8 && Math.abs(x) <= 5 && Math.abs(limelight.getTargetRotation()) <= 0.004){
            mecanum.driveWithSpeed(0, 0, 0);
            System.out.println("Alignment complete");
        }
        else{
            mecanum.driveWithSpeed(targeting.frontFollow(target, 8), targeting.frontLockOnX(target, 0, 0), targeting.frontAngleAlign(target));
        }
    }

    public boolean isEnabled(){
        return true;
    }

    public String getName(){
        return "alignment";
    }

    public void initialize(){

    }

    public void log(){
    }

    public void update(){
        x = limelight.getX();
        area = limelight.getArea();
        areaConstant = limelight.getArea()/3;
        SmartDashboard.putNumber("Area constant", areaConstant);
    }
}
