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

    private NEOSparkMaxMotor effectorMotorLower; 
    private NEOSparkMaxMotor effectorMotorUpper;

    private LaserCan lc = new LaserCan(PortMap.LASER_CAN);
    public int laserDetectedDistance;
    public double coralDetectThreshold = 5; //in mm

    private double effectorUpper;
    private double effectorLower;

    States state;

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
        //effectorMotorLower = new NEOSparkMaxMotor(PortMap.EFFECTOR_MOTOR_LOWER);
        //effectorMotorUpper = new NEOSparkMaxMotor(PortMap.EFFECTOR_MOTOR_UPPER);
    }

    public void EffectorRun(){
        //effectorMotorLower.set(effectorUpper);
        //effectorMotorUpper.set(effectorLower);
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

    public void setState(States state){
        this.state = state;
    }

    public void setManualEffectorSpeed(double speed){
        effectorUpper = speed;
        effectorLower = speed;
    }

    public void EffectorStateProcessing(){
        switch (state)
        {
            case PASSIVE:
                    effectorUpper = 0.0;
                    effectorLower = 0.0;
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

    @Override
    public void update() {
        EffectorStateProcessing();
        EffectorRun();
        SmartDashboard.putNumber("Laser Detected Distance", laserDetectedDistance);
    }

    @Override
    public void initialize() {
        elevator = Elevator.getInstance();
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
