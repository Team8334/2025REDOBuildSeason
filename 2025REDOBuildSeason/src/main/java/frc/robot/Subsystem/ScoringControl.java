package frc.robot.Subsystem;

import frc.robot.Data.PortMap;

import frc.robot.Devices.NEOSparkMaxMotor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystem.Elevator;
import frc.robot.Data.Debug;
import frc.robot.Data.EncoderValues;
import frc.robot.Data.States;
import frc.robot.Devices.NEOSparkMaxMotor;
import frc.robot.Devices.Laser;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ScoringControl implements Subsystem {

    //case statement has nothing to make this class work. delete this comment when you have added the necessary changes.
    //much testing is need for this branch. delete this comment when you have tested it.

    private static ScoringControl instance = null;

    Timer timer;
    Elevator elevator;
    Laser laser;

    private NEOSparkMaxMotor rampLeftMotor; 
    private NEOSparkMaxMotor rampRightMotor;
    private NEOSparkMaxMotor effectorMotor;

    public double rampRight;
    public double rampLeft;
    public double effector;
    public double CORAL_DETECT_THRESHOLD = 10; //in mm
    public double PASSING_DELAY = 45;
    public double manualEffectorSpeed;

    public States elevatorState;
    public States effectorState;


    public String monitoringState;
    public String monitoringEffectorState;

    public boolean elevatorIsSafe;
    public boolean timerStart;
    public boolean pieceDetected;

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

    public void setElevatorState(States elevatorState){
        this.elevatorState = elevatorState;
    }

    public void setEffectorState(States effectorState){
        this.effectorState = effectorState;
    }

    public void setManualEffectorSpeed(double speed){
       effector = (speed/-2);
    }
    public void ElevatorStateProcessing(){
        switch (elevatorState){

            case PASSIVE:
                elevator.stop();
                monitoringState = "Passive";
                break;

            case RAMP:
                elevator.reachGoal(EncoderValues.ELEVATOR_RAMP);
                monitoringState = "Ramp";
                break;

            case SCOREL1:
                elevator.reachGoal(EncoderValues.ELEVATOR_L1);
                monitoringState = "Score L1";
                break;
            
            case SCOREL2:
                elevator.reachGoal(EncoderValues.ELEVATOR_L2);
                monitoringState = "Score L2";
                break;

            case SCOREL3:
                elevator.reachGoal(EncoderValues.ELEVATOR_L3);
                monitoringState = "Score L3";
                break;

            case SCOREL4:
                elevator.reachGoal(EncoderValues.ELEVATOR_L4);
                monitoringState = "Score L4";
                break;

            case LOWERALGAE:
                elevator.reachGoal(EncoderValues.ELEVATOR_LOWER_ALGAE);
                monitoringState = "Lower Algae";
                break;

            case UPPERALGAE:
                elevator.reachGoal(EncoderValues.ELEVATOR_UPPER_ALGAE);
                monitoringState = "Upper Algae";
                break;
        
            case BARGE:
                elevator.reachGoal(EncoderValues.ELEVATOR_BARGE);
                monitoringState = "Barge";
                break;

        }

        if(Debug.debug){
        SmartDashboard.putString("elevatorScoringState", monitoringState);
        }
    }

    public void setEffectorSpeed(double manualSpeed){
        manualEffectorSpeed = manualSpeed;
    }

    public void EffectorStateProcessing(){
        switch (effectorState){

            case NOTHING:
                effector = 0.0;
                rampLeft = 0.0;
                rampRight = 0.0;
                monitoringEffectorState = "nothing";
                break;

            case WAITINGINRAMP:
                effector = 0.0;
                rampLeft = 0.1;
                rampRight = 0.1;
                monitoringEffectorState = "waiting in ramp";
                break;

            case PASSING:
                if(pieceDetected){
                    effector = 0.0;
                    rampLeft = 0.0;
                    rampRight = 0.0;
                }
                else{
                    effector =0.22;
                    rampLeft = 0.3;
                    rampRight = -0.3;
                }
                //if (!timerStart && pieceDetected){
                //    timerStart = true;
                //    timer.reset();
                //    timer.start();
                //}
                //if (timerStart && pieceDetected && timer.get() >= PASSING_DELAY){
                //    timerStart = false;
                //    timer.stop();
                //    setEffectorState(States.WAITINGINEFFECTOR);
                //}
                monitoringEffectorState = "passing";
                break;

            case WAITINGINEFFECTOR:
                effector = 0.0;
                rampLeft = 0.0;
                rampRight = 0.0;
                monitoringEffectorState = "waiting in effector";
                break;

            case MANUAL:
                effector = manualEffectorSpeed;
                rampLeft = manualEffectorSpeed;
                rampRight = manualEffectorSpeed;
                monitoringEffectorState = "manual";
            break;

            case SCORING:
                if(pieceDetected){
                    effector = 0.5;
                    rampLeft = 0.0;
                    rampRight = 0.0;
                } else {
                    effector = 0.0;
                    rampLeft = 0.0;
                    rampRight = 0.0;
                }
                monitoringEffectorState = "scoring";
                break;
            
            case REVERSE:
                effector = -0.3;
                rampLeft = 0.0;
                rampRight = 0.0;
                monitoringEffectorState = "reverse";
                break;

            case RAMPREVERSE:
                effector = -0.3;
                rampLeft = -0.3;
                rampRight = 0.3;
                break;

            case DEALGAEFYING:
                effector = 0.3;
                rampLeft = 0.0;
                rampRight = 0.0;
                monitoringEffectorState = "De-algaefying";
                break;

            case YEETINGALGAE:
                effector = -0.4;
                rampLeft = 0.0;
                rampRight = 0.0;
                monitoringEffectorState = "yeet";
                break;
            
            case HOLDINGALGAE:
                effector = 0.34;
                rampLeft = 0.0;
                rampRight = 0.0;
                monitoringEffectorState = "hold";
                break;
                
        }

        if(Debug.debug){
        SmartDashboard.putString("effectorState", monitoringEffectorState);
        }
    }
    
    public boolean coralDetect(){
        if(laser.laserDistance() < CORAL_DETECT_THRESHOLD){
            return pieceDetected = true;
        }else{
            return pieceDetected = false;
        }
    }

    @Override
    public void update() {
        coralDetect();
        ElevatorStateProcessing();
        EffectorStateProcessing();
        effectorMotor.set(effector);
        rampRightMotor.set(rampRight);
        rampLeftMotor.set(rampLeft);

        if(Debug.debug){
        SmartDashboard.putNumber("Laser Detected Distance", laser.laserDistance());
        }
    }

    @Override
    public void initialize() {
        laser = new Laser(PortMap.LASER_CAN);
        elevator = Elevator.getInstance();
        rampLeftMotor = new NEOSparkMaxMotor(PortMap.RAMP_LEFT);
        rampRightMotor = new NEOSparkMaxMotor(PortMap.RAMP_RIGHT);
        effectorMotor = new NEOSparkMaxMotor(PortMap.EFFECTOR);
        timer= new Timer();
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
