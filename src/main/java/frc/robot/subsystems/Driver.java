package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Driver extends SubsystemBase {
  private static Driver instance;
  TalonFX topR = new TalonFX(0);
  TalonFX topL = new TalonFX(1);
  TalonFX botR = new TalonFX(2);
  TalonFX botL = new TalonFX(3);

  public Driver() {
  }

  @Override
  public void periodic() {
  }

  public static Driver getInstance() {
    if (instance == null) {
      instance = new Driver();
    }
    return instance;
  }

  public void drive(Double speed, Double turn) {
    if (speed + turn / 2 > 1) {
      speed = 1.0 - turn / 2;
    }
    if (speed - turn / 2 < -1) {
      speed = -1.0 + turn / 2;
    }
    topL.set(speed + turn / 2);
    botL.set(speed + turn / 2);
    topR.set(speed - turn / 2);
    botR.set(speed - turn / 2);
  }
}
