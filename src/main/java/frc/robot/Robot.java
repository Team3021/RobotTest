/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.ConfigParameter;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

  private Joystick joyStick;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    joyStick = new Joystick(0);

    CANSparkMax motor0 = sparkMaxInitWithParams(11);
    sparkMaxPrintParameters(motor0);

    CANSparkMax motor1 = sparkMaxInitWithParams(12);
    sparkMaxPrintParameters(motor1);

    CANSparkMax motor2 = sparkMaxInitWithParams(13);
    sparkMaxPrintParameters(motor2);

    CANSparkMax motor3 = sparkMaxInitWithParams(21);
    sparkMaxPrintParameters(motor3);

    CANSparkMax motor4 = sparkMaxInitWithParams(22);
    sparkMaxPrintParameters(motor4);

    CANSparkMax motor5 = sparkMaxInitWithParams(23);
    sparkMaxPrintParameters(motor5);

  }

  private CANSparkMax sparkMaxInit(int deviceID) {
    
    CANSparkMax motor = new CANSparkMax(deviceID, MotorType.kBrushless);

    return motor;
  }

  private CANSparkMax sparkMaxInitWithParams(int deviceID) {
    
    CANSparkMax motor = new CANSparkMax(deviceID, MotorType.kBrushless);

    /**
     * The restoreFactoryDefaults method can be used to reset the configuration parameters
     * in the SPARK MAX to their factory default state. If no argument is passed, these
     * parameters will not persist between power cycles
     */
    CANError canResponse = motor.restoreFactoryDefaults();

    if (canResponse != CANError.kOK){
      System.out.println("Motor " + motor.getDeviceId() + ": " + "Restore Factor Defaults: Error");
    }

    // Clear the faults
    canResponse = motor.clearFaults();

    if (canResponse != CANError.kOK){
      System.out.println("Motor " + motor.getDeviceId() + ": " + "Clear Faults: Error");
    }

    // Set the idle mode
    canResponse = motor.setIdleMode(IdleMode.kCoast);

    if (canResponse != CANError.kOK){
      System.out.println("Motor " + motor.getDeviceId() + ": " + "Idle Mode: Error in set");
    }

    // Set ramp rate to 0
    canResponse = motor.setOpenLoopRampRate(0);

    if (canResponse != CANError.kOK) {
      System.out.println("Motor " + motor.getDeviceId() + ": " + "Ramp Rate: Error in set");
    }

    return motor;
  }

  private void sparkMaxPrintParameters(CANSparkMax motor) {

    String firmware = motor.getFirmwareString();
    System.out.println("Motor "+ motor.getDeviceId() + ": " + "Firmware: " + firmware);

    Short faults = motor.getFaults();
    System.out.println("Motor "+ motor.getDeviceId() + ": " + "Faults: " + faults);

    Short stickyFaults = motor.getStickyFaults();
    System.out.println("Motor "+ motor.getDeviceId() + ": " + "Sticky Faults: " + stickyFaults);

    java.util.Optional<java.lang.Integer> inputMode = motor.getParameterInt(ConfigParameter.kInputMode);

    if (inputMode.isPresent() && inputMode.get().intValue() == 0) {
      System.out.println("Motor "+ motor.getDeviceId() + ": " + "Input Mode: PWM");
    } else if (inputMode.isPresent() && inputMode.get().intValue() == 1) {
      System.out.println("Motor "+ motor.getDeviceId() + ": " + "Input Mode: CAN");
    }

    java.util.Optional<java.lang.Integer> controlType = motor.getParameterInt(ConfigParameter.kCtrlType);

    if (controlType.isPresent()) {
      System.out.println("Motor "+ motor.getDeviceId() + ": " + "Control Type: " + controlType.get().intValue());
    }

    IdleMode idleMode = motor.getIdleMode();

    if (idleMode == IdleMode.kCoast) {
      System.out.println("Motor " + motor.getDeviceId() + ": " + "Idle Mode: Coast");
    } else {
      System.out.println("Motor " + motor.getDeviceId() + ": " + "Idle Mode: Brake");
    }

    double rampRate = motor.getOpenLoopRampRate();

    System.out.println("Motor "+ motor.getDeviceId() + ": " + "Ramp Rate:" + rampRate);
  }
  
  /**
   * This function is called periodically during teleoperated mode.
   */
  @Override
  public void teleopPeriodic() {
    // m_motor.set(joyStick.getY());
  }
}
