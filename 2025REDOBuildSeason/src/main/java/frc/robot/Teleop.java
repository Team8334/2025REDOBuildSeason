package frc.robot;

import frc.robot.Devices.Controller;
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
           if(Math.abs(controllerLeftY) >= 0.5){
            forward = (controllerLeftY*0.5);
           }
           else{
            forward = 0;
           }
           if(Math.abs(controllerLeftX) >= 0.5){
            strafe = (controllerLeftX*0.5);
           }
           else{
            strafe = 0;
           }
           if(Math.abs(controllerRightX) >= 0.2){
            rotation = (controllerRightX*0.35);
           }
           else{
            rotation = 0;
           }
           mecanum.drive(forward, strafe, rotation);
        }
    
        public void manipulatorControl() {
            if (operatorController.getAButton()){
                scoringControl.OperatorWantsCoral();
            }
            if (operatorController.getBButton()){
                scoringControl.eject();
            }
        }
    
    }
