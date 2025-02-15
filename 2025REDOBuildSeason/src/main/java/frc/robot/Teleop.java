package frc.robot;

import frc.robot.Devices.Controller;

import java.lang.annotation.ElementType;

import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Data.PortMap;
import frc.robot.Subsystem.Mecanum;
import frc.robot.Subsystem.ScoringControl;


public class Teleop {
    
    Controller driverController;
    Controller operatorController;
    
        Mecanum mecanum;
        ScoringControl scoringControl;
        private double controllerLeftX;
        private double controllerLeftY;
        private double controllerRightX;

        public double SafeSpeed = 0.2;
        public boolean IsElevatorUp;
        public boolean IsSlowMode = false;
        public boolean IsDriveFast;
        public boolean OperatorWants = false;
    
        public Teleop() {
            driverController = new Controller(PortMap.DRIVER_CONTROLLER);
            if(!driverController.isOperational()) {
                System.out.println("404 Controller not found :(");
            }

            operatorController = new Controller(PortMap.OPERATOR_CONTROLLER);
            if(!operatorController.isOperational()) {
                System.out.println("404 Controller not found :(");
            }
    
            mecanum = Mecanum.getInstance();
            scoringControl = ScoringControl.getInstance();
        }
    
        public void teleopPeriodic() {
            driveBaseControl();
        }
    
        public void driveBaseControl() {
           controllerLeftY = driverController.getLeftY();
           controllerLeftX = driverController.getLeftX();
           controllerRightX = driverController.getRightX();
           double forward;
           double strafe;
           double rotation;

           if (operatorController.getAButton()){
            IsSlowMode = !IsSlowMode;
           }

           if(Math.abs(controllerLeftY) >= 0.5 && !IsElevatorUp){
            forward = (controllerLeftY);
           }
           else if(Math.abs(controllerLeftY) >= 0.5 && IsElevatorUp || IsSlowMode){
            forward = (controllerLeftY*SafeSpeed);
           }
           else{
            forward = 0;
           }
           
           if(Math.abs(controllerLeftX) >= 0.5 && !IsElevatorUp){
            strafe = (controllerLeftX);
           }
           else if(Math.abs(controllerLeftX) >= 0.5 && IsElevatorUp || IsSlowMode){
            strafe = (controllerLeftX*SafeSpeed);
           }
           else{
            strafe = 0;
           }

           if(Math.abs(controllerRightX) >= 0.2 && !IsElevatorUp){
            rotation = (controllerRightX);
           }
           else if (Math.abs(controllerRightX) >= 0.2 && IsElevatorUp || IsSlowMode){
            rotation = (controllerRightX*SafeSpeed);
           }
           else{
            rotation = 0;
           }
           
           mecanum.drive(forward, strafe, rotation);

           if(forward > 0.15 || strafe > 0.15 || rotation > 0.15){
            IsDriveFast = true;
           }
        }
    
        public void manipulatorControl() {
            if (operatorController.getDebouncedButton(7)){


                scoringControl.OperatorWantsCoral();
            }
            if (operatorController.getDebouncedButton(2)){
                scoringControl.eject();
            }

            if (operatorController.getDebouncedButton(11) && !IsDriveFast){
                scoringControl.ScoreL1();
            }

            if (operatorController.getDebouncedButton(12) && !IsDriveFast){
                scoringControl.ScoreL2();
            }

            if (operatorController.getDebouncedButton(9) && !IsDriveFast){
                scoringControl.ScoreL3();
            }

            if (operatorController.getDebouncedButton(10) && !IsDriveFast){
                scoringControl.ScoreL4();
            }
        }
    
    }
