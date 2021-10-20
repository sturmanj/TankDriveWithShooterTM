// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private XboxController driver;
  private TalonFX rightA;
  private TalonFX rightB;
  private TalonFX leftA;
  private TalonFX leftB;
  private TalonFX shooterA;
  private TalonFX shooterB;
  private TalonFX indexer;
  private CANSparkMax funnelRightMotor;
  private CANSparkMax rollerMotorA;

  private final double deadzone = 0.05;
  

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    driver = new XboxController(0);
    rightA = new TalonFX(3);
    rightB = new TalonFX(4);
    leftA = new TalonFX(1);
    leftB = new TalonFX(2);
    shooterA = new TalonFX(9);
    shooterB = new TalonFX(10);
    indexer = new TalonFX(8);
    funnelRightMotor = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
    rollerMotorA = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
    leftB.follow(leftA);
    rightB.follow(rightA);
    indexer.setInverted(true);
    shooterB.setInverted(true);
    shooterB.follow(shooterA);

    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
  /*  m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  */
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    double rightStick = 0.5 * driver.getY(Hand.kRight);
    double leftStick = -0.5 * driver.getY(Hand.kLeft);

    if (Math.abs(leftStick) <= deadzone) {
      leftStick = 0;
    }
    
    if (Math.abs(rightStick) <= deadzone) {
      rightStick = 0;
    }
    //System.out.println(leftStick + "   " + rightStick);

    rightA.set(ControlMode.PercentOutput, rightStick);
    leftA.set(ControlMode.PercentOutput, leftStick);

    if (driver.getBButton()) {
      shooterA.set(ControlMode.PercentOutput, 0.5);
      indexer.set(ControlMode.PercentOutput, 0.5);
      System.out.println("B button pressed");
    } else {
      shooterA.set(ControlMode.PercentOutput, 0);
      indexer.set(ControlMode.PercentOutput, 0);
      System.out.println("B button not pressed");
    } 
    
    if (driver.getXButton()) {
      funnelRightMotor.set(0.5);
      rollerMotorA.set(0.5);
      System.out.println("X button pressed");
    }   else {
      funnelRightMotor.set(0.0);
      rollerMotorA.set(0.0);
      System.out.println("X button not pressed");
    } 
  }


  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
