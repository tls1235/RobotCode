package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Driver extends SubsystemBase {

  final double MAX_SPEED = 0.3;
  final double SPEED_MULT = 0.2;
  double robotTargetAngle = 0;
  double wheelTargetAngle = 0;

  // all variables NEED to start with a private and m_[NAME]
  // the motor names are not clear and coherent name them sonthing like
  // m_frontLeftMotor, m_backRightMotor, etc m_TRDrive m_TRSpin
  // define the motors as private final and initialize them in the constructor
  private static Driver instance;

  private AHRS gyro = new AHRS(NavXComType.kMXP_SPI);
  private TalonFX m_forwR;
  private TalonFX m_forwL;
  private TalonFX m_backR;
  private TalonFX m_backL;
  private SparkMax m_forwRs;
  private SparkMax m_forwLs;
  private SparkMax m_backRs;
  private SparkMax m_backLs;

  public Driver() {
    m_forwR = new TalonFX(1);
    m_forwL = new TalonFX(3);
    m_backR = new TalonFX(0);
    m_backL = new TalonFX(2);

    m_forwRs = new SparkMax(10, MotorType.kBrushless);
    m_forwLs = new SparkMax(11, MotorType.kBrushless);
    m_backRs = new SparkMax(13, MotorType.kBrushless);
    m_backLs = new SparkMax(12, MotorType.kBrushless);
  }

  @Override
  public void periodic() {
    double avg = (m_backLs.get() + m_backRs.get() + m_forwLs.get() + m_forwRs.get()) / 4;
    if (Math.abs(avg) <= 0) {
      turnWheelsTo(wheelTargetAngle, 1 / 1000.0, 0.02);
    }
  }

  public static Driver getInstance() {
    if (instance == null) {
      instance = new Driver();
    }
    return instance;
  }

  // parameters names should be consistent
  public void drive(double leftStick_Y) {
    wheelTargetAngle = 0;
    turnWheelsTo(wheelTargetAngle, 1 / 1000.0, 0.02);

    m_forwL.set(-leftStick_Y * SPEED_MULT);
    m_backL.set(-leftStick_Y * SPEED_MULT);
    m_forwR.set(-leftStick_Y * SPEED_MULT);
    m_backR.set(-leftStick_Y * SPEED_MULT);
  }

  public void turn(double speed) {
    rotateWheelsToTurn(45, 1 / 1000.0, 0.02);

    m_forwL.set(speed * SPEED_MULT);
    m_backL.set(speed * SPEED_MULT);
    m_forwR.set(-speed * SPEED_MULT);
    m_backR.set(-speed * SPEED_MULT);
  }

  public void robotTurnTo(double targetAngle, double KP, double absoluteConstantCorrection) {
    double error = targetAngle - gyro.getAngle();
    turn(error * KP + absoluteConstantCorrection * Math.signum(error));
  }

  public void turnWheelsTo(double targetAngle, double KP, double absoluteConstantCorrection) {
    setSteer(m_forwRs, targetAngle, KP, absoluteConstantCorrection);
    setSteer(m_backRs, targetAngle, KP, absoluteConstantCorrection);
    setSteer(m_backLs, targetAngle, KP, absoluteConstantCorrection);
    setSteer(m_forwLs, targetAngle, KP, absoluteConstantCorrection);
  }

  public void rotateWheelsToTurn(double targetAngle, double KP, double absoluteConstantCorrection) {
    targetAngle = Math.abs(targetAngle);
    setSteer(m_forwRs, -targetAngle, KP, absoluteConstantCorrection);
    setSteer(m_backRs, targetAngle, KP, absoluteConstantCorrection);
    setSteer(m_backLs, -targetAngle, KP, absoluteConstantCorrection);
    setSteer(m_forwLs, targetAngle, KP, absoluteConstantCorrection);
  }

  private void setSteer(SparkMax motor, double target, double KP, double absoluteConstantCorrection) {
    double error = target - motor.getEncoder().getPosition();
    motor.set(error * KP + absoluteConstantCorrection * Math.signum(error));
  }

  public AHRS getGyro() {
    return gyro;
  }

  public void setRobotTargetAngle(double targetAngle) {
    this.robotTargetAngle = targetAngle;
  }
}
