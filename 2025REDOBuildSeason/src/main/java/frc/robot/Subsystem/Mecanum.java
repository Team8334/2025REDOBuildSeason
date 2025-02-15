package frc.robot.Subsystem;

import frc.robot.Data.PortMap;
import edu.wpi.first.units.Units;
import frc.robot.Devices.NEOSparkMaxMotor;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;

public class Mecanum implements Subsystem {

    private static Mecanum instance = null;

    private NEOSparkMaxMotor rearLeftMotor = new NEOSparkMaxMotor(PortMap.MECANUM_BACK_LEFT);
    private NEOSparkMaxMotor frontRightMotor = new NEOSparkMaxMotor(PortMap.MECANUM_FRONT_RIGHT);
    private NEOSparkMaxMotor rearRightMotor = new NEOSparkMaxMotor(PortMap.MECANUM_BACK_RIGHT);
    private NEOSparkMaxMotor frontLeftMotor = new NEOSparkMaxMotor(PortMap.MECANUM_FRONT_LEFT);

    private double frontLeft;
    private double frontRight;
    private double rearLeft;
    private double rearRight;

    private double rotationScalar = ((2 * Math.PI) / 60.0 / 10.71);

    private double MAX_SPEED_CONSTANT_FORWARD = 20; //TO DO: calculate this (meters per sec)
    private double MAX_SPEED_CONSTANT_STRAFE = 20; //Meters per sec. Should calculate this too.
    private double MAX_SPEED_CONSTANT_ROTATION = 40; //Radians per sec.

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
            frontLeftMotor.setInverted(true);
            rearLeftMotor.setInverted(true);
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

        frontLeftMotor.set(frontLeft);
        frontRightMotor.set(frontRight);
        rearLeftMotor.set(rearLeft);
        rearRightMotor.set(rearRight);
        System.out.println("DRIVE IS BEING CALLED");
    }

    public void driveWithSpeed(double forward, double strafe, double rotation){
        
        ChassisSpeeds speeds = new ChassisSpeeds(forward*MAX_SPEED_CONSTANT_FORWARD, strafe*MAX_SPEED_CONSTANT_STRAFE, rotation*MAX_SPEED_CONSTANT_ROTATION);
        MecanumDriveWheelSpeeds wheelSpeeds = m_kinematics.toWheelSpeeds(speeds);

        frontLeft = wheelSpeeds.frontLeftMetersPerSecond;
        frontRight = wheelSpeeds.frontRightMetersPerSecond;
        rearLeft = wheelSpeeds.rearLeftMetersPerSecond;
        rearRight = wheelSpeeds.rearRightMetersPerSecond;

        frontLeftMotor.setWheelRotationSpeed(frontLeft);
        frontRightMotor.setWheelRotationSpeed(frontRight);
        rearLeftMotor.setWheelRotationSpeed(rearLeft);
        rearRightMotor.setWheelRotationSpeed(rearRight);
    }

    @Override
    public void update() {
        
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

}
