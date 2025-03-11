package frc.robot.Devices;

import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface.Measurement;

import frc.robot.Data.PortMap;

public class Lazer {
     private LaserCan lc; 
     public int laserDetectedDistance;

     public Lazer(int LazerCanID){
            lc = new LaserCan(LazerCanID);
            laserConfig();
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
            measurement.distance_mm = laserDetectedDistance;
            System.out.println("The target is " + measurement.distance_mm + "mm away!");
        } else{
            laserDetectedDistance = 999999;
        }
        return laserDetectedDistance;
    }
}
