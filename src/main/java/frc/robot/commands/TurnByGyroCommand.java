package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Driver;

public class TurnByGyroCommand extends Command {

  double targetAngle;
  double acceptableError;

  public TurnByGyroCommand(double targetAngle, double acceptableError) {
    addRequirements(Driver.getInstance());
    this.targetAngle = targetAngle;
    this.acceptableError = acceptableError;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Driver.getInstance().robotTurnTo(targetAngle, 1 / 1000.0, 0.02);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Driver.getInstance().getGyro().getAngle() > targetAngle - acceptableError || Driver.getInstance().getGyro().getAngle() < targetAngle + acceptableError) {
      return true;
    } else {
      return false;
    }
  }
}
