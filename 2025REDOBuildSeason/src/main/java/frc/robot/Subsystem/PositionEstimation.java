package frc.robot.Subsystem;

import com.studica.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;

import frc.robot.Devices.ModifiedEndcoders;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Data.PortMap;

public class PositionEstimation {
        
    private static PositionEstimation instance = null;
    AHRS ahrs;

    private Mecanum mecanum;
    private PortMap Portmap;
    private ModifiedEncoders modifiedencoders;
    private modifiedencoders leftEncoder;
    private modifiedencoders rightEncoder;

    public static PositionEstimation getInstance()
    {
        if (instance == null)
        {
            instance = new PositionEstimation();
        }
        return instance;
    }

    public double getLeftEncoderDistance(){
        this.leftEncoder = new Encoders(Portmap.LEFTENCODER_A.portNumber, Portmap.LEFTENCODER_B.portNumber,
            "E4TEncoder");
    }

    public double getRightEncoderDistance(){
        this.rightEncoder = new Encoders(Portmap.RIGHTENCODER_A.portNumber, Portmap.RIGHTENCODER_B.portNumber,
            "E4TEncoder");
    }
    
    public PositionEstimation(){

    }

    public double getDistance(){
         return ((getLeftEncoderDistance() + getRightEncoderDistance()) / 2);
    }
}
