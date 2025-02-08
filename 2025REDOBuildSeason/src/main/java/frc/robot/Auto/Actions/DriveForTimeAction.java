
package frc.robot.Auto.Actions;

//imports a timer
import edu.wpi.first.wpilibj.Timer;

//imports Mecanum Drive
import frc.robot.Subsystem.Mecanum;

/* This action moves the robot forward for a certain number
 * of seconds at a certain speed
 */

public class DriveForTimeAction implements Actions {

    private double seconds;
    private double speed;
    private Mecanum drive = null;
    Timer timer;

    public DriveForTimeAction(double seconds , double strafe , double speed) {
        this.seconds = seconds;
        this.speed = speed;
        drive = Mecanum.getInstance();
    }

    @Override
    public void start() {
        timer = new Timer();
        timer.start();
        System.out.println("Current Action DriveForTimeAction Started");
    }

    @Override
    public void update() {
        drive.drive(this.speed, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return timer.get() >= seconds;
    }

    @Override
    public void done() {
        timer.stop();
    }
}

