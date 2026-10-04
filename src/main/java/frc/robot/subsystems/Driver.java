package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Driver extends SubsystemBase {

  final double MAX_SPEED = 0.3;
  final double SPEED_MULT = 0.2;

  private static Driver instance;
  TalonFX forR = new TalonFX(1);
  TalonFX forL = new TalonFX(3);
  TalonFX backR = new TalonFX(0);
  TalonFX backL = new TalonFX(2);

  SparkMax forRs = new SparkMax(10, MotorType.kBrushless);
  SparkMax forLs = new SparkMax(11, MotorType.kBrushless);
  SparkMax backRs = new SparkMax(13, MotorType.kBrushless);
  SparkMax backLs = new SparkMax(12, MotorType.kBrushless);

  AHRS gyro = new AHRS(NavXComType.kMXP_SPI);

  public Driver() {
  }

  @Override
  public void periodic() {
    forRs.set(0 - forRs.getEncoder().getPosition() * 1 / 100);
    forLs.set(0 - forLs.getEncoder().getPosition() * 1 / 100);
    backRs.set(0 - backRs.getEncoder().getPosition() * 1 / 100);
    backLs.set(0 - backLs.getEncoder().getPosition() * 1 / 100);
    CommandScheduler.getInstance().run();
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
    forL.set((speed + turn / 2) * SPEED_MULT);
    backL.set((speed + turn / 2) * SPEED_MULT);
    forR.set((speed - turn / 2) * SPEED_MULT);
    backR.set((speed - turn / 2) * SPEED_MULT);
  }
}
