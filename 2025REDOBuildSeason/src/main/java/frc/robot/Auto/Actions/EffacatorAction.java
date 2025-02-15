package frc.robot.Auto.Actions;

//imports a timer
import edu.wpi.first.wpilibj.Timer;

/* This action should activate the end effacator for a certain
 * number of seconds to score
*/
public class EffacatorAction implements Actions{
    
    private double speed;
    private double seconds;
    Timer timer;

    public EffacatorAction(double speed, double seconds){
        this.speed = speed;
        this.seconds = seconds;
    }

    @Override
    public void start(){
        timer = new Timer();
        timer.start();
    }

    @Override
    public void update(){

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
