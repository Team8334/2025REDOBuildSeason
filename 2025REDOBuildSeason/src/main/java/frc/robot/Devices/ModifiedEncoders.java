package frc.robot.Devices;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.math.MathUtil;

public class ModifiedEncoders {
    enum output{
        getRate,
        getDistance
    }

    private Encoder encoder;
    private DutyCycleEncoder dutyCycleEncoder;
    private static double fullRange = 1.3;
    private static double expectedZero = 0;
    private double ratio = 1.0;

    public ModifiedEncoders(int channel){
        dutyCycleEncoder = new DutyCycleEncoder(channel);
        dutyCycleEncoder = new DutyCycleEncoder(0, fullRange, expectedZero);
        dutyCycleEncoder.setAssumedFrequency(975.6);
    }

    public Boolean isConnected(){
        boolean connected = dutyCycleEncoder.isConnected();
        return connected;
    }
    
    public int getFrequency(){
        int frequency = dutyCycleEncoder.getFrequency();
        return frequency;
    }
    
    public double get(){
        double output = dutyCycleEncoder.get();
        return output;
    }
    
    public double shiftedOutput(){
        double percentOfRange = fullRange * 0.1;
        double shiftedOutput = MathUtil.inputModulus(dutyCycleEncoder.get(), 0 - percentOfRange, fullRange - percentOfRange);
        return shiftedOutput;
    }
    
    
    
    public void setRatio(double ratio)
    {
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
    
    public double getRate(){
        if(encoder != null){
            return encoder.getRaw();
        }
        else{
            return 0;
        }
    }

    public double getRelativeDistance(){
        if (encoder != null){
            return encoder.getDistance();
        }
        else{
            return 0;
        }
    }

    public double getAbsoluteDistance(){
        if(dutyCycleEncoder != null)
        {
            return dutyCycleEncoder.get();
        }
        else{
            return 8;
        }
    }

    public double getAbsolutePosition(){
        if(dutyCycleEncoder != null){
            return (dutyCycleEncoder.get() * 360);
        }
        else{
            return 0;
        }
    }
}
