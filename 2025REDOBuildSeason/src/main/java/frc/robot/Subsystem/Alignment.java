package frc.robot.Subsystem;

import frc.robot.Subsystem.FrontLimelight;
import frc.robot.Subsystem.Mecanum;
import frc.robot.Subsystem.Targeting;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Alignment implements Subsystem{

    private static Alignment instance = null;

    private FrontLimelight limelight;
    private Mecanum mecanum;
    private Targeting targeting;
    private double x;
    private double area;
    private Timer alignmentTimer;

    public Alignment(){
        SubsystemManager.registerSubsystem(this);
        
        limelight = FrontLimelight.getInstance();
        mecanum = Mecanum.getInstance();
        targeting = Targeting.getInstance();

        x = limelight.getX();
        area = limelight.getArea();
    }

    public static Alignment getInstance() {
        if (instance == null) {
            instance = new Alignment();
        }
        return instance;
    }

    public void alignLeft(String target){
        alignmentTimer.restart();

        if(alignmentTimer.get() <= 2 && x > -25){
            mecanum.driveWithSpeed(0.0, targeting.frontLockOn(target, -25),0.0);
        }
        else{
            alignmentTimer.stop();
            mecanum.driveWithSpeed(0,0,0);
            System.out.println("Alignment complete");
        }
    }

    public void alignRight(String target){
        alignmentTimer.restart();

        if(alignmentTimer.get() <= 2 && x < 25){
            mecanum.driveWithSpeed(0.0, targeting.frontLockOn(target, 25),0.0);
        }
        else{
            alignmentTimer.stop();
            mecanum.driveWithSpeed(0,0,0);
            System.out.println("Alignment complete");
        }
    }

    public void align(String target){
        alignmentTimer.restart();

        if(alignmentTimer.get() <= 5 || area < 25){
            mecanum.driveWithSpeed(0, targeting.frontLockOn(target, 0), targeting.frontAngleAlign(target));
        }
        else{
            alignmentTimer.stop();
            mecanum.driveWithSpeed(0, 0, 0);
            System.out.println("Alignment complete");
        }
    }

    public void driveTo(String target){
        alignmentTimer.restart();

        if(alignmentTimer.get() <= 5 || area < 25){
            mecanum.driveWithSpeed(targeting.frontFollow(target, 25), 0, 0);
        }
        else{
            alignmentTimer.stop();
            mecanum.driveWithSpeed(0, 0, 0);
            System.out.println("Alignment complete");
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
        SmartDashboard.putNumber("Alignment Timer", alignmentTimer.get());
    }

    public void update(){

    }
}
