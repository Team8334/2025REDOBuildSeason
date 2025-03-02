package frc.robot.Interfaces;

public interface Vision{

    public class TargetMeasurements{

        double TargetPose;
        double TargetDistance2d;
        double TargetX;
        double TargetY;
        double TargetArea;
        
        int TargetID;
    }

    public class RobotMeasurements{

        double RobotPose_Field2d;
        double RobotPose_Target2d;
    }

}
