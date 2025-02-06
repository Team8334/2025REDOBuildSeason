
package frc.robot.Auto.Actions;

//imported Actions
import frc.robot.Auto.Actions.Actions;
import frc.robot.Auto.AutoMissionChooser;

//imported subsystems that help calculate turn
import frc.robot.Subsystem.Mecanum;
import frc.robot.Devices.Gyro;
//THIS COULD CHANGE TO SOME OTHER FORM OF POSITION ESTIMATION
//import frc.robot.Subsystem.PositionEstimation;

//DO WE NEED THIS?
//import javax.swing.text.Position;

// imported math things
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* This action turns the robot a certain number of degrees
 * for a certain number of seconds
 * MIGHT NEED ANOTHER WAY TO TURN FOR SCRIMMAGE
 */

public class TurnDegreesExperimenting implements Actions{
    
    //variables
    Timer timer;
    private double currentDegrees = 0;
    private double targetDegrees;
    private double desiredDegrees;
    private double turn;
    private double toleranceDegrees = 0.0025;
    private double endAfterSeconds;

    private Mecanum mecanum;
    private Gyro gyro;
    private Gyro position;

    private gyro.getAngle();
    
     //plus is left 
     
    public TurnDegreesExperimenting(double degrees, double seconds) 
    {
        this.degrees = degrees;
        this.seconds = seconds
    }

    @Override
    public void start()
    {
        timer = new Timer();
        timer.start();
        targetDegrees = (currentDegrees + desiredDegrees);
    }

 

    @Override
    public void update()
    {
        gyro.getAngle() = targetDegrees; 
            return true;
        }
        
    }

    @Override
    public boolean isFinished()
    {
        return (timer.get() >= endAfterSeconds);
    }
    

    @Override
    public void done()
    {
        SmartDashboard.putString( "Current Action", "TurnDegreesAction Ended");
        mecanum.drive(0, 0, 0);
    }

    
}
