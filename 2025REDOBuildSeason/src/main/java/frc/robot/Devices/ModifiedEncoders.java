package frc.robot.Devices;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Data.PortMap;

import com.studica.frc.AHRS;

import edu.wpi.first.math.MathUtil;

public class ModifiedEncoders {
    
    private static ModifiedEncoders instance = null;
    AHRS ahrs;

    public static ModifiedEncoders getInstance()
    {
        if (instance == null)
        {
            instance = new ModifiedEncoders();
        }
        return instance;
    }
    
    enum output{
        getRate,
        getDistance
    }

    private Encoder encoder;
    private DutyCycleEncoder dutyCycleEncoder;
    private static double fullRange = 1;
    private static double expectedZero = 0;
    private double ratio = 1.0;

    private double saveEncoder;
    private int cycle = 0;
    
    public ModifiedEncoders(){
        
    }

    public ModifiedEncoders(int channel){
        dutyCycleEncoder = new DutyCycleEncoder(channel, fullRange, expectedZero);
        saveEncoder = 0;
        cycle = 0;
    }

    public void zeroCycle(){
        cycle = 0;
    }

    public Boolean isConnected(){
        return dutyCycleEncoder != null ? dutyCycleEncoder.isConnected() : false;
    }
    
    public int getFrequency(){
        return dutyCycleEncoder != null ? dutyCycleEncoder.getFrequency() : 0;
    }
    
    public double get(){
        return dutyCycleEncoder != null ? dutyCycleEncoder.get() : 0;
    }

    public double getExtendedCyclePosition(){
        double currentValue = dutyCycleEncoder.get();
        if((saveEncoder - currentValue) >= 0.5){
            cycle += 1;
        }
        if((saveEncoder - currentValue) < -0.5){
            cycle -= 1;
        }
        saveEncoder = currentValue;

        SmartDashboard.putNumber("cycle", cycle);
        return cycle + currentValue;
    }
    
    public double shiftedOutput(){
        if(dutyCycleEncoder != null){
            double percentOfRange = fullRange * 0.1;
            double shiftedOutput = MathUtil.inputModulus(dutyCycleEncoder.get(), 0 - percentOfRange, fullRange - percentOfRange);
            return shiftedOutput;
        }
        else{
            return 0;
        }
    }
    
    
    
    public void setRatio(double ratio){
        this.ratio = ratio;
    }

    public void invert(boolean reversed){
        if (encoder != null){
            encoder.setReverseDirection(reversed);
        }
    }

    public void setDistancePerPulse(double distancePerPulse){
        if (encoder != null){
            encoder.setDistancePerPulse(distancePerPulse);
        }
        else {
           
        }
    }

    public double getAbsoluteDistance(){
        return dutyCycleEncoder != null ? dutyCycleEncoder.get() : 0;
    }

    public double getAbsolutePosition(){
        return dutyCycleEncoder != null ? (dutyCycleEncoder.get()*360) : 0;
    }
}
