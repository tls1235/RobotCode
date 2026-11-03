// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.Driver;
import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
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
      // Linux sim GUI maps the right stick X to axis 3 (roboRIO uses 4)
      return RobotBase.isSimulation() ? m_driverController.getRawAxis(3) : m_driverController.getRightX();
    });
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
