package frc.robot.Subsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CollisionDetection implements Subsystem {
    public static CollisionDetection instance = null;

    public double last_world_linear_accel_x;
    public double last_world_linear_accel_y;

    public final static double kCollisionThreshold_DeltaG = 0.5f;

    public static CollisionDetection getInstance() {
        if(instance == null) {
            instance = new CollisionDetection();
        }
        return instance;
    }

    @Override
    public void update() {
        
    }

    @Override
    public void initialize() {
       
    }

    @Override
    public void log() {
      
    }

    @Override
    public boolean isEnabled() {
       return true;
    }

    @Override
    public String getName() {
      return "collsion detection";
    }
}
