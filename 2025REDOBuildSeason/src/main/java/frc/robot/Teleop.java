package frc.robot;

import frc.robot.Devices.Controller;

import java.lang.annotation.ElementType;

import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Data.PortMap;
import frc.robot.Subsystem.Mecanum;
import frc.robot.Subsystem.ScoringControl;
import frc.robot.Subsystem.Targeting;
import frc.robot.Subsystem.FrontLimelight;
import frc.robot.Subsystem.Alignment;
import frc.robot.Subsystem.Elevator;
import frc.robot.Data.States;

public class Teleop {

    Controller driverController;
    Controller operatorController;

    Mecanum mecanum;
    Alignment alignment;
    ScoringControl scoringControl;
    Elevator elevator;

    private double controllerLeftX;
    private double controllerLeftY;
    private double controllerRightX;
    public String driveState = "Idle";

    public double SafeSpeed = 0.1;
    public boolean IsSlowMode = false;
    public boolean IsDriveFast;
    public boolean OperatorWants = false;
    public boolean ElevatorIsUp;
    public double EffectorSpeed = -0.2;
    public double factorOfReduction;

    private boolean aButtonPressed;
    private boolean rightBumperPressed;
    private boolean leftBumperPressed;
    private boolean bButtonPressed;
    private boolean xButtonPressed;

    public Teleop() {
        driverController = new Controller(PortMap.DRIVER_CONTROLLER);
        if (!driverController.isOperational()) {
            System.out.println("404 Controller not found :(");
        }
    
        mecanum = Mecanum.getInstance();
        alignment = Alignment.getInstance();
        //scoringControl = ScoringControl.getInstance();
        //elevator = Elevator.getInstance();

        operatorController = new Controller(PortMap.OPERATOR_CONTROLLER);
        if (!operatorController.isOperational()) {
            System.out.println("404 Controller not found :(");
        }
    }

    public void teleopPeriodic() {
        driveBaseControl();
        //manipulatorControl();
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

        double forward;
        double strafe;
        double rotation;

        switch(driveState){
            case "Idle": {
                if(Math.abs(controllerLeftY) >= 0.2 || Math.abs(controllerLeftX) >= 0.2 || Math.abs(controllerRightX) >= 0.2){
                    driveState = "Manually Driving";
                }
                if(Math.abs(controllerLeftY) <= 0.2 && Math.abs(controllerLeftX) <= 0.2 && Math.abs(controllerRightX) <= 0.2){
                    if(aButtonPressed || rightBumperPressed || leftBumperPressed || bButtonPressed || xButtonPressed){
                        driveState = "Automatically Driving";
                    }
                }
                mecanum.driveWithSpeed(0, 0, 0);
            }
            break;
            case "Manually Driving": {
                if(Math.abs(controllerLeftY) >= 0.2){
                    forward = (controllerLeftY);
                }
                else{
                    forward = 0;
                }
                if(Math.abs(controllerLeftX) >= 0.2){
                    strafe = (controllerLeftX);
                }
                else{
                    strafe = 0;
                }
                if(Math.abs(controllerRightX) >= 0.2){
                    rotation = (controllerRightX);
                }
                else{
                    rotation = 0;
                }
                if(Math.abs(controllerLeftY) <= 0.2 && Math.abs(controllerLeftX) <= 0.2 && Math.abs(controllerRightX) <= 0.2){
                    driveState = "Idle";
                }
                if(factorOfReduction > 0){
                    forward = forward/factorOfReduction;
                    strafe = strafe/factorOfReduction;
                    rotation = rotation/factorOfReduction;
                }
                    
                mecanum.driveWithSpeed(forward, strafe, rotation);
                
            }
            break;
            case "Automatically Driving": {
                if(Math.abs(controllerLeftY) >= 0.2 || Math.abs(controllerLeftX) >= 0.2 || Math.abs(controllerRightX) >= 0.2){
                    driveState = "Manually Driving";
                }
                if(xButtonPressed){
                    alignment.alignXAngle("Reef");
                }
                else if(aButtonPressed){
                    alignment.alignAngle("Reef");
                }
                else if(bButtonPressed){
                    alignment.driveTo("Reef");
                }
                else if(rightBumperPressed){
                    alignment.alignRight("Reef");
                }
                else if(leftBumperPressed){
                    alignment.alignLeft("Reef");
                }
                else{
                    driveState = "Idle";
                }
            }

            
            break;
        }
        SmartDashboard.putString("Drive State", driveState);
    }

    public void manipulatorControl() {
        scoringControl.setManualEffectorSpeed(operatorController.getRightY());//re-test this. if this comment is in, assume the effector to be untested.

        if (operatorController.getAButton()) {
            factorOfReduction = 0;
            scoringControl.setState(States.RAMP);
        }
        
        if (operatorController.getBButton()) {
            scoringControl.setState(States.SCOREL2);
            factorOfReduction = 12;
            System.out.println("L2");
        }

        if (operatorController.getXButton()) {
            scoringControl.setState(States.SCOREL3);
            factorOfReduction = 15;
            System.out.println("L3");
        }

        if (operatorController.getYButton()) {
            scoringControl.setState(States.SCOREL4);
            factorOfReduction = 18;
            System.out.println("L4");
        }
    }
}

