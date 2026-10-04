package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Driver extends SubsystemBase {

  final double MAX_SPEED = 0.3;

  private static Driver instance;
  TalonFX topR = new TalonFX(1);
  TalonFX topL = new TalonFX(3);
  TalonFX botR = new TalonFX(0);
  TalonFX botL = new TalonFX(2);

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
    if (speed + turn / 2 > MAX_SPEED || speed - turn / 2 > MAX_SPEED) {
      speed = MAX_SPEED - turn / 2;
    }
    if (speed - turn / 2 < -MAX_SPEED || speed + turn < -MAX_SPEED) {
      speed = -MAX_SPEED + turn / 2;
    }
    topL.set(speed + turn / 2);
    botL.set(speed + turn / 2);
    topR.set(speed - turn / 2);
    botR.set(speed - turn / 2);
  }
}
