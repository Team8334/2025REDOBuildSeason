package frc.robot;

import frc.robot.Devices.Controller;

import static edu.wpi.first.units.Units.Horsepower;

import java.lang.annotation.ElementType;

import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Data.PortMap;
import frc.robot.Subsystem.Mecanum;
import frc.robot.Subsystem.ScoringControl;
import frc.robot.Subsystem.Elevator;
import frc.robot.Data.States;
import frc.robot.Data.Debug;

public class Teleop {

    Controller driverController;
    Controller operatorController;

    Mecanum mecanum;
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
    public boolean algaeMode;

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
        
        scoringControl = ScoringControl.getInstance();
        elevator = Elevator.getInstance();

        operatorController = new Controller(PortMap.OPERATOR_CONTROLLER);
        if (!operatorController.isOperational()) {
        }
    }

    public void teleopPeriodic() {
        driveBaseControl();
        manipulatorControl();
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

        factorOfReduction = (elevator.getExtendedCyclePosition()>1?elevator.getExtendedCyclePosition():1);

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
        
            
        if(Debug.debug){
        SmartDashboard.putString("Drive State", driveState);
        }
    }

    public void manipulatorControl() {

        scoringControl.setManualEffectorSpeed(operatorController.getRightY());

        if (operatorController.getLeftTriggerAxis()>0.6){
            algaeMode = true;
            scoringControl.setEffectorState(States.HOLDINGALGAE);
        }
        else{
            algaeMode = false;
            if (operatorController.getRightBumperButton() && !algaeMode && scoringControl.elevatorState!=States.RAMP){
                scoringControl.setEffectorState(States.SCORING);
            }
    
            else if (operatorController.getRightBumperButton() && !algaeMode && scoringControl.elevatorState==States.RAMP){
                if (true) {
                    scoringControl.setEffectorState(States.PASSING);
                }

            } else{
                scoringControl.setEffectorState(States.NOTHING);
            }
        }

        if (operatorController.getLeftBumperButton() && algaeMode){
            scoringControl.setEffectorState(States.DEALGAEFYING);
        }

        if (operatorController.getRightBumperButton() && algaeMode){
            scoringControl.setEffectorState(States.YEETINGALGAE);
        }

        if (operatorController.getAButton() && algaeMode){
            scoringControl.setElevatorState(States.LOWERALGAE);
        }

        if (operatorController.getBButton() && algaeMode){
            scoringControl.setElevatorState(States.UPPERALGAE);
        }

        if (operatorController.getYButton() && algaeMode){
            scoringControl.setElevatorState(States.BARGE);
        }

        if (operatorController.getXButton() && !algaeMode){
            scoringControl.setElevatorState(States.RAMP);
        }

        if (operatorController.getAButton() && !algaeMode){
            scoringControl.setElevatorState(States.SCOREL2);
        }

        if (operatorController.getBButton() && !algaeMode){
            scoringControl.setElevatorState(States.SCOREL3);
        }

        if (operatorController.getYButton() && !algaeMode){
            scoringControl.setElevatorState(States.SCOREL4);
        }

        if(Debug.debug){
        SmartDashboard.putBoolean("algaeMode", algaeMode);
        }

    }
}

