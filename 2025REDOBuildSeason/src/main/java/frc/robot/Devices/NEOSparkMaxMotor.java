package frc.robot.Devices;

import com.revrobotics.spark.*;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NEOSparkMaxMotor {
    // REV robotics changed "CANSparkMax" to "SparkMax"

    private SparkMax m_motor;
    private SparkClosedLoopController closedLoopController;
    private RelativeEncoder encoder;
    private SparkMaxConfig motorConfig;
    private int CANID;
    private boolean isInverted;

    public NEOSparkMaxMotor(int CANID){
        
        this.CANID = CANID;
        try {
            m_motor = new SparkMax(CANID,SparkLowLevel.MotorType.kBrushless);
        }
        catch(Exception e) {
            m_motor = null;
            System.out.println("SparkMax not found: " + CANID);
        }

        encoder = m_motor.getEncoder();
        motorConfig = new SparkMaxConfig();
        closedLoopController = m_motor.getClosedLoopController();

        motorConfig.encoder
            .positionConversionFactor((2 * Math.PI*.1) / 60.0 / 10.71)
            .velocityConversionFactor((2 * Math.PI*.1) / 60.0 / 10.71);
            
        motorConfig.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            // Set PID values for position control. We don't need to pass a closed loop
            // slot, as it will default to slot 0.
            .p(0.035)
            .i(0)
            .d(0)
            .velocityFF(1.0 / 5676)
            .outputRange(-1, 1);  
        
        m_motor.configure(motorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
    }
    public String getName(){
        return "SparkMax CAN ID:" + CANID;
    }
    public boolean isOperational(){
       if (m_motor == null){
        return false;
       }
       else {
        return true;
       }
    }

    public void set(double speed){
       
        if (isInverted){
            speed*=-1;
        }
        //System.out.println("Speed is being set");
        SmartDashboard.putNumber(this.getName()+"/set speed", speed);
        
        if (m_motor == null) {
            System.out.println("YOU SET THE SPEED OF A NONEXISTANT MOTOR: " + CANID);
        } 
        else {
            m_motor.set(speed);
           // System.out.println("Speed is " + speed);
        }
    }

    public void setWheelRotationSpeed(double speed){
        if (isInverted){
            speed*=-1;
        }
        closedLoopController.setReference(speed, ControlType.kVelocity);
        SmartDashboard.putNumber(this.getName()+"/Actual Velocity", encoder.getVelocity());
        SmartDashboard.putNumber(this.getName()+"/Actual Velocity", encoder.getPosition());
        SmartDashboard.putNumber(this.getName()+"/Target Velocity", speed);
    }

    public void setInverted(boolean isInverted){
        this.isInverted = isInverted;
    }

}
