package frc.robot.Auto.Missions.RedMissions;


import frc.robot.Auto.AutoMissionChooser;
import frc.robot.Auto.AutoMissionEndedException;
import frc.robot.Auto.Missions.MissionBase;

import frc.robot.Auto.Actions.DriveForTimeAction;
import frc.robot.Auto.Actions.EffectorAction;
import frc.robot.Auto.Actions.MoveElevatorAction;
import frc.robot.Auto.Actions.TurnDegreesAction;
import frc.robot.Auto.Actions.WaitAction;
import frc.robot.Auto.Actions.FrontLockOnAction;

/*
 * This mission has the robot move out of the robot starting
 * zone to score a coral on L4
 */

public class RedScoreStartPosition2 extends MissionBase{
    @Override
    protected void routine() throws AutoMissionEndedException {
       
       
        //runAction(new WaitAction(AutoMissionChooser.delay)); // MAY NOT BE NEEDED

        runAction(new DriveForTimeAction(-0.2, 1));
        runAction(new TurnDegreesAction(-45, 4));// add 5 degrees because michalangelo has consistently com up 5 degrees short of the needed turn
        runAction(new FrontLockOnAction(null, mActive, mUpdateRate));
        // String options: passive, ramp, Score L1, Score L2, Score L3, Score L4, ejecting coral
        runAction(new MoveElevatorAction(2, "Score L2")); // May be needed depending on how robot starts
        runAction(new EffectorAction(0.1,0.5));
        runAction(new DriveForTimeAction(-0.2, 0.1));
        runAction(new MoveElevatorAction(2, "ramp"));
    }
}
