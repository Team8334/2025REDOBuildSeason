package frc.robot.Subsystem;

import frc.robot.Data.EncoderValues;
import frc.robot.Devices.ModifiedEncoders;
import frc.robot.Data.PortMap;
import frc.robot.Devices.Controller;
import frc.robot.Devices.NEOSparkMaxMotor;
import frc.robot.Data.PortMap;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

public class Elevator implements Subsystem {
    public static Elevator instance = null;

    public static final double kElevatorKp = 10;
    public static final double kElevatorKi = 2.5;
    public static final double kElevatorKd = 0;

    public static final double kElevatorkS = 0.0; //volts 
    public static final double kElevatorkG = 0.62; // volts 
    public static final double kElevatorkV = 1.19; //volt per velocity 
    public static final double kElevatorkA = 0.06; // volt per acceleration 

    public static final double kElevatorDrumRadius = Units.inchesToMeters(1.0);
    public static final double kElevatorEncoderDistPerPulse = 2.0 * Math.PI * kElevatorDrumRadius / 4096;

    private ModifiedEncoders encoder;
    private NEOSparkMaxMotor elevatorMotorOne;
    private NEOSparkMaxMotor elevatorMotorTwo;
    private Controller controller;

    private double elevatorOne;
    private double elevatorTwo;

    private final ProfiledPIDController m_controller = new ProfiledPIDController(kElevatorKp, kElevatorKi, kElevatorKd, new TrapezoidProfile.Constraints(4.2, 6));
    ElevatorFeedforward m_feedforward = new ElevatorFeedforward(kElevatorkS, kElevatorkG, kElevatorkV, kElevatorkA);

    private Mechanism2d m_mech2d;
    private MechanismRoot2d m_mech2dRoot;
    private MechanismLigament2d m_elevatorMech2d;

    public static Elevator getInstance() {
        if(instance == null) {
            instance = new Elevator();
        }
        return instance;
    }
    
    public Elevator() //sets up encoder turns to distance
    {
        encoder = new ModifiedEncoders(PortMap.ELEVATOR_ENCODER);
        elevatorMotorOne = new NEOSparkMaxMotor(PortMap.ELEVATOR_MOTOR_ONE);
        elevatorMotorTwo = new NEOSparkMaxMotor(PortMap.ELEVATOR_MOTOR_TWO);
        
        SubsystemManager.registerSubsystem(this);

        encoder.zeroCycle();
    }

    public void elevatorZero(){
        encoder.zeroCycle();
    }

    public void safetyCheck(){
        
    }
    
    public void updateTelemetry(){
    
    }
    
    @Override
    public void update() 
    {
        SmartDashboard.putBoolean("Elevator/Connected", encoder.isConnected());
        SmartDashboard.putNumber("Elevator/Frequency", encoder.getFrequency());
        SmartDashboard.putNumber("Elevator/Output",-1* encoder.getExtendedCyclePosition());
        SmartDashboard.putNumber("Elevator/ShiftedOutput", encoder.shiftedOutput());
    }

    public void reachGoal(double goal) {
        m_controller.setGoal(goal);
        double pidOutput = m_controller.calculate(-1 * encoder.getExtendedCyclePosition());
        double feedforwardOutput = m_feedforward.calculate(m_controller.getSetpoint().velocity);
        elevatorMotorOne.setVoltage(feedforwardOutput + pidOutput);
        elevatorMotorTwo.setVoltage((feedforwardOutput + pidOutput)*-1);
        SmartDashboard.putNumber("Elevator/motorOneVoltage", feedforwardOutput+pidOutput);
        SmartDashboard.putNumber("Elevator/goal", goal);
    }
    
    public void stop() {
        elevatorMotorOne.set(0.0);
        elevatorMotorTwo.set(0.0);
    }
    
    @Override
    public void initialize() {
        m_mech2d = new Mechanism2d(20, 50);
        m_mech2dRoot = m_mech2d.getRoot("Elevator Root", 10, 0);
        m_elevatorMech2d = new MechanismLigament2d("Elevator", -1* encoder.get(), 90);
        SmartDashboard.putData("Elevator Sim", m_mech2d);
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
        return "Elevator";
    }
    public float height() {
        return (float)(0.0 / 0.0);
    }
}
