package frc.robot.Subsystem;

import frc.robot.Data.PortMap;

import frc.robot.Devices.NEOSparkMaxMotor;
import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface.Measurement;
import au.grapplerobotics.ConfigurationFailedException;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystem.Elevator;
import frc.robot.Data.EncoderValues;
import frc.robot.Data.States;

public class ScoringControl implements Subsystem {

    //case statement has nothing to make this class work. delete this comment when you have added the necessary changes.
    //much testing is need for this branch. delete this comment when you have tested it.

    private static ScoringControl instance = null;

    Timer timer;
    Elevator elevator;

    private NEOSparkMaxMotor rampLeftMotor; 
    private NEOSparkMaxMotor rampRightMotor;
    private NEOSparkMaxMotor effectorMotor;

    private LaserCan lc = new LaserCan(PortMap.LASER_CAN);
    public int laserDetectedDistance;
    public double coralDetectThreshold = 5; //in mm

    private double effectorLower;

    States elevatorState;
    States effectorState;


    public String monitoringState;

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
        rampLeftMotor.set(rampRight);
        rampRightMotor.set(rampLeft);
        effectorMotor.set(effector);
    }

    public void laserConfig(){
        try {
            lc.setRangingMode(LaserCan.RangingMode.SHORT);
            lc.setRegionOfInterest(new LaserCan.RegionOfInterest(8, 8, 16, 16));
            lc.setTimingBudget(LaserCan.TimingBudget.TIMING_BUDGET_33MS);
          } 
        catch (ConfigurationFailedException e) {
            System.out.println("Configuration failed! " + e);
        }
    }

    public double laserDistance(){
        LaserCan.Measurement measurement = lc.getMeasurement();
        measurement.distance_mm = laserDetectedDistance;
        return laserDetectedDistance;
    }

    public void setElevatorState(States elevatorState){
        this.elevatorState = elevatorState;
    }

    public void setEffectorState(States effectorState){
        this.effectorState = effectorState;
    }

   // public void setManualEffectorSpeed(double speed){
   //     rampLeft = (speed/4);
   //     rampRight = (speed/-4);
   //     effector = (speed/-5);
   // }

    public void ElevatorStateProcessing(){
        switch (elevatorState){

            case PASSIVE:
                    elevator.stop();
                    monitoringState = "Passive";
                break;

            case RAMP:
                    elevator.reachGoal(EncoderValues.ELEVATOR_RAMP);
                    
                break;

            case SCOREL1:
                    elevator.reachGoal(EncoderValues.ELEVATOR_L1);
                
                break;
            
            case SCOREL2:
                    elevator.reachGoal(EncoderValues.ELEVATOR_L2);

                break;

            case SCOREL3:
                    elevator.reachGoal(EncoderValues.ELEVATOR_L3);
                    
                break;

            case SCOREL4:
                   elevator.reachGoal(EncoderValues.ELEVATOR_L4);
                break;

        }
        SmartDashboard.putString("scoringState", monitoringState);
    }

    public void EffectorStateProcessing(){
        switch (effectorState){

            case NOTHING:
                effector = 0.0;
                rampLeft = 0.0;
                rampRight = 0.0;
                 
                break;

            case WAITINGINRAMP:
                effector = 0.0;
                rampLeft = 0.1;
                rampRight = 0.1;

                break;

            case PASSING:
                effector = 0.1;
                rampLeft = 0.1;
                rampRight = 0.1;

                break;

            case WAITINGINEFFECTOR:
                effector = 0.0;
                rampLeft = 0.0;
                rampRight = 0.0;

                break;

            case SCORING:
                effector = 0.1;
                rampLeft = 0.0;
                rampRight = 0.0;

                break;

            case DEALGAEFYING:
                effector = 0.1;
                rampLeft = 0.0;
                rampRight = 0.0;

                break;
        }
    }

    @Override
    public void update() {
        ElevatorStateProcessing();
        EffectorStateProcessing();
        //EffectorRun();
        SmartDashboard.putNumber("Laser Detected Distance", laserDetectedDistance);
    }

    @Override
    public void initialize() {
        elevator = Elevator.getInstance();
        rampLeftMotor = new NEOSparkMaxMotor(PortMap.RAMP_LEFT);
        rampRightMotor = new NEOSparkMaxMotor(PortMap.RAMP_RIGHT);
        effectorMotor = new NEOSparkMaxMotor(PortMap.EFFECTOR);
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
