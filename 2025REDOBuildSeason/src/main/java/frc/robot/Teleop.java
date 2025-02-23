package frc.robot;

import frc.robot.Devices.Controller;

import java.lang.annotation.ElementType;

import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Data.PortMap;
import frc.robot.Subsystem.Mecanum;
import frc.robot.Subsystem.ScoringControl;
import frc.robot.Subsystem.Elevator;


public class Teleop {

    Controller driverController;
    Controller operatorController;

    Mecanum mecanum;
    Elevator elevator;
    ScoringControl scoringControl;

    private double controllerLeftX;
    private double controllerLeftY;
    private double controllerRightX;

    public double SafeSpeed = 0.1;
    public boolean IsSlowMode = false;
    public boolean IsDriveFast;
    public boolean OperatorWants = false;
    public boolean ElevatorIsUp;
    public double EffectorSpeed = -0.2;

    public Teleop() {
        driverController = new Controller(PortMap.DRIVER_CONTROLLER);
        if (!driverController.isOperational()) {
            System.out.println("404 Controller not found :(");
        }

        operatorController = new Controller(PortMap.OPERATOR_CONTROLLER);
        if (!operatorController.isOperational()) {
            System.out.println("404 Controller not found :(");
        }

        mecanum = Mecanum.getInstance();
        scoringControl = ScoringControl.getInstance();
        elevator = Elevator.getInstance();
    }

    public void teleopPeriodic() {
        driveBaseControl();
        manipulatorControl();
    }

    public void driveBaseControl() {
        controllerLeftY = driverController.getLeftY();
        controllerLeftX = driverController.getLeftX();
        controllerRightX = driverController.getRightX();
        double forward;
        double strafe;
        double rotation;

        if (Math.abs(controllerLeftY) >= 0.1) {
            forward = (controllerLeftY);
        } else {
            forward = 0;
        }
        if (Math.abs(controllerLeftX) >= 0.1) {
            strafe = (controllerLeftX);
        } else {
            strafe = 0;
        }
        if (Math.abs(controllerRightX) >= 0.1) {
            rotation = (controllerRightX);
        } else {
            rotation = 0;
        }
        mecanum.driveWithSpeed(forward, strafe, rotation);
    }

    public void manipulatorControl() {
        scoringControl.setManualEffectorSpeed(operatorController.getRightY() * EffectorSpeed);

        if (operatorController.getAButton()) {
            scoringControl.moveToRamp();
        }
        // if (operatorController.getBButton()) {
        //     scoringControl.eject();
        // }

        // if (operatorController.getAButton() && !IsDriveFast) {
        // scoringControl.ScoreL1();
        // System.out.println("L1");
        // }

        if (operatorController.getBButton()) {
            scoringControl.scoreL2();
            System.out.println("L2");
        }

        if (operatorController.getXButton()) {
            scoringControl.scoreL3();
            System.out.println("L3");
        }

        if (operatorController.getYButton()) {
            scoringControl.passive();
        }
    }

}
