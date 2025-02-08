package frc.robot.Subsystem;

import frc.robot.Data.PortMap;

import frc.robot.Devices.NEOSparkMaxMotor;
import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface.Measurement;
import au.grapplerobotics.ConfigurationFailedException;

public class ScoringControl implements Subsystem {

    //case statement has nothing to make this class work. delete this comment when you have added the necessary changes.
    //much testing is need for this branch. delete this comment when you have tested it.

    private static ScoringControl instance = null;

    private NEOSparkMaxMotor effectorMotorUno = new NEOSparkMaxMotor(PortMap.EFFECTOR_MOTOR_1);
    private NEOSparkMaxMotor effectorMotorDos = new NEOSparkMaxMotor(PortMap.EFFECTOR_MOTOR_2);

    private LaserCan lc = new LaserCan(PortMap.LASER_CAN);
    public int laserDetectedDistance;
    public double coralDetectThreshold = 5; //in mm

    private double effectorUno;
    private double effectorDos;


    public String state = "passive";


    public static ScoringControl getInstance() {
        if (instance == null) {
            instance = new ScoringControl();
        }
        return instance;
    }

    public ScoringControl(){
        SubsystemManager.registerSubsystem(this);
    }

    public void EffectorRun(){
        effectorMotorUno.set(effectorUno);
        effectorMotorDos.set(effectorDos);
    }

    public void laserConfig(){
        try {
            lc.setRangingMode(LaserCan.RangingMode.SHORT);
            lc.setRegionOfInterest(new LaserCan.RegionOfInterest(8, 8, 16, 16));
            lc.setTimingBudget(LaserCan.TimingBudget.TIMING_BUDGET_33MS);
          } catch (ConfigurationFailedException e) {
            System.out.println("Configuration failed! " + e);
          }
    }

    public double laserDistance(){
        LaserCan.Measurement measurement = lc.getMeasurement();
        measurement.distance_mm = laserDetectedDistance;
        return laserDetectedDistance;
    }

    public void Passive(){
        state = "passive";
    }

    public void RampHasCoral(){
        state = "operator wants coral";
    }

    public void RampToEffector(){
        state = "transfering coral to effector";
    }

    public void CoralTripsSensor(){
        if (laserDetectedDistance < coralDetectThreshold){
        state = "coral tripped sensor";
        }
    }

    public void eject(){
        state = "ejecting coral";
    }

    public void EffectorStateProcessing(){
        switch (state)
        {
            case "passive":
                    effectorUno = 0.0;
                    effectorDos = 0.0;
                    System.out.println("passive");

                break;

            case "operator wants coral":
                    effectorUno = 0.0;
                    effectorDos = 0.0;
                    System.out.println("operator wants coral");
                
                break;

            case "transfering coral to effector":
                    effectorUno = 0.5;
                    effectorDos = 0.5;
                    System.out.println("transfering coral to effector");

                break;

            case "coral tripped sensor":
                    effectorUno = 0.0;
                    effectorDos = 0.0;
                    System.out.println("coral tripped sensor");

                break;
            
            case "ejecting coral":
                    effectorUno = -0.5;
                    effectorDos = -0.5;
                    if (laserDetectedDistance > coralDetectThreshold){
                        state = "passive";
                    }
                    System.out.println("ejecting coral");

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
