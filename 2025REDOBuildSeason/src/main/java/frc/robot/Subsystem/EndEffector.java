package frc.robot.Subsystem;

import frc.robot.Data.PortMap;

import frc.robot.Devices.NEOSparkMaxMotor;

public class EndEffector implements Subsystem {

    //case statement has nothing to make this class work. delete this comment when you have added the necessary changes.
    //much testing is need for this branch. delete this comment when you have tested it.

    private static EndEffector instance = null;

    private NEOSparkMaxMotor effectorMotorUno = new NEOSparkMaxMotor(PortMap.EFFECTOR_MOTOR_1);
    private NEOSparkMaxMotor effectorMotorDos = new NEOSparkMaxMotor(PortMap.EFFECTOR_MOTOR_2);

    private double effectorUno;
    private double effectorDos;

    public String state = "passive";


    public static EndEffector getInstance() {
        if (instance == null) {
            instance = new EndEffector();
        }
        return instance;
    }

    public EndEffector(){
        SubsystemManager.registerSubsystem(this);
    }

    public void Passive(){
        state = "passive";
    }

    public void RampDetect(){
        state = "ramp detects coral";
    }

    public void RampToEffector(){
        state = "transfering coral to effector";
    }

    public void CoralTripsSensor(){
        state = "coral tripped sensor";
    }

    public void eject(){
        state = "ejecting coral";
    }

    public void EffectorStateProcessing(){
        switch (state)
        {
            case "passive":

                break;

            case "ramp detects coral":
                
                break;

            case "transfering coral to effector":

                break;

            case "coral tripped sensor":

                break;
            
            case "ejecting coral":

                break;

        }
    }

    @Override
    public void update() {
        
    }

    @Override
    public void initialize() {
    
    }

    @Override
    public void log() {
   
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "EndEffector";
    }

}
