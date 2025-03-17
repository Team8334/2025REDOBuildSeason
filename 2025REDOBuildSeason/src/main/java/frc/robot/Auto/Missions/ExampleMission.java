package frc.robot.Auto.Missions;

//import these so that the mission is an option when testing
import frc.robot.Auto.AutoMissionChooser;
import frc.robot.Auto.AutoMissionEndedException;

// import the actions from the auto.actions folder
import frc.robot.Auto.Actions.DriveForTimeAction;
import frc.robot.Auto.Actions.ParallelAction;
import frc.robot.Auto.Actions.TurnDegreesAction;
import frc.robot.Auto.Actions.WaitAction;
import frc.robot.Auto.Actions.MoveElevatorAction;
import frc.robot.Auto.Actions.EffectorAction;
import frc.robot.Data.States;

/*
 * This mission is an example mission
 * Has examples of a normal action and a parallel action
 */

public class ExampleMission extends MissionBase {
    States state;
    @Override
    protected void routine() throws AutoMissionEndedException {
       
        //put the actions you want to do here in order of execution
        // String options for MoveElevatorAction: passive, ramp, Score L1, Score L2, Score L3, Score L4, ejecting coral
        runAction(new WaitAction(AutoMissionChooser.delay));
        runAction(new TurnDegreesAction(90, 3.0)); 
        runAction(new DriveForTimeAction (.5, 0));;
        runAction(new ParallelAction(new DriveForTimeAction( 0, 0) , new TurnDegreesAction(20, 1)));
        runAction(new MoveElevatorAction(2, state.SCOREL3));
       // runAction(new EffectorAction(0.5,1));
    }
}
