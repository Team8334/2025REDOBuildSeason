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
    public static final double kElevatorkG = 2.28; // volts 
    public static final double kElevatorkV = 3.07; //volt per velocity 
    public static final double kElevatorkA = 0.41; // volt per acceleration 

    public static final double kElevatorDrumRadius = Units.inchesToMeters(1.0); // change this to actual
    public static final double kElevatorEncoderDistPerPulse = 2.0 * Math.PI * kElevatorDrumRadius / 4096; // place holder for encoder. this is for the sim

    private ModifiedEncoders encoder;
    private NEOSparkMaxMotor elevatorMotorOne;
    private NEOSparkMaxMotor elevatorMotorTwo;
    private Controller controller;

    private double elevatorOne;
    private double elevatorTwo;
    public double elevatorSpeed = .15; //adjust this

    private final ProfiledPIDController m_controller = new ProfiledPIDController(kElevatorKp, kElevatorKi, kElevatorKd, new TrapezoidProfile.Constraints(2.45, 2.45));
    ElevatorFeedforward m_feedforward = new ElevatorFeedforward(kElevatorkS, kElevatorkG, kElevatorkV, kElevatorkA);

    private final Mechanism2d m_mech2d = new Mechanism2d(20, 50);
    private final MechanismRoot2d m_mech2dRoot = m_mech2d.getRoot("Elevator Root", 10, 0);
    private final MechanismLigament2d m_elevatorMech2d = new MechanismLigament2d("Elevator", encoder.get(), 90);

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
        controller = new Controller(PortMap.OPERATOR_CONTROLLER);

        elevatorMotorOne.set(elevatorOne);
        elevatorMotorTwo.set(elevatorTwo);
        
        SubsystemManager.registerSubsystem(this);
        SmartDashboard.putData("Elevator Sim", m_mech2d);
    }

    public void updateTelemetry()
    {
        m_elevatorMech2d.setLength(encoder.get());
    }

    @Override
    public void update() 
    {
        SmartDashboard.putBoolean("Elevator/Connected", encoder.isConnected());
        SmartDashboard.putNumber("Elevator/Frequency", encoder.getFrequency());
        SmartDashboard.putNumber("Elevator/Output", encoder.get());
        SmartDashboard.putNumber("Elevator/ShiftedOutput", encoder.shiftedOutput());
    }

    public void reachGoal(double goal) {
        m_controller.setGoal(goal);
        double pidOutput = m_controller.calculate(encoder.extendedCycle());
        double feedforwardOutput = m_feedforward.calculate(m_controller.getSetpoint().velocity);
        //elevatorOne = elevatorSpeed;
        //elevatorTwo = -elevatorSpeed; //determine which side needs to be negative
    }


    public void stop() {
        m_controller.setGoal(0.0);
        elevatorMotorOne.set(0.0);
        elevatorMotorTwo.set(0.0);
    }

    @Override
    public void initialize() {
       //m_elevatorSim.setInput(m_motor.getSpeed()* RobotController.getBatteryVoltage());
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
