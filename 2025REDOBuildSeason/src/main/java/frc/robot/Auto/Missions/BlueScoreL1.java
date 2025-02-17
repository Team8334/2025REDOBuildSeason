package frc.robot.Auto.Missions;

import frc.robot.Auto.AutoMissionChooser;
import frc.robot.Auto.AutoMissionEndedException;

import frc.robot.Auto.Actions.DriveForTimeAction;
import frc.robot.Auto.Actions.WaitAction;
import frc.robot.Auto.Actions.TurnDegreesAction;
import frc.robot.Auto.Actions.MoveElevatorAction;
import frc.robot.Auto.Actions.EffacatorAction;

/*
 * This mission has the robot move out of the robot starting
 * zone to score a coral on L1
 */

public class BlueScoreL1 extends MissionBase{
    @Override
    protected void routine() throws AutoMissionEndedException {
       
        runAction(new WaitAction(AutoMissionChooser.delay)); // MAY NOT BE NEEDED
        runAction(new DriveForTimeAction(0.5,  5));
        runAction(new TurnDegreesAction(30, 1));
        runAction(new EffacatorAction(0.5, 1));
    }
}
