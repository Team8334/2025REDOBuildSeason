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

    public void alignLeft(){
        alignmentTimer.restart();

        if(alignmentTimer.get() <= 2 && x > -25){
            mecanum.drive(0.0,-0.25,0.0);
        }
        else{
            alignmentTimer.stop();
            mecanum.drive(0,0,0);
            System.out.println("Alignment complete");
        }
    }

    public void alignRight(){
        alignmentTimer.restart();

        if(alignmentTimer.get() <= 2 && x < 25){
            mecanum.drive(0.0,0.25,0.0);
        }
        else{
            alignmentTimer.stop();
            mecanum.drive(0,0,0);
            System.out.println("Alignment complete");
        }
    }

    public void alignReef(){
        alignmentTimer.restart();

        if(alignmentTimer.get() <= 5 || area < 25){
            mecanum.drive(targeting.frontFollow("reef"), 0, targeting.frontLockOn("reef"));
        }
        else{
            alignmentTimer.stop();
            mecanum.drive(0, 0, 0);
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
