package frc.robot.Auto.Missions.RedMissions;

import frc.robot.Auto.AutoMissionChooser;
import frc.robot.Auto.AutoMissionEndedException;
import frc.robot.Auto.Missions.MissionBase;

import frc.robot.Auto.Actions.DriveForTimeAction;
import frc.robot.Auto.Actions.EffectorAction;
import frc.robot.Auto.Actions.FrontAlignAction;
import frc.robot.Auto.Actions.FrontLockOnAction;
import frc.robot.Auto.Actions.MoveElevatorAction;
import frc.robot.Auto.Actions.TurnDegreesAction;
import frc.robot.Auto.Actions.WaitAction;
import frc.robot.Data.States;

public class  RedScoreL4AndDeAlgaefying extends MissionBase {
    States state;

    protected void routine() throws AutoMissionEndedException {
        runAction (new FrontLockOnAction("Reef",true,2));//pls change number
        runAction (new FrontAlignAction("Reef","left",10)); //pls change number
        runAction (new MoveElevatorAction(0.5, States.SCOREL2));
        //runAction (new EffectorAction(.2, States.SCORING));
        runAction (new DriveForTimeAction(0.1, 0.2));
        runAction (new MoveElevatorAction(0.5, States.RAMP));

        //de-algaefying
        runAction (new FrontLockOnAction("Reef", true, 2));
        runAction(new DriveForTimeAction(0.2, 0.2));
        runAction(new MoveElevatorAction(1.25, States.LOWERALGAE));
        runAction(new EffectorAction(0.75, States.DEALGAEFYING));
    }
}
