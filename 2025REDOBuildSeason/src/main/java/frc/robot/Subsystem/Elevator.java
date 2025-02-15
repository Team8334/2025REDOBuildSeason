package frc.robot.Subsystem;

import frc.robot.Data.EncoderValues;
import frc.robot.Data.PortMap;
import frc.robot.Devices.Controller;
//import frc.robot.Devices.NEOSparkMaxMotor;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.RobotController;

public class Elevator implements Subsystem {
    //note the sim stuff is only to test. To do for real robot take out the constants

    public static Elevator instance = null;

    //private Controller controller = new Controller(0);

    public static final int kEncoderAChannel = 0;
    public static final int kEncoderBChannel = 1;

    public static final double kElevatorKp = 10;
    public static final double kElevatorKi = 2.5;
    public static final double kElevatorKd = 0;

    public static final double kElevatorkS = 0.0; //volts 
    public static final double kElevatorkG = 2.28; // volts 
    public static final double kElevatorkV = 3.07; //volt per velocity 
    public static final double kElevatorkA = 0.41; // bolt per acceleration 

    public static final double kElevatorGearing = 10.0; 
    public static final double kElevatorDrumRadius = Units.inchesToMeters(1.0); // change this to actual
    public static final double kCarriageMass = 2; 

    public static final double kSetpointMeters = 0.75; // constant setpoint
    public static final double kMinElevatorHeightMeters = 0.0; //safe guard could replace this with encoder
    public static final double kMfaxElevatorHeightMeters = 10; // safe guard could replace this with encoder. change to actual height
    public static final double kElevatorEncoderDistPerPulse = 2.0 * Math.PI * kElevatorDrumRadius / 4096; // place holder for encoder. this is for the sim
    
    //private NEOSparkMaxMotor elevatorMotor = new NEOSparkMaxMotor(2);
    //private final DCMotor m_elevatorGearbox = DCMotor.getNEO(2); //sets the motor gearbox

    private final ProfiledPIDController m_controller = 
        new ProfiledPIDController(kElevatorKp, kElevatorKi, kElevatorKd, new TrapezoidProfile.Constraints(2.45, 2.45)); // need to find values for these

    ElevatorFeedforward m_feedforward = 
        new ElevatorFeedforward(kElevatorkS, kElevatorkG, kElevatorkV, kElevatorkA);

    private final Encoder m_encoder = 
        new Encoder(kEncoderAChannel, kEncoderBChannel);

    //private final ElevatorSim m_elevatorSim = 
       // new ElevatorSim(m_elevatorGearbox, kElevatorGearing, kCarriageMass, kElevatorDrumRadius, kMinElevatorHeightMeters, kMaxElevatorHeightMeters, true, 0, 0.01, 0.0);
    

    //private final Encoder encoder = new Encoder(8);
    private final PWM m_motor = new PWM(0);

    private final Mechanism2d m_mech2d = new Mechanism2d(20, 50);
    private final MechanismRoot2d m_mech2dRoot = m_mech2d.getRoot("Elevator Root", 10, 0);
    //private final MechanismLigament2d m_elevatorMech2d = m_mech2dRoot.append(
       // new MechanismLigament2d("Elevator", m_elevatorSim.getPositionMeters(), 90));

    public static Elevator getInstance() {
        if(instance == null) {
            instance = new Elevator();
        }
        return instance;
    }
    
    public Elevator() //sets up encoder tursn to distance
    {
        SubsystemManager.registerSubsystem(this);
        m_encoder.setDistancePerPulse(4.0/256.0);
        SmartDashboard.putData("Elevator Sim", m_mech2d);
    }

    public void updateTelemetry() //puts distance for sim
    {
        //m_elevatorMech2d.setLength(m_encoder.getDistance());
    }

    @Override
    public void update() 
    {
        //m_elevatorSim.setInput(m_motor.getSpeed()* RobotController.getBatteryVoltage());
        //m_elevatorSim.update(.020);
        m_encoder.getDistance();
        m_encoder.get();
        System.out.println(m_encoder.getDistance());
        System.out.println(m_encoder.getRate());
        System.out.println(m_encoder.get());
        System.out.println("elise was here");
        /*if (controller.getAButtonPressed()) {
            reachGoal(EncoderValues.ELEVATOR_L2);
        } else if (controller.getBButtonPressed()){
            reachGoal(EncoderValues.ELEVATOR_L2);
        } else if (controller.getXButtonPressed()){
            reachGoal(EncoderValues.ELEVATOR_L3);
        } else if (controller.getYButtonPressed()){
            reachGoal(EncoderValues.ELEVATOR_L4);
        } else {
            reachGoal(0);
        }*/

        // set the case for use of autonoumous. Like set state for bottom.
    }

    public void reachGoal(double goal) {
        m_controller.setGoal(goal);
        double pidOutput = m_controller.calculate(m_encoder.getDistance());
        double feedforwardOutput = m_feedforward.calculate(m_controller.getSetpoint().velocity);
    }


    public void stop() {
        m_controller.setGoal(0.0);
        //elevatorMotor.set(0.0);
    }

    @Override
    public void initialize() {
       //m_elevatorSim.setInput(m_motor.getSpeed()* RobotController.getBatteryVoltage());
       m_encoder.getDistance();
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
