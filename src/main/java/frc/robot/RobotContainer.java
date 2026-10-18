// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.Driver;
import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

// This class is where the bulk of the robot should be declared.
// Since Command-based is a "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
// Instead, the structure of the robot (including subsystems, commands, and trig ger mappings) should be declared here.

public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  public DriveCommand drive;

  public static final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public RobotContainer() {
    configureBindings();

    Driver.getInstance().setDefaultCommand(drive);
  }

  private void configureBindings() {
    drive = new DriveCommand(() -> {
      return -m_driverController.getLeftY();
    }, () -> {
      return m_driverController.getRightX();
    });
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
