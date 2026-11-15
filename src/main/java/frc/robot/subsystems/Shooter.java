package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private double currentSpeed, previousState, error, derivative, integral, target_RPS = 0;
  private final double Kp = 0.08, Ki = 0.008, Kd = 0;
  private final double kIntegralZone = 20; // only integrate when within this many RPS of target

  private static Shooter instance;

  private TalonFX m_motor;

  @Override
  public void periodic() {
    currentSpeed = m_motor.getVelocity().getValueAsDouble();

    // Not shooting: coast and clear the integral so the next spin-up starts clean
    if (target_RPS == 0) {
      integral = 0;
      previousState = currentSpeed;
      m_motor.set(0);
      return;
    }

    error = target_RPS - currentSpeed;
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

  public void shootAt(double target_RPS) {
    this.target_RPS = target_RPS;
  }

  public Shooter() {
    m_motor = new TalonFX(1);
  }

  public static Shooter getInstance() {
    if (instance == null) {
      instance = new Shooter();
    }
    return instance;
  }
}
