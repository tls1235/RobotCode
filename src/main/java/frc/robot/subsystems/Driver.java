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
  final double KP = 1 / 1000;
  final double I = 0.02;

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
    if (getCurrentCommand() != null) {
      turnWheelsTo(0);
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
    turnWheelsTo(0);

    m_forwL.set(-leftStick_Y * SPEED_MULT);
    m_backL.set(-leftStick_Y * SPEED_MULT);
    m_forwR.set(-leftStick_Y * SPEED_MULT);
    m_backR.set(-leftStick_Y * SPEED_MULT);
  }

  public void turn(double rightStick_x) {
    rotateWheelsToTurn(45);

    m_forwL.set(rightStick_x * SPEED_MULT);
    m_backL.set(rightStick_x * SPEED_MULT);
    m_forwR.set(-rightStick_x * SPEED_MULT);
    m_backR.set(-rightStick_x * SPEED_MULT);
  }

  public void turnWheelsTo(double targetAngle) {
    m_forwRs.set(((targetAngle - m_forwRs.getEncoder().getPosition()) * KP) - I * Math.abs(targetAngle - m_forwRs.getEncoder().getPosition()) / (targetAngle - m_forwRs.getEncoder().getPosition()));
    m_backRs.set(((targetAngle - m_backRs.getEncoder().getPosition()) * KP) - I * Math.abs(targetAngle - m_backRs.getEncoder().getPosition()) / (targetAngle - m_backRs.getEncoder().getPosition()));
    m_backLs.set(((targetAngle - m_backLs.getEncoder().getPosition()) * KP) - I * Math.abs(targetAngle - m_backLs.getEncoder().getPosition()) / (targetAngle - m_backLs.getEncoder().getPosition()));
    m_forwLs.set(((targetAngle - m_forwLs.getEncoder().getPosition()) * KP) - I * Math.abs(targetAngle - m_forwLs.getEncoder().getPosition()) / (targetAngle - m_forwLs.getEncoder().getPosition()));
  }

  public void rotateWheelsToTurn(double targetAngle) {
    m_forwRs.set(((-Math.abs(targetAngle) - m_forwRs.getEncoder().getPosition()) * KP) - I * Math.abs(-Math.abs(targetAngle) - m_forwRs.getEncoder().getPosition()) / (-Math.abs(targetAngle) - m_forwRs.getEncoder().getPosition()));
    m_backRs.set(((Math.abs(targetAngle) - m_backRs.getEncoder().getPosition()) * KP) - I * Math.abs(Math.abs(targetAngle) - m_backRs.getEncoder().getPosition()) / (Math.abs(targetAngle) - m_backRs.getEncoder().getPosition()));
    m_backLs.set(((-Math.abs(targetAngle) - m_backLs.getEncoder().getPosition()) * KP) - I * Math.abs(-Math.abs(targetAngle) - m_backLs.getEncoder().getPosition()) / (-Math.abs(targetAngle) - m_backLs.getEncoder().getPosition()));
    m_forwLs.set(((Math.abs(targetAngle) - m_forwLs.getEncoder().getPosition()) * KP) - I * Math.abs(Math.abs(targetAngle) - m_forwLs.getEncoder().getPosition()) / (Math.abs(targetAngle) - m_forwLs.getEncoder().getPosition()));
  }
}
