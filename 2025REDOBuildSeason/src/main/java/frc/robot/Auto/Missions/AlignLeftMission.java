package frc.robot.Auto.Missions;

import frc.robot.Auto.AutoMissionChooser;
import frc.robot.Auto.AutoMissionEndedException;

import frc.robot.Auto.Actions.FrontLockOnAction;
import frc.robot.Auto.Actions.FrontAlignAction;
import frc.robot.Auto.Actions.WaitAction;

public class AlignLeftMission extends MissionBase {
    @Override
    protected void routine() throws AutoMissionEndedException {
       
        runAction(new WaitAction(AutoMissionChooser.delay)); // a delay to avoid other robots during competition. 
        runAction(new FrontLockOnAction("Reef", true, 5));
        runAction(new FrontAlignAction("Reef", "Left", 4));
    }
}
