// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Optional;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Teleop;
import frc.robot.Auto.AutoMissionChooser;
import frc.robot.Auto.AutoMissionExecutor;
import frc.robot.Auto.Missions.MissionBase;
import frc.robot.Subsystem.Mecanum;
import frc.robot.Devices.Gyro;
import frc.robot.Subsystem.FrontLimelight;
import frc.robot.Subsystem.SubsystemManager;
import frc.robot.Subsystem.ScoringControl;
import au.grapplerobotics.CanBridge;

import frc.robot.Teleop;
import frc.robot.Subsystem.Elevator;
import frc.robot.Subsystem.SubsystemManager;
/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  Teleop teleop;
  FrontLimelight frontLimelight;
  

  private AutoMissionExecutor autoMissionExecutor = new AutoMissionExecutor();
  private AutoMissionChooser autoMissionChooser = new AutoMissionChooser();

  // private static final String kDefaultAuto = "Default";
  // private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public void robotInit() {
    //m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    // m_chooser.addOption("My Auto");
    CanBridge.runTCP();

    SmartDashboard.putData("Auto choices", m_chooser);
    Gyro.getInstance();
    teleop = new Teleop();
    Mecanum.getInstance();

    frontLimelight = frontLimelight.getInstance();

    ScoringControl.getInstance();

   // Elevator.getInstance();

    SubsystemManager.initializeSubsystems();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    SubsystemManager.updateSubsystems();
    
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  
   @Override
  public void autonomousInit() {

    if (autoMissionChooser.getAutoMission().isPresent()){
    {
      autoMissionChooser.getAutoMission().get().setStartPose();
    }
    autoMissionExecutor.start();
  }

    m_autoSelected = m_chooser.getSelected();
    System.out.println("Auto selected: " + m_autoSelected);
  }


  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    teleop.teleopPeriodic();
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    // Reset all auto mission states.
    if (autoMissionExecutor != null)
    {
      autoMissionExecutor.stop();
    }
    autoMissionChooser.reset();
    autoMissionChooser.updateMissionCreator();
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    autoMissionChooser.outputToSmartDashboard();
    autoMissionChooser.updateMissionCreator();

    Optional<MissionBase> autoMission = autoMissionChooser.getAutoMission();
    if (autoMission.isPresent() && autoMission.get() != autoMissionExecutor.getAutoMission())
    {
      System.out.println("Set auto mission to: " + autoMission.get().getClass().toString());
      autoMissionExecutor.setAutoMission(autoMission.get());
    }
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
  }
}
