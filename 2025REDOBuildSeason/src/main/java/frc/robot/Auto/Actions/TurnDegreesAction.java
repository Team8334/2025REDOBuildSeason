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
    //private double targetDegrees = 2;
    private double desiredDegrees;
    private double turn;
    private double seconds;

    private Mecanum mecanum;
    private Gyro gyro;
    
     //plus is left 
     
    public TurnDegreesAction(double degrees, double seconds) 
    {
        this.seconds = seconds;
        mecanum = Mecanum.getInstance();
        gyro = Gyro.getInstance();
        desiredDegrees = degrees;
    }

    @Override
    public void start()
    {
        currentDegrees = gyro.getAngleDegrees();
        timer = new Timer();
        timer.start();
        //targetDegrees = (currentDegrees + desiredDegrees);
    }

    @Override
    public void update()
    {
        currentDegrees = gyro.getAngleDegrees();;
        turn = (currentDegrees + desiredDegrees);
        mecanum.drive(0, 0, turn);
        System.out.println("gyro yaw:" + gyro.getAngleDegrees());
        
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
        mecanum.drive(0, 0, 0);
    }
}
