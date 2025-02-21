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
    private static double fullRange = 1;
    private static double expectedZero = 0;
    private double ratio = 1.0;

    public ModifiedEncoders(int channel){
        //dutyCycleEncoder = new DutyCycleEncoder(channel);
        dutyCycleEncoder = new DutyCycleEncoder(channel, fullRange, expectedZero);
    }

    public Boolean isConnected(){
        //boolean connected = dutyCycleEncoder.isConnected();
        //return connected;
        if(dutyCycleEncoder != null){
            return (dutyCycleEncoder.isConnected());
        }
        else{
            return false;
        }
    }
    
    public int getFrequency(){
        //int frequency = dutyCycleEncoder.getFrequency();
        //return frequency;

        if(dutyCycleEncoder != null){
            return (dutyCycleEncoder.getFrequency());
        }
        else{
            return 0;
        }
    }
    
    public double get(){
        //double output = dutyCycleEncoder.get();
        //return output;

        if(dutyCycleEncoder != null){
            return (dutyCycleEncoder.get());
        }
        else{
            return 0;
        }
    }

    public double extendedCycle(){
        double saveEncoder;
        double currentEncoder;
        int cycle = 0;
        if(saveEncoder - currentEncoder > .25){
            return cycle + 1;
        }
        if(saveEncoder - currentEncoder < .25){
            return cycle -1;
        }
    }
    
    public double shiftedOutput(){
        //return shiftedOutput;

        if(dutyCycleEncoder != null){
            double percentOfRange = fullRange * 0.1;
            double shiftedOutput = MathUtil.inputModulus(dutyCycleEncoder.get(), 0 - percentOfRange, fullRange - percentOfRange);
            return shiftedOutput;
        }
        else{
            return 0;
        }
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
