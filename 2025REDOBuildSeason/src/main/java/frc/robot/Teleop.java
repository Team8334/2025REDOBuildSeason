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

public class Teleop {

    Controller driverController;
    Controller operatorController;

    Mecanum mecanum;
    Alignment alignment;
    ScoringControl scoringControl;

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

    private boolean aButtonPressed;
    private boolean rightBumperPressed;
    private boolean leftBumperPressed;
    private boolean bButtonPressed;

    public Teleop() {
        driverController = new Controller(PortMap.DRIVER_CONTROLLER);
        if (!driverController.isOperational()) {
            System.out.println("404 Controller not found :(");
        }
    
        mecanum = Mecanum.getInstance();
        alignment = Alignment.getInstance();
        //scoringControl = ScoringControl.getInstance();

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

        double forward;
        double strafe;
        double rotation;

        switch(driveState){
            case "Idle": {
                if(Math.abs(controllerLeftY) >= 0.2 || Math.abs(controllerLeftX) >= 0.2 || Math.abs(controllerRightX) >= 0.2){
                    driveState = "Manually Driving";
                }
                if(Math.abs(controllerLeftY) <= 0.2 && Math.abs(controllerLeftX) <= 0.2 && Math.abs(controllerRightX) <= 0.2){
                    if(aButtonPressed || rightBumperPressed || leftBumperPressed || bButtonPressed){
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
                mecanum.driveWithSpeed(forward, strafe, rotation);
            }
            break;
            case "Automatically Driving": {
                if(Math.abs(controllerLeftY) >= 0.2 || Math.abs(controllerLeftX) >= 0.2 || Math.abs(controllerRightX) >= 0.2){
                    driveState = "Manually Driving";
                }
                if(aButtonPressed){
                    alignment.align("Reef");
                }
                if(bButtonPressed){
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
        scoringControl.setManualEffectorSpeed(operatorController.getRightY() * EffectorSpeed);

        if (operatorController.getAButton()) {
            scoringControl.OperatorWantsCoral();
        }
        if (operatorController.getBButton()) {
            scoringControl.eject();
        }

        // if (operatorController.getAButton() && !IsDriveFast) {
        // scoringControl.ScoreL1();
        // System.out.println("L1");
        // }

        if (operatorController.getBButton() && !IsDriveFast) {
            scoringControl.ScoreL2();
            System.out.println("L2");
        }

        if (operatorController.getXButton() && !IsDriveFast) {
            scoringControl.ScoreL3();
            System.out.println("L3");
        }

        if (operatorController.getYButton() && !IsDriveFast) {
            scoringControl.ScoreL4();
            System.out.println("L4");
        }
    }

}
