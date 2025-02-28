package frc.robot.Auto.Actions;

//imports important things
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
    Timer timer;
    private ScoringControl score = null;

    public EffectorAction(double seconds){
        this.seconds = seconds;
        score = ScoringControl.getInstance();
    }

    @Override
    public void start(){
        timer = new Timer();
        timer.start();
    }

    @Override
    public void update(){
        score.EffectorRun();
    }

    @Override
    public boolean isFinished(){
        return timer.get() >= seconds; 
    }

    @Override
    public void done(){
        timer.stop();
        score.EffectorStateProcessing();
        System.out.println("Scoring done");
    }
}
