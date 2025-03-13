package frc.robot.Devices;

import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface.Measurement;

public class Laser {
    private LaserCan lc; 
     public int laserDetectedDistance;

     public Laser(int LaserCanID){
            lc = new LaserCan(LaserCanID);
            laserConfig();
            System.out.println("laserCan is being conficgured" + LaserCanID);
     }
     public void laserConfig(){
        try {
            lc.setRangingMode(LaserCan.RangingMode.SHORT);
            lc.setRegionOfInterest(new LaserCan.RegionOfInterest(8, 8, 16, 16));
            lc.setTimingBudget(LaserCan.TimingBudget.TIMING_BUDGET_33MS);
          } 
        catch (ConfigurationFailedException e) {
            System.out.println("Configuration failed! " + e);
        }
    }

    public double laserDistance(){
        LaserCan.Measurement measurement = lc.getMeasurement();
        if (measurement != null && measurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT){
            laserDetectedDistance = measurement.distance_mm;
            System.out.println("The target is " + measurement.distance_mm + "mm away!");
        } else{
            laserDetectedDistance = 999999;
        }
        return laserDetectedDistance;
    }
}
