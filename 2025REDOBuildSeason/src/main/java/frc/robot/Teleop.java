package frc.robot;

import frc.robot.Devices.Controller;
import frc.robot.Data.PortMap;
import frc.robot.Subsystem.Mecanum;
import frc.robot.Subsystem.Targeting;
import frc.robot.Subsystem.FrontLimelight;
import frc.robot.Subsystem.Alignment;

public class Teleop {
    
        Controller driverController;
    
        Mecanum mecanum;
        Alignment alignment;
        private double controllerLeftX;
        private double controllerLeftY;
        private double controllerRightX;

        private boolean aligning = false;
        private boolean aButtonPressed;
        private boolean rightBumperPressed;
        private boolean leftBumperPressed;
    
        public Teleop() {
            driverController = new Controller(PortMap.DRIVER_CONTROLLER);
            if(!driverController.isOperational()) {
                System.out.println("404 Controller not found :(");
            }
    
            mecanum = Mecanum.getInstance();
            alignment = Alignment.getInstance();

            aButtonPressed = driverController.getAButtonPressed();
            rightBumperPressed = driverController.getRightBumperButtonPressed();
            leftBumperPressed = driverController.getLeftBumperButtonPressed();
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
            
            if(aButtonPressed){
                aligning = true;
                alignment.alignReef();
            }
            if(rightBumperPressed){
                aligning = true;
                alignment.alignRight();
            }
            if(leftBumperPressed){
                aligning = true;
                alignment.alignLeft();
            }
            if(!aButtonPressed && !rightBumperPressed && !leftBumperPressed){
                aligning = false;
            }
            if(!aligning){
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
          
        }
    
        public void manipulatorControl() {
    
        }
    
    }
