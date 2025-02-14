package frc.robot.Subsystem;

import frc.robot.Data.PortMap;
import frc.robot.Devices.NEOSparkMaxMotor;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import frc.robot.Subsystem.Elevator;

public class Mecanum implements Subsystem {

    private static Mecanum instance = null;

    private NEOSparkMaxMotor rearLeftMotor = new NEOSparkMaxMotor(2);
    private NEOSparkMaxMotor frontRightMotor = new NEOSparkMaxMotor(3);
    private NEOSparkMaxMotor rearRightMotor = new NEOSparkMaxMotor(3);
    private NEOSparkMaxMotor frontLeftMotor = new NEOSparkMaxMotor(1);

    private double frontLeft;
    private double frontRight;
    private double rearLeft;
    private double rearRight;

    // distance of wheels from center in meters
    Translation2d m_frontLeftLocation = new Translation2d(0.381, 0.381); // these are not actually measured
    Translation2d m_frontRightLocation = new Translation2d(0.381, -0.381); // these are not actually measured
    Translation2d m_backLeftLocation = new Translation2d(-0.381, 0.381); // these are not actually measured
    Translation2d m_backRightLocation = new Translation2d(-0.381, -0.381); // these are not actually measured

    // Creating my kinematics object using the wheel locations.
    MecanumDriveKinematics m_kinematics = new MecanumDriveKinematics(
            m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation);

    public static Mecanum getInstance() {
        if (instance == null) {
            instance = new Mecanum();
        }
        return instance;
    }

    public Mecanum() {
            SubsystemManager.registerSubsystem(this);
    }

    public void drive(double forward, double strafe, double rotation) {
        // Example chassis speeds: 1 meter per second forward, 3 meters
        // per second to the left, and rotation at 1.5 radians per second
        // counterclockwise.
        ChassisSpeeds speeds = new ChassisSpeeds(forward, strafe, rotation);
        // Convert to wheel speeds
        MecanumDriveWheelSpeeds wheelSpeeds = m_kinematics.toWheelSpeeds(speeds);
        // Get the individual wheel speeds
        frontLeft = wheelSpeeds.frontLeftMetersPerSecond;
        frontRight = wheelSpeeds.frontRightMetersPerSecond;
        rearLeft = wheelSpeeds.rearLeftMetersPerSecond;
        rearRight = wheelSpeeds.rearRightMetersPerSecond;
    }

    @Override
    public void update() {
        if(Elevator.instance.height() > PortMap.MAX_HEIGHT_FOR_DRIVING) {
            frontLeftMotor.set(0.0);
            frontRightMotor.set(0.0);
            rearLeftMotor.set(0.0);
            rearRightMotor.set(0.0);
        }
        frontLeftMotor.set(frontLeft);
        frontRightMotor.set(frontRight);
        rearLeftMotor.set(rearLeft);
        rearRightMotor.set(rearRight);
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
        return "mecanum";
    }
    public boolean moving() {
        if(frontRight >= PortMap.MOVING_THRESHOLD || frontLeft >= PortMap.MOVING_THRESHOLD || rearLeft >= PortMap.MOVING_THRESHOLD || rearRight >= PortMap.MOVING_THRESHOLD) {
            return true;
        }
        return false;
    }

}
