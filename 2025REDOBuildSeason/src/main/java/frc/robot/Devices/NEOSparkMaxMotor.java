package frc.robot.Devices;

import com.revrobotics.spark.*;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NEOSparkMaxMotor {
    // REV robotics changed "CANSparkMax" to "SparkMax"

    private SparkMax m_motor;
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
        SmartDashboard.putNumber(this.getName()+"/set speed", speed);
        if (m_motor == null) {
            System.out.println("YOU SET THE SPEED OF A NONEXISTANT MOTOR: " + CANID);
        } else {
            m_motor.set(speed);
            System.out.println("Speed is " + speed);
        }
    }
    public void setInverted(boolean isInverted){
        this.isInverted = isInverted;
        
    }

}
