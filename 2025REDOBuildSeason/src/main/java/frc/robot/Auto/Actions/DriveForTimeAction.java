
package frc.robot.Auto.Actions;

//imports a timer
import edu.wpi.first.wpilibj.Timer;

//imports Mecanum Drive
import frc.robot.Subsystem.Mecanum;

/* This action moves the robot forward for a certain number
 * of seconds at a certain speed
 * negative is FORWARD, positive is backward
 */

public class DriveForTimeAction implements Actions {
    
    Timer timer;
    private double seconds;
    private double speed;
    private Mecanum mDrive = null;

    public DriveForTimeAction(double speed, double seconds) {
        this.seconds = seconds;
        this.speed = speed;
        mDrive = Mecanum.getInstance();
    }

    @Override
    public void start() {
        timer = new Timer();
        timer.start();
        System.out.println("Current Action DriveForTimeAction Started");
    }

    @Override
    public void update() {
        mDrive.driveWithSpeed(this.speed, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return timer.get() >= seconds;
    }

    @Override
    public void done() {
        timer.stop();
        mDrive.driveWithSpeed(0, 0, 0);
    }
}

