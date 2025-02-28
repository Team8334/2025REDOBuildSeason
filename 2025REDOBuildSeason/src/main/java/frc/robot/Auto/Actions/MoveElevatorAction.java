package frc.robot.Auto.Actions;

//imports important things
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Data.EncoderValues;
import frc.robot.Subsystem.Elevator;
import frc.robot.Subsystem.ScoringControl;

//import frc.robot.Subsystem.Elevator;//CHANGE ACCORDING TO REAL NAME

/* This action should move the elevator up or down
 * 
 */
public class MoveElevatorAction implements Actions{
    
    private double seconds;
    private String state;
   // private double speed;
    private Elevator elevator;
    private ScoringControl scoringControl;
    Timer timer;

    public MoveElevatorAction (double seconds, String state){
        this.seconds = seconds;
        this.state = state;
    }
    
    @Override
    public void start(){
        timer = new Timer();
        timer.start();
        SmartDashboard.putString("scoringState", state);
    }

    @Override
    public void update(){
        scoringControl.EffectorStateProcessing();
        SmartDashboard.putString("Elevator State: " + state);
    }
    

    @Override
    public boolean isFinished() {
        return timer.get() >= seconds;
    }

    @Override
    public void done(){
        timer.stop();
        state = "passive";
    }
}
