
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
import javax.swing.text.Position;

// imported math things
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* This action turns the robot a certain number of degrees
 * for a certain number of seconds
 * MIGHT NEED ANOTHER WAY TO TURN FOR SCRIMMAGE
 */

public class TurnDegreesAction implements Actions{
    
    //variables
    Timer timer;
    private double currentDegrees = 0;
    private double desiredDegrees;
    private double targetDegrees;
    private double turn;
    private double toleranceDegrees = 0.0025;
    private double endAfterSeconds;

    private Mecanum mecanum;
    private Gyro gyro;
    //private PositionEstimation position;

    private PIDController PID;
    private final double kp = 0.01;
    private final double ki = 0.013;
    private final double kd = 0.002;
    
     //plus is left 
     
    public TurnDegreesAction(double degrees, double seconds) 
    {
        mecanum = Mecanum.getInstance();
        position = PositionEstimation.getInstance();
        endAfterSeconds = seconds;
        desiredDegrees = degrees;
        PID = new PIDController(kp, ki, kd);
        PID.enableContinuousInput(-180, 180);
        
    }

    @Override
    public void start()
    {
        currentDegrees = position.getAngle();;
        timer = new Timer();
        timer.start();
        //currentDegrees = position.getAngle();
        targetDegrees = (currentDegrees + desiredDegrees);
        PID.setSetpoint(targetDegrees);
        PID.setTolerance(toleranceDegrees);
       SmartDashboard.putString( "Current Action", "TurnDegreesAction Started");
    }

 

    @Override
    public void update()
    {
        currentDegrees = position.getAngle();
        SmartDashboard.putNumber("CurrentDegrees", currentDegrees);
        turn = PID.calculate(position.getAngle());
        mecanum.drive(0, 0, turn);
        SmartDashboard.putNumber("targetDegrees", targetDegrees);
        SmartDashboard.putNumber("turn", turn);
        
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
