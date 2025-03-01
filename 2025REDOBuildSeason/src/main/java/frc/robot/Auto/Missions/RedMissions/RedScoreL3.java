package frc.robot.Auto.Missions.RedMissions;

import frc.robot.Auto.AutoMissionChooser;
import frc.robot.Auto.Missions.MissionBase;
import frc.robot.Auto.AutoMissionEndedException;

import frc.robot.Auto.Actions.EffectorAction;
import frc.robot.Auto.Actions.MoveElevatorAction;
import frc.robot.Auto.Actions.DriveForTimeAction;
import frc.robot.Auto.Actions.TurnDegreesAction;
import frc.robot.Auto.Actions.WaitAction;

/*
 * This mission has the robot move out of the robot starting
 * zone to score a coral on L3
 */

public class RedScoreL3 extends MissionBase{
    
    @Override
    protected void routine() throws AutoMissionEndedException {
       
        //runAction(new WaitAction(AutoMissionChooser.delay)); // MAY NOT BE NEEDED
        runAction(new DriveForTimeAction(-0.5,  4));
        //runAction(new TurnDegreesAction(65, 1));// add 5 degrees because michalangelo has consistently com up 5 degrees short of the needed turn
        
        // String options: passive, ramp, Score L1, Score L2, Score L3, Score L4, ejecting coral
        runAction(new MoveElevatorAction(1, "Score L3"));
        runAction(new EffectorAction(0.5,1));
        runAction(new MoveElevatorAction(2, "ramp"));
    }
}
