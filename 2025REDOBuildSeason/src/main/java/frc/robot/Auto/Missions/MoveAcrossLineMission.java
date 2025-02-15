package frc.robot.Auto.Missions;

import frc.robot.Auto.AutoMissionChooser;
import frc.robot.Auto.AutoMissionEndedException;

import frc.robot.Auto.Actions.DriveForTimeAction;
import frc.robot.Auto.Actions.WaitAction;

/*
 * This mission has the robot move out of the robot starting
 * zone to score some points
 * Can be used for both red and blue alliances
 */

public class MoveAcrossLineMission extends MissionBase{
    @Override
    protected void routine() throws AutoMissionEndedException {
       
        //runAction(new WaitAction(AutoMissionChooser.delay)); // MAY NOT BE NEEDED
        runAction(new DriveForTimeAction(0.25, 5));

    }
}
