package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class ShootCommand extends Command {
  private Supplier<Double> targetSpeed;

  public ShootCommand(Supplier<Double> speed) {
    this.targetSpeed = speed;
    addRequirements(Shooter.getInstance());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Shooter.getInstance().shootAt(targetSpeed.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Shooter.getInstance().shootAt(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
