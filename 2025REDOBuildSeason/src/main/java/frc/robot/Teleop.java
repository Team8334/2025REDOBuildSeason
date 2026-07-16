package frc.robot;

import frc.robot.Devices.Controller;

import static edu.wpi.first.units.Units.Horsepower;

import java.lang.annotation.ElementType;

import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Data.PortMap;
import frc.robot.Subsystem.Mecanum;
import frc.robot.Data.Debug;

public class Teleop {

    Controller driverController;

    Mecanum mecanum;

    private double controllerLeftX;
    private double controllerLeftY;
    private double controllerRightX;
    public String driveState = "Idle";

    public double SafeSpeed = 0.1;
    public boolean IsSlowMode = false;
    public boolean IsDriveFast;

    private boolean aButtonPressed;
    private boolean rightBumperPressed;
    private boolean leftBumperPressed;
    private boolean bButtonPressed;
    private boolean xButtonPressed;

    public Teleop() {
        driverController = new Controller(PortMap.DRIVER_CONTROLLER);
        if (!driverController.isOperational()) {
        }

        mecanum = Mecanum.getInstance();
    }

    public void teleopPeriodic() {
        driveBaseControl();
    }

    public void driveBaseControl() {
        controllerLeftY = driverController.getLeftY();
        controllerLeftX = driverController.getLeftX();
        controllerRightX = driverController.getRightX();

        aButtonPressed = driverController.getAButton();
        rightBumperPressed = driverController.getRightBumperButton();
        leftBumperPressed = driverController.getLeftBumperButton();
        bButtonPressed = driverController.getBButton();
        xButtonPressed = driverController.getXButton();

        // Initialize the movement variables to 0
        double forward = 0;
        double strafe = 0;
        double rotation = 0;

        // TODO 1: Link Joystick Inputs to Movement Variables
        // The variables above (forward, strafe, rotation) control how the robot moves.
        // Currently they are 0, meaning the robot won't move!
        // We need to set them using the values from the controller joysticks.
        // 
        // Hint: 
        // - controllerLeftY gives the up/down position of the left joystick (used for forward/backward)
        // - controllerLeftX gives the left/right position of the left joystick (used for strafing left/right)
        // - controllerRightX gives the left/right position of the right joystick (used for rotating the robot)
        //
        // Try setting: forward = controllerLeftY;
        // (Do the same for strafe and rotation below)
        
        

        // TODO 2: Add a Deadband (Optional but recommended)
        // Joysticks sometimes don't perfectly return to 0 when you let go.
        // To fix this, we can ignore values that are very small (e.g., between -0.2 and 0.2).
        // 
        // Example for forward:
        // if (Math.abs(controllerLeftY) >= 0.2) {
        //     forward = controllerLeftY;
        // } else {
        //     forward = 0;
        // }
        // 
        // Can you write the if/else statements for strafe and rotation?



        // TODO 3: Turbo Mode / Reverse Mode (Optional)
        // If you want, you can make a button change how the robot drives.
        // For example, if you press the right bumper, we can multiply the speeds by -1 to reverse the controls.
        // 
        // if (rightBumperPressed) {
        //     forward = forward * -1;
        //     // do the same for strafe
        // }


        if (Math.abs(controllerLeftY) <= 0.2 && Math.abs(controllerLeftX) <= 0.2 && Math.abs(controllerRightX) <= 0.2) {
            driveState = "Idle";
        }

        // TODO 4: Command the Robot to Drive
        // Finally, uncomment the line below to send the forward, strafe, and rotation values to the robot's wheels!
        // mecanum.driveWithSpeed(forward, strafe, rotation);

        if (Debug.debug) {
            SmartDashboard.putString("Drive State", driveState);
        }
    }
}
