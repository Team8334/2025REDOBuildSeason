package frc.robot.Subsystem;

import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.simulation.PWMSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.math.system.plant.DCMotor;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;


import frc.robot.Devices.NEOSparkMaxMotor;

public class Elevator implements Subsystem {
    //note the sim stuff is only to test. To do for real robot take out the constants

    public static Elevator instance = null;

    private final DCMotor m_elevatorGearbox = DCMotor.getNEO(4);

    public static final int kEncoderAChannel = 0;
    public static final int kEncoderBChannel = 1;
    public static final int kJoystickPort = 0;

    public static final double kElevatorKp = 0;
    public static final double kElevatorKi = 0;
    public static final double kElevatorKd = 0;

    public static final double kElevatorkS = 0.0; //volts  (take this out for real)
    public static final double kElevatorkG = 0.0; // volts (take this out for real)
    public static final double kElevatorkV = 0.0; //volt per velocity (take this out for real)
    public static final double kElevatorkA = 0.0; // bolt per acceleration (take this out for real)

    public static final double kElevatorGearing = 0.0; //source had 10
    public static final double kElevatorDrumRadius = Units.inchesToMeters(2.0);
    public static final double kCarriageMass = 0.0; // it's in kg

    public static final double kSetpointMeters = 0.75; // constant setpoint
    public static final double kMinElevatorHeightMeters = 0.0; //safe guard could replace this with encoder
    public static final double kMaxElevatorHeightMeters = 0.0; // safe guard could replace this with encoder

    public static final double kElevatorEncoderDistPerPulse = 2.0 * Math.PI * kElevatorDrumRadius / 4096; // place holder for encoder. this is for the sim
    
    private final ProfiledPIDController m_PIDController = 
        new ProfiledPIDController(kElevatorKp,kElevatorKi,kElevatorKd, new TrapezoidProfile.Constraints(0.0, 0.0)); // need to find values for these

    ElevatorFeedforward m_feedforward =
        new ElevatorFeedforward(
            kElevatorkS,
            kElevatorkG,
            kElevatorkV,
            kElevatorkA);

    private final Encoder m_encoder =
        new Encoder(kEncoderAChannel, kEncoderBChannel);

        private NEOSparkMaxMotor elevatorMotor;

    //Simulation part of the code
    private final ElevatorSim m_elevatorSim =
        new ElevatorSim(
            m_elevatorGearbox, 
            kElevatorGearing, 
            kCarriageMass, 
            kElevatorDrumRadius, 
            kMinElevatorHeightMeters, 
            kMaxElevatorHeightMeters, 
            true, 
            0, 
            0.01, 
            0.0);
    // end of simulation part

    @Override
    public void update() {
        m_encoder.setDistancePerPulse(kElevatorEncoderDistPerPulse);
        m_elevatorSim.update(.020);
        RoboRioSim.setVInVoltage(BatterySim.calculateDefaultBatteryLoadedVoltage(m_elevatorSim.getCurrentDrawAmps()));
    }

    @Override
    public void initialize() {
       //m_elevatorSim.setInput(m_motorSim.getSpeed()* RobotController.getBatteryVoltage());
       //m_encoderSim.setDistance(m_elevatorSim.getPositionMeters());
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
        return "example subsystem";
    }
    public float height() {
        return (float)(0.0 / 0.0);
    }
}
