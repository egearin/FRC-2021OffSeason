// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.Constants;

/** Subsystem for climb, a great masterpiece of Ata Aydın.
 * 
*/

public class Climbing {
    private static Climbing mInstance = new Climbing();

    private VictorSP climbMotor;

    public static Climbing getInstance(){
        return mInstance;
    }

    private Climbing(){
        climbMotor = new VictorSP(Constants.climberMotorPort);
        //climbMotor.setInverted(true);
    }

    public void releaseClimber(){
        climbMotor.set(0.5);
    }

    public void climb(){
        climbMotor.set(-0.7);
    }

    public void hang(){
        climbMotor.set(-0.3);
    }

    public void stopClimbMotor(){
        climbMotor.set(0);
    }
}