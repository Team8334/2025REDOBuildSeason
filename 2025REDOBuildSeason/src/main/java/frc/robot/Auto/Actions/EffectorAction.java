package frc.robot.Auto.Actions;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Data.EncoderValues;
import frc.robot.Subsystem.ScoringControl;

/* 
 * This action should activate the end effacator for a certain
 * number of seconds to score, the elevator should already be at the
 * appropriate position when this happens
*/
public class EffectorAction implements Actions{
    
    private double seconds;
    private double speed;
    Timer timer;
    private ScoringControl scoringControl = null;

    public EffectorAction(double speed, double seconds){
        this.seconds = seconds;
        this.speed = speed;
        scoringControl = ScoringControl.getInstance();
    }

    @Override
    public void start(){
        timer = new Timer();
        timer.start();
    }

    @Override
    public void update(){
        //scoringControl.setManualEffectorSpeed(this.speed);
    }

    @Override
    public boolean isFinished(){
        return timer.get() >= seconds; 
    }

    @Override
    public void done(){
        timer.stop();
       // scoringControl.setManualEffectorSpeed(0);
    }
}
