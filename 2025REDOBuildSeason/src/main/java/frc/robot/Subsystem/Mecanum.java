package frc.robot.Subsystem;

import frc.robot.Data.PortMap;
import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Devices.NEOSparkMaxMotor;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import frc.robot.Devices.Gyro;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.Subsystem.Elevator;

public class Mecanum implements Subsystem {

    private static Mecanum instance = null;
    private Gyro gyro;

    private NEOSparkMaxMotor rearLeftMotor;
    private NEOSparkMaxMotor frontRightMotor;
    private NEOSparkMaxMotor rearRightMotor; 
    private NEOSparkMaxMotor frontLeftMotor;

    private double frontLeft;
    private double frontRight;
    private double rearLeft;
    private double rearRight;

    private double rotationScalar = ((2 * Math.PI) / 60.0 / 10.71);
    private double desiredAngleVelocity;
    private double currentAngleVelocity;
    private double desiredAngle;

    private PIDController speedControlPID = new PIDController(5.5, 0, 0.00001);

    private double MAX_SPEED_CONSTANT_FORWARD = 20; //TO DO: calculate this (meters per sec)
    private double MAX_SPEED_CONSTANT_STRAFE = 20; //Meters per sec. Should calculate this too.
    private double MAX_SPEED_CONSTANT_ROTATION = 3*Math.PI; //Radians per sec.

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
            gyro = Gyro.getInstance();
    }

    /*
     * currently not functional
     */
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
    }

    // @Override
    // public void update() {
    //     if(Elevator.instance.height() > PortMap.MAX_HEIGHT_FOR_DRIVING) {
    //         frontLeftMotor.set(0.0);
    //         frontRightMotor.set(0.0);
    //         rearLeftMotor.set(0.0);
    //         rearRightMotor.set(0.0);
    //     }
    //     frontLeftMotor.set(frontLeft);
    //     frontRightMotor.set(frontRight);
    //     rearLeftMotor.set(rearLeft);
    //     rearRightMotor.set(rearRight);
    //     System.out.println("DRIVE IS BEING CALLED");
    // }

    private double rotationControl(double rotationInput){
        currentAngleVelocity = (gyro.getAngleVelocityDegrees()*(Math.PI/180));
        double currentAngle = (gyro.getAngleDegrees()*(Math.PI/180));
        //maybe try is sftrafe over a threshold and rotationInput is over a threshold
        if (Math.abs(currentAngleVelocity) >= 0.15 && Math.abs(rotationInput) >= 0) {
            desiredAngle = currentAngle;
        }
        if(Math.abs(rotationInput) <= .2){
            double correction = speedControlPID.calculate(currentAngle, desiredAngle);
            SmartDashboard.putNumber(getName()+"/correction", correction);
            SmartDashboard.putNumber(getName()+"/desiredAngle", desiredAngle);
            SmartDashboard.putNumber(getName()+"/currentAngle", currentAngle);
            SmartDashboard.putNumber(getName()+"/currentAngleVelocity", currentAngleVelocity);

            return correction;
        }
        else{
            return rotationInput;
        }
    }

    public void driveWithSpeed(double forward, double strafe, double rotation){
        
        ChassisSpeeds speeds = new ChassisSpeeds(forward*MAX_SPEED_CONSTANT_FORWARD, strafe*MAX_SPEED_CONSTANT_STRAFE, rotationControl(rotation)*MAX_SPEED_CONSTANT_ROTATION);
        MecanumDriveWheelSpeeds wheelSpeeds = m_kinematics.toWheelSpeeds(speeds);

        frontLeft = wheelSpeeds.frontLeftMetersPerSecond;
        frontRight = wheelSpeeds.frontRightMetersPerSecond;
        rearLeft = wheelSpeeds.rearLeftMetersPerSecond;
        rearRight = wheelSpeeds.rearRightMetersPerSecond;
        SmartDashboard.putNumber("Mecanum/frontLeft", frontLeft);
        SmartDashboard.putNumber("Mecanum/frontRight", frontRight);
        SmartDashboard.putNumber("Mecanum/rearLeft", rearLeft);
        SmartDashboard.putNumber("Mecanum/rearRight", rearRight);

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
        rearLeftMotor = new NEOSparkMaxMotor(PortMap.MECANUM_BACK_LEFT);
        frontRightMotor = new NEOSparkMaxMotor(PortMap.MECANUM_FRONT_RIGHT);
        rearRightMotor = new NEOSparkMaxMotor(PortMap.MECANUM_BACK_RIGHT);
        frontLeftMotor = new NEOSparkMaxMotor(PortMap.MECANUM_FRONT_LEFT);
        frontLeftMotor.setInverted(true);
        rearLeftMotor.setInverted(true);
        desiredAngle = (gyro.getAngleDegrees()*(Math.PI/180));
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
