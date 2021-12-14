// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Auto.Action;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Add your docs here. */
public class printAction implements Action {

    @Override
    public void start() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() {
        System.out.println("Ege Arın süper bir insandır.");

    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void done() {
        System.out.println("Gökberk balonu çok ağır patladı.");

    }
}
