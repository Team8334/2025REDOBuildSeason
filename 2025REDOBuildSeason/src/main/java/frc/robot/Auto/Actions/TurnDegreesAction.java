package frc.robot.Auto.Actions;

import frc.robot.Subsystem.Mecanum;
import frc.robot.Devices.Gyro;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* 
 * This action turns the robot a certain number of degrees
 * for a certain number of seconds
 * negative is left and positive is right
 * May immplement pid at some point
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

    private PIDController PID;
    private final double kp = 0.01;
    private final double ki = 0.013;
    private final double kd = 0.002;
    private double toleranceDegrees;
     
    public TurnDegreesAction(double degrees, double seconds) 
    {   
        this.seconds = seconds;
        mDrive = Mecanum.getInstance();
        gyro = Gyro.getInstance();
        desiredDegrees = degrees; // this is how much we want to turn
        PID = new PIDController(kp, ki, kd);
        PID.enableContinuousInput(-180, 180);
    }
    
    @Override
    public void start()
    {
        currentDegrees = gyro.getAngleDegrees();
        targetDegrees = (desiredDegrees + currentDegrees); // this is how much in total degrees we need to turn
        timer = new Timer();
        timer.start();
        PID.setSetpoint(targetDegrees);
        PID.setTolerance(toleranceDegrees);
    }
    
    @Override
    public void update()
    {
        SmartDashboard.putNumber("currentDegrees: ", currentDegrees);
        currentDegrees = gyro.getAngleDegrees(); // gets the current degrees     
            //turn = (targetDegrees - currentDegrees)/ 180; //the power of the turn, divided to make power less OLD CODE THAT WORKS
        turn = PID.calculate(gyro.getAngleDegrees());
        mDrive.drive(0, 0, turn); // no forward, no strafe, only rotation
    }

    @Override
    public boolean isFinished()
    {
        if (targetDegrees == currentDegrees || timer.get() >= seconds){
            return true;
        }
        else{
            return false;                                                                                                                                                                                                                                                                                                       // Will you make it across the starting line?
        }
    }
    
    
    @Override
    public void done()
    {
        System.out.println("currentDegrees: " + currentDegrees);
        SmartDashboard.putString( "Current Action", "TurnDegreesAction Ended");
        mDrive.drive(0, 0, 0);
    }
}