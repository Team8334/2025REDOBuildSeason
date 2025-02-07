package frc.robot.Subsystem;

import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.simulation.PWMSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.RobotController;

public class Elevator implements Subsystem {
    //note the sim stuff is only to test. To do for real robot take out the constants

    private static Elevator instance = null;

    public static final int kMotorPort = 0; //this is for the simulation, needs to be replaced for actual
    public static final int kEncoderAChannel = 0; // these two are also for the simulation. needs to be replaced
    public static final int kEncoderBChannel = 1;
    public static final int kJoystickPort = 0; // for simulation. needs to be replaced for actual

    public static final double kElevatorKp = 5;
    public static final double kElevatorKi = 0;
    public static final double kElevatorKd = 0;

    public static final double kElevatorkS = 0.0; //volts  (take this out for real)
    public static final double kElevatorkG = 0.762; // volts (take this out for real)
    public static final double kElevatorkV = 0.762; //volt per velocity (take this out for real)
    public static final double kElevatorkA = 0.0; // bolt per acceleration (take this out for real)

    public static final double kElevatorGearing = 10.0; 
    public static final double kElevatorDrumRadius = Units.inchesToMeters(2.0); // change this to actual
    public static final double kCarriageMass = 4.0; // it's in kg. situate to cargo

    public static final double kSetpointMeters = 0.75; // constant setpoint
    public static final double kMinElevatorHeightMeters = 0.0; //safe guard could replace this with encoder
    public static final double kMaxElevatorHeightMeters = 1.25; // safe guard could replace this with encoder. change to actual height
    public static final double kElevatorEncoderDistPerPulse = 2.0 * Math.PI * kElevatorDrumRadius / 4096; // place holder for encoder. this is for the sim
    


    private final DCMotor m_elevatorGearbox = DCMotor.getNEO(2); //sets the motor for the simulation

    private final ProfiledPIDController m_controller = 
        new ProfiledPIDController(kElevatorKp, kElevatorKi, kElevatorKd, new TrapezoidProfile.Constraints(2.45, 2.45)); // need to find values for these

    ElevatorFeedforward m_feedforward = 
        new ElevatorFeedforward(kElevatorkS, kElevatorkG, kElevatorkV, kElevatorkA);

    private final Encoder m_encoder = 
        new Encoder(kEncoderAChannel, kEncoderBChannel);

    private  PWMSparkMax m_motor = new PWMSparkMax(kMotorPort); // sets up motor

    private final ElevatorSim m_elevatorSim = 
        new ElevatorSim(m_elevatorGearbox, kElevatorGearing, kCarriageMass, kElevatorDrumRadius, kMinElevatorHeightMeters, kMaxElevatorHeightMeters, true, 0, 0.01, 0.0);


    private final EncoderSim m_encoderSim = new EncoderSim(m_encoder);
    private final PWMSim m_motorSim = new PWMSim(m_motor);

    private final Mechanism2d m_mech2d = new Mechanism2d(20, 50);
    private final MechanismRoot2d m_mech2dRoot = m_mech2d.getRoot("Elevator Root", 10, 0);
    private final MechanismLigament2d m_elevatorMech2d = m_mech2dRoot.append(
        new MechanismLigament2d("Elevator", m_elevatorSim.getPositionMeters(), 90));

    public Elevator() //sets up encoder tursn to distance
    {
        m_encoder.setDistancePerPulse(kElevatorEncoderDistPerPulse);
        SmartDashboard.putData("Elevator Sim", m_mech2d);
    }

    public void updateTelemetry() //puts distance for sim
    {
        m_elevatorMech2d.setLength(m_encoder.getDistance());
    }

    @Override
    public void update() 
    {
        m_elevatorSim.setInput(m_motorSim.getSpeed()* RobotController.getBatteryVoltage());
        m_elevatorSim.update(.020);
        m_encoderSim.setDistance(m_elevatorSim.getPositionMeters());
        RoboRioSim.setVInVoltage(BatterySim.calculateDefaultBatteryLoadedVoltage(m_elevatorSim.getCurrentDrawAmps()));
    }

    public void reachGoal(double goal) {
        m_controller.setGoal(goal);
        double pidOutput = m_controller.calculate(m_encoder.getDistance());
        double feedforwardOutput = m_feedforward.calculate(m_controller.getSetpoint().velocity);
        m_motor.setVoltage(pidOutput + feedforwardOutput);
    }

    public void stop() {
        m_controller.setGoal(0.0);
        m_motor.set(0.0);
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
        return "Elevator";
    }
}
