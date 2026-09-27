package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Climber extends SubsystemBase {
private static Climber instance;
  /** Creates a new Climber. */
  TalonFX climberMotor = new TalonFX(1);
  DigitalInput topLimitSwitch = new DigitalInput(0);
  DigitalInput bottomLimitSwitch = new DigitalInput(1);


  public Climber() {}

  @Override
  public void periodic() {
    if (topLimitSwitch.get() && climberMotor.get() > 0) {
      Stop();
    }
    if (bottomLimitSwitch.get() && climberMotor.get() < 0) {
    Stop();
    }
  }

  public static Climber getInstance() {
    if (instance == null) {
      instance = new Climber();
    }
    return instance;
  }
  public void Climb() {
    climberMotor.set(0.5);
  }
  public void Stop() {
    climberMotor.set(0);
  }
  public void Reverse() {
    climberMotor.set(-0.5);
  }
}