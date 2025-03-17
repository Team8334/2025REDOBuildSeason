package frc.robot.Auto.Actions;

//imported subsystems that help calculate turn
import frc.robot.Subsystem.Mecanum;
import frc.robot.Data.Debug;
import frc.robot.Devices.Gyro;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* 
 * This action turns the robot a certain number of degrees
 * for a certain number of seconds
 * negative is left and positive is right
 * May implement pid at some point
 */

public class TurnDegreesAction implements Actions{
    
    //variables
    Timer timer;
    private double currentDegrees;
    private double desiredDegrees;
    private double turn;
    private double seconds;
    private double targetDegrees;

    private Mecanum mDrive = null;
    private Gyro gyro;
     
    public TurnDegreesAction(double degrees, double seconds) {   
        this.seconds = seconds;
        mDrive = Mecanum.getInstance();
        gyro = Gyro.getInstance();
        desiredDegrees = degrees; // this is how much we want to turn
    }
    
    @Override
    public void start()
    {
        currentDegrees = gyro.getAngleDegrees();
        targetDegrees = (desiredDegrees + currentDegrees); // this is how much in total degrees we need to turn
        timer = new Timer();
        timer.start();
    }
    
    @Override
    public void update(){
        currentDegrees = gyro.getAngleDegrees(); // gets the current degrees

        if(Debug.debug){
        SmartDashboard.putNumber("targetDegrees ", targetDegrees);
        SmartDashboard.putNumber("desiredDegrees ", desiredDegrees);
        SmartDashboard.putNumber("currentDegreese ", currentDegrees);
        }
        
        turn = (targetDegrees - currentDegrees)/ 180; //the power of the turn, divided to make power less
        
        mDrive.driveWithSpeed(0, 0, turn); // no forward, no strafe, only rotation
        
        if(Debug.debug){
        SmartDashboard.putNumber("turnDegreesAction/speed " , turn);
        }
    }

    @Override
    public boolean isFinished(){
        return targetDegrees == currentDegrees || timer.get() >= seconds;
    }
    
    
    @Override
    public void done(){

        if (Debug.debug){
        SmartDashboard.putString( "Current Action", "TurnDegreesAction Ended");
        SmartDashboard.putNumber("turnDegreesAction/speed" , turn);
        }
        
        mDrive.driveWithSpeed(0, 0, 0);
    }
}