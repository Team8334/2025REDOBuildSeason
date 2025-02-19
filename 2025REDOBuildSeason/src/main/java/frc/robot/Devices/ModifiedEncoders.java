package frc.robot.Devices;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;

public class ModifiedEncoders {
    enum output{
        getRate,
        getDistance
    }

    private Encoder encoder;
    private DutyCycleEncoder dutyCycleEncoder;
    private double ratio = 1.0;

    public ModifiedEncoders(int channel){
        dutyCycleEncoder = new DutyCycleEncoder(channel);
        dutyCycleEncoder.setAssumedFrequency(975.6);
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
