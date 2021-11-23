// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Auto.Action;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Subsystems.Intake;

/** Add your docs here. */
public class IntakeAction implements Action {

    Intake mIntake;
    double intakeOnTime;
    Timer timer;

    public IntakeAction(double intakeTime){
        intakeOnTime = intakeTime;
        mIntake = Intake.getInstance();
        timer = new Timer();
    }

    @Override
    public void start() {
        timer.reset();
        timer.start();
    }

    @Override
    public void update() {
        mIntake.intakeOn();
    }

    @Override
    public boolean isFinished() {
        return timer.get() > intakeOnTime;
    }

    @Override
    public void done() {
        mIntake.intakeStop();
    }

}
