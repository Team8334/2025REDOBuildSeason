/*package frc.robot.Auto.Actions;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Data.EncoderValues;
import frc.robot.Subsystem.ScoringControl;
import frc.robot.Data.States;

/* 
 * This action should activate the end effacator for a certain
 * number of seconds to score, the elevator should already be at the
 * appropriate position when this happens
*/ /* 
public class EffectorAction implements Actions{
    
    private double seconds;
    private double speed;
    Timer timer;
    States state;
    private ScoringControl scoringControl = null;

    public EffectorAction(double seconds, States state){
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
        scoringControl.setEffectorState(state);
    }

    @Override
    public boolean isFinished(){
        return timer.get() >= seconds;
    }

    @Override
    public void done(){
        timer.stop();
    }
}
*/