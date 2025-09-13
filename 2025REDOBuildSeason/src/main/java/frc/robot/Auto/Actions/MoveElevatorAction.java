/*package frc.robot.Auto.Actions;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Data.EncoderValues;
import frc.robot.Subsystem.Elevator;
import frc.robot.Subsystem.ScoringControl;
import frc.robot.Data.States;

public class MoveElevatorAction implements Actions{
    
    private double seconds;
    private ScoringControl scoringControl = null;
    Timer timer;
    States state;

    public MoveElevatorAction (double seconds, States state){
        this.seconds = seconds;
        this.state = state;
        scoringControl = ScoringControl.getInstance();
    }
    
    @Override
    public void start(){
        timer = new Timer();
        timer.start();
    }

    @Override
    public void update(){
        scoringControl.setElevatorState(state);
    }
    
    @Override
    public boolean isFinished() {
        return timer.get() >= seconds;
    }

    @Override
    public void done(){
        timer.stop();
    }
}
*/