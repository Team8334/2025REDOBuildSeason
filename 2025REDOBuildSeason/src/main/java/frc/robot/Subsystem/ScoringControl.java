package frc.robot.Subsystem;

import frc.robot.Data.PortMap;

import frc.robot.Devices.NEOSparkMaxMotor;
import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface.Measurement;
import au.grapplerobotics.ConfigurationFailedException;
import edu.wpi.first.wpilibj.Timer;

public class ScoringControl implements Subsystem {

    //case statement has nothing to make this class work. delete this comment when you have added the necessary changes.
    //much testing is need for this branch. delete this comment when you have tested it.

    private static ScoringControl instance = null;

    Timer timer;

    private NEOSparkMaxMotor effectorMotorUno = new NEOSparkMaxMotor(PortMap.EFFECTOR_MOTOR_1);
    private NEOSparkMaxMotor effectorMotorDos = new NEOSparkMaxMotor(PortMap.EFFECTOR_MOTOR_2);

    private LaserCan lc = new LaserCan(PortMap.LASER_CAN);
    public int laserDetectedDistance;
    public double coralDetectThreshold = 5; //in mm

    private double effectorUno;
    private double effectorDos;

    public String state = "passive";
    public boolean elevatorIsSafe;

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
        elevatorIsSafe = true;
    }

    public void Ramp(){
        state = "ramp";
    }

    public void OperatorWantsCoral(){
        state = "operator wants coral";
        elevatorIsSafe = true;
    }

    public void CoralTripsSensor(){
        if (laserDetectedDistance < coralDetectThreshold){
        state = "coral tripped sensor";
        }
        elevatorIsSafe = true;
    }

    public void ScoreL1(){
        state = "Score L1";
        elevatorIsSafe = false;
    }

    public void ScoreL2(){
        state = "Score L2";
        elevatorIsSafe = false;
    }

    public void ScoreL3(){
        state = "Score L3";
        elevatorIsSafe = false;
    }

    public void ScoreL4(){
        state = "Score L4";
        elevatorIsSafe = false;
    }

    public void eject(){ //in the emergency case you need to get rid of the coral. 
        state = "ejecting coral";
    }

    public void setManualEffectorSpeed(double speed){
        effectorUno = speed;
        effectorDos = speed;
    }

    public void EffectorStateProcessing(){
        switch (state)
        {
            case "passive":
                    effectorUno = 0.0;
                    effectorDos = 0.0;
                    System.out.println("passive");

                break;

            case "ramp":
                    effectorUno = 0.0;
                    effectorDos = 0.0;
                    //code for elevator here. preset: ramp
                    System.out.println("ramp");
                break;

            case "operator wants coral":
                    timer = new Timer();
                    timer.start();
                    if (timer.get() < 1){
                    effectorUno = 0.5;
                    effectorDos = 0.5;
                    }
                    else {
                        state = "passive";
                    }
                    System.out.println("operator wants coral");
                break;

            case "coral tripped sensor":
                    effectorUno = 0.0;
                    effectorDos = 0.0;
                    System.out.println("coral tripped sensor");

                break;

            case "Score L1":
                    //code for elevator here. preset: L1
                    //if statement here, checking if the elevator is in position before proceeding
                    effectorUno = -0.5;
                    effectorDos = -0.5;
                    System.out.println("scoring in L1");
                
                break;
            
            case "Score L2":
                    //code for elevator here. preset: L2
                    //if statement here, checking if the elevator is in position before proceeding
                    effectorUno = -0.5;
                    effectorDos = -0.5;
                    System.out.println("scoring in L2");

                break;

            case "Score L3":
                    //code for elevator here. preset: L3
                    //if statement here, checking if the elevator is in position before proceeding
                    effectorUno = -0.5;
                    effectorDos = -0.5;
                    System.out.println("scoring in L3");
                    
                break;

            case "Score L4":
                    //code for elevator here. preset: L4
                    //if statement here, checking if the elevator is in position before proceeding
                    effectorUno = -0.5;
                    effectorDos = -0.5;
                    System.out.println("scoring in L4");

                break;
            
            case "ejecting coral":
                    effectorUno = -0.5;
                    effectorDos = -0.5;
                    //we might need to manipulate the elevator to ensure the piece is successfully ditched. 
                    //when testing, you might need to add a slight time delay to ensure the coral is successfully ejected.
                    if (laserDetectedDistance > coralDetectThreshold){ 
                        state = "passive";
                    }
                    System.out.println("ejecting coral");

                break;

        }
    }

    @Override
    public void update() {
        //EffectorStateProcessing();
        EffectorRun();
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
