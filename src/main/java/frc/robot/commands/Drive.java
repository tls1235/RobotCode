package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Driver;

public class Drive extends Command {
  private Supplier<Double> speed;
  private Supplier<Double> turn;

  public Drive(Supplier<Double> speed, Supplier<Double> turn) {
    this.speed = speed;
    this.turn = turn;
    addRequirements(Driver.getInstance());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Driver.getInstance().drive(speed.get(), turn.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Driver.getInstance().drive(0.0, 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}
