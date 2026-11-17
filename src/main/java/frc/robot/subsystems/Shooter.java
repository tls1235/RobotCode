package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private double currentSpeed, previousState, error, derivative, integral, kIntegralZone, target_RPM = 0;
  private final double Kp = 1.0/750, Ki = 0.008, Kd = 0;

  private static Shooter instance;

  private SparkMax m_motor;

  @Override
  public void periodic() {
    kIntegralZone = target_RPM / 5;
    currentSpeed = m_motor.getEncoder().getVelocity();

    // Not shooting: coast and clear the integral so the next spin-up starts clean
    if (target_RPM == 0) {
      integral = 0;
      previousState = currentSpeed;
      m_motor.set(0);
      return;
    }

    error = target_RPM - currentSpeed;
    derivative = currentSpeed - previousState;
    // Integral zone: no windup during the big spin-up, only fine-corrects near target
    if (Math.abs(error) < kIntegralZone) {
      integral += error;
    } else {
      integral = 0;
    }

    previousState = currentSpeed;

    m_motor.set(error * Kp + integral * Ki - derivative * Kd);
  }

  public void shootAt(double target_RPM) {
    this.target_RPM = target_RPM;
  }

  public Shooter() {
    m_motor = new SparkMax(11, MotorType.kBrushless);
  }

  public static Shooter getInstance() {
    if (instance == null) {
      instance = new Shooter();
    }
    return instance;
  }
}
