package frc.robot.Auto.Actions;

//imports a timer
import edu.wpi.first.wpilibj.Timer;

//import frc.robot.Subsystem.Elevators;//CHANGE ACCORDING TO REAL NAME

public class MoveElevatorAction implements Actions{
    
    private double seconds;
    private double speed;
    Timer timer;

    public MoveElevatorAction (double seconds, double speed){
        this.seconds = seconds;
        this.speed = speed;
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
    public boolean isFinished() {
        return timer.get() >= seconds;
    }

    @Override
    public void done(){
        timer.stop();
    }
}
