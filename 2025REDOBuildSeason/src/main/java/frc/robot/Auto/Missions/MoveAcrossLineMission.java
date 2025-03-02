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
        runAction(new DriveForTimeAction(-0.3, 3)); // lengths can be adjusted as needed

    }
}
