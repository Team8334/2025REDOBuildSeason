package frc.robot;

import frc.robot.Devices.Controller;
import frc.robot.Data.PortMap;
import frc.robot.Subsystem.Mecanum;


public class Teleop {
    
    Controller driverController;
    
        Mecanum mecanum;
        private double controllerLeftX;
        private double controllerLeftY;
        private double controllerRightX;
    
        public Teleop() {
            driverController = new Controller(PortMap.DRIVER_CONTROLLER);
            if(!driverController.isOperational()) {
                System.out.println("404 Controller not found :(");
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
           double forward;
           double strafe;
           double rotation;
           if(Math.abs(controllerLeftY) >= 0.5){
            forward = controllerLeftY;
           }
           else{
            forward = 0;
           }
           if(Math.abs(controllerLeftX) >= 0.5){
            strafe = controllerLeftX;
           }
           else{
            strafe = 0;
           }
           if(Math.abs(controllerRightX) >= 0.5){
            rotation = controllerRightX;
           }
           else{
            rotation = 0;
           }
           mecanum.drive(forward, strafe, rotation);
           System.out.println("Controller x: " + driverController.getLeftX());
           System.out.println("Controller y: " + driverController.getLeftY());
           System.out.println("Controller right x: " + driverController.getRightX());
        }
    
        public void manipulatorControl() {
    
        }
    
    }
