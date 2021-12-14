// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Auto.Action;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Subsystems.Intake;

/** Add your docs here. */
public class PivotUpAction implements Action {

    Intake mIntake;
    Timer timer;
    double _pivotTime;

    public PivotUpAction(double pivotTime) {
        mIntake = Intake.getInstance();
        timer = new Timer();
        _pivotTime = pivotTime;
    }

    @Override
    public void start() {
        timer.reset();
        timer.start();
        System.out.println("I'm starting");
    }

    @Override
    public void update() {
        mIntake.pivotUp();
        System.out.println("Pivot timer is " + timer.get() + "condition is" + (timer.get() > _pivotTime));
    }

    @Override
    public boolean isFinished() {
        return timer.get() > _pivotTime;
    }

    @Override
    public void done() {
        System.out.println("I'm done");
        mIntake.pivotStall();
    }
}
