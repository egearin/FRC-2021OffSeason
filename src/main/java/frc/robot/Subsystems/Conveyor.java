// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.Constants;

/**
 * Conveyor Subsystem
 */
public class Conveyor {
    private static Conveyor mInstance = new Conveyor();

    public static Conveyor getInstance() {
        return mInstance;
    }

    public VictorSP conveyorMotor;

    private Conveyor() {
        conveyorMotor = new VictorSP(Constants.conveyorMotorPort);
    }

    public void conveyorStart() {
        conveyorMotor.set(-0.8);
    }

    public void conveyorShoot() {
        conveyorMotor.set(-0.4);
    }

    public void conveyorStop() {
        conveyorMotor.set(0);
    }

    public void conveyorReverse() {
        conveyorMotor.set(0.5);
    }
}
