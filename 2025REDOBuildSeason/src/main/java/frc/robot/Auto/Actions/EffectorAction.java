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
    private double speed;
    //private String state;
    Timer timer;
    private ScoringControl scoringControl = null;

    public EffectorAction(double speed, double seconds){
        this.seconds = seconds;
        this.speed = speed;
        //this.state = state;
        scoringControl = ScoringControl.getInstance();
       // System.out.println("effectorSpeed" + this.speed);
    }

    @Override
    public void start(){
        //scoringControl.setState(this.state);
        //System.out.println("SeffectorSpeed " + this.speed);
        timer = new Timer();
        timer.start();
    }

    @Override
    public void update(){
        //System.out.println("UeffectorSpeed " + this.speed);
        scoringControl.setManualEffectorSpeed(this.speed);
        //System.out.println("scoring");
    }

    @Override
    public boolean isFinished(){
        return timer.get() >= seconds; 
    }

    @Override
    public void done(){
        timer.stop();
        scoringControl.setManualEffectorSpeed(0);
       // System.out.println("DeffectorSpeed " + this.speed);
        System.out.println("Scoring done");
    }
}
