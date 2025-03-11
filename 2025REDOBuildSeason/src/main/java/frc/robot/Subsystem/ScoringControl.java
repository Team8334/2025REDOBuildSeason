package frc.robot.Subsystem;

import frc.robot.Data.PortMap;
import frc.robot.Subsystem.Elevator;
import frc.robot.Data.EncoderValues;
import frc.robot.Data.States;
import frc.robot.Devices.NEOSparkMaxMotor;
import frc.robot.Devices.Lazer;

import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface.Measurement;
import au.grapplerobotics.ConfigurationFailedException;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ScoringControl implements Subsystem {

    //case statement has nothing to make this class work. delete this comment when you have added the necessary changes.
    //much testing is need for this branch. delete this comment when you have tested it.

    private static ScoringControl instance = null;

    Timer timer;
    Elevator elevator;
    Lazer lazer;

    private NEOSparkMaxMotor effectorMotorLower = new NEOSparkMaxMotor(PortMap.EFFECTOR_MOTOR_LOWER);
    private NEOSparkMaxMotor effectorMotorUpper = new NEOSparkMaxMotor(PortMap.EFFECTOR_MOTOR_UPPER);

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
        lazer = new Lazer(PortMap.LASER_CAN);
        SubsystemManager.registerSubsystem(this);
    }

    public void EffectorRun(){
        effectorMotorLower.set(effectorUpper);
        effectorMotorUpper.set(effectorLower);
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
        SmartDashboard.putNumber("Laser Detected Distance", lazer.laserDistance());
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
