// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.Constants;

/**
 * Intake Subsystem
 */
public class Intake {
    private static Intake mInstance = new Intake();

    private Conveyor mConveyor;
    public VictorSP intakeMotor;
    public VictorSP centerRight;
    public VictorSP centerLeft;
    public VictorSP pivotMotor;
    

    public static Intake getInstance(){
        return mInstance;
    }

    private Intake(){
        mConveyor = Conveyor.getInstance();
        intakeMotor = new VictorSP(Constants.intakeMotorPort);
        centerLeft = new VictorSP(Constants.centerLeftMotorPort);
        centerRight = new VictorSP(Constants.centerRightMotorPort);
        pivotMotor = new VictorSP(Constants.pivotMotorPort);
    }
    //Pivot Motor Codes

    public void pivotDown(){
        pivotMotor.set(-0.2);
    }

    public void pivotUp(){
        pivotMotor.set(0.3);
    }

    public void pivotStall(){
        pivotMotor.set(0);
    }

    public void intakeOn(){
        intakeMotor.set(0.5);
        centerLeft.set(-0.5);
        centerRight.set(-0.5);
        mConveyor.conveyorStart();
    }

    public void intakeReverse(){
        intakeMotor.set(-0.5);
        centerLeft.set(0.5);
        centerRight.set(0.5);
        mConveyor.conveyorReverse();
    }

    public void intakeStop(){
        intakeMotor.set(0);
        centerLeft.set(0);
        centerRight.set(0);
        mConveyor.conveyorStop();
    }

    //Manual Intake Codes (drivepanel)
    public void centerRoller(){
        intakeMotor.set(0.7);
    }

    public void leftRoller(){
        centerLeft.set(1);
    }

    public void rightRoller(){
        centerRight.set(-1);
    }

    //Manual Intake Reverse Codes (drivepanel)
    public void centerRollerReverse(){
        intakeMotor.set(-1);
    }

    public void leftRollerReverse(){
        centerLeft.set(-1);
    }

    public void rightRollerReverse(){
        centerRight.set(1);
    }

    //Manual Intake Stop (drivepanel)
    public void stopCenterRoller(){
        intakeMotor.set(0);
    }

    public void stopLeftRoller(){
        centerLeft.set(0);
    }

    public void stopRightRoller(){
        centerRight.set(0);
    }
}