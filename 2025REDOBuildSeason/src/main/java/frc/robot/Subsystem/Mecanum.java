package frc.robot.Subsystem;

import frc.robot.Data.PortMap;
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

        System.out.println("Front left:" + frontLeft);
        System.out.println("Front right:" + frontRight);
        System.out.println("Rear left:" + rearLeft);
        System.out.println("Rear right:" + rearRight);

        frontLeftMotor.set(frontLeft*-1);
        frontRightMotor.set(frontRight);
        rearLeftMotor.set(rearLeft*-1);
        rearRightMotor.set(rearRight);

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
