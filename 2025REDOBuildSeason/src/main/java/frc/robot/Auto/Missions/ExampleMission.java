package frc.robot.Auto.Missions;

//import these so that the mission is an option when testing
import frc.robot.Auto.AutoMissionChooser;
import frc.robot.Auto.AutoMissionEndedException;

// import the actions from the auto.actions folder
import frc.robot.Auto.Actions.DriveForTimeAction;
import frc.robot.Auto.Actions.ParallelAction;
import frc.robot.Auto.Actions.TurnDegreesAction;
import frc.robot.Auto.Actions.WaitAction;

/*
 * This mission is an example mission
 * Has examples of a normal action and a parallel action
 */

public class ExampleMission extends MissionBase {

    @Override
    protected void routine() throws AutoMissionEndedException {
       
        //put the actions you want to do here in order of execution
        runAction(new WaitAction(AutoMissionChooser.delay));
        runAction(new TurnDegreesAction(90, 3.0)); 
        runAction(new DriveForTimeAction (.5, 0));;
        runAction(new ParallelAction(new DriveForTimeAction( 0, 0) , new TurnDegreesAction(20, 1)));
    }
}
