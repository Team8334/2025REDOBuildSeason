package frc.robot.Auto.Actions;

//imported subsystems that help calculate turn
import frc.robot.Subsystem.Mecanum;
import frc.robot.Devices.Gyro;

// imported math things
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* 
 * This action turns the robot a certain number of degrees
 * for a certain number of seconds
 *  STILL MIGHT NEED ANOTHER WAY TO TURN FOR SCRIMMAGE
 */

public class TurnDegreesAction implements Actions{
    
    //variables
    Timer timer;
    private double currentDegrees = 0;
    private double desiredDegrees;
    private double turn;
    private double seconds;

    private Mecanum mDrive = null;
    private Gyro gyro;
    
     //plus is left 
     
    public TurnDegreesAction(double degrees, double seconds) 
    {   
        this.seconds = seconds;
        mDrive = Mecanum.getInstance();
        gyro = Gyro.getInstance();
        desiredDegrees = degrees;
    }

    @Override
    public void start()
    {
        //currentDegrees = gyro.getAngleDegrees();
        timer = new Timer();
        timer.start();
        //targetDegrees = (currentDegrees + desiredDegrees);
    }

    @Override
    public void update()
    {
        System.out.println(mDrive);
        //System.out.println("desiredDegrees: " + desiredDegrees);
        currentDegrees = gyro.getAngleDegrees();;
        //System.out.println("currentDegrees: " + currentDegrees);
        turn = (currentDegrees + desiredDegrees);
        //System.out.println("turn is: " + turn);
        //mDrive.drive(0, 0, turn);
        //System.out.println("gyro yaw:" + gyro.getAngleDegrees());
        
    }

    @Override
    public boolean isFinished()
    {
        return (timer.get() >= seconds);
    }
    

    @Override
    public void done()
    {
        SmartDashboard.putString( "Current Action", "TurnDegreesAction Ended");
        mDrive.drive(0, 0, 0);
    }
}
