// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Class for using DrivePanel buttons
 */

public class Drivepanel {
    // TO-DO adjust this code for this years code
    private static Drivepanel mInstance = new Drivepanel();

    public static Drivepanel getInstance(){
        return mInstance;
    }

    Joystick panelM;
    Joystick panelA;

    public Drivepanel(){
        panelM = new Joystick(2);
        panelA = new Joystick(1);
    }

    public boolean leftRoller(){
        return panelM.getRawButton(7);
    }

    public boolean leftRollerReverse(){
        return panelM.getRawButton(8);
    }

    public boolean releaseSlowly(){
        return panelA.getRawButton(8);
    }
    public boolean centerRoller(){
        return panelM.getRawButton(3);
    }

    public boolean centerRollerReverse(){
        return panelM.getRawButton(4);
    }

    public boolean rightRoller(){
        return panelM.getRawButton(2);
    }

    public boolean rightRollerReverse(){
        return panelM.getRawButton(1);
    }

    public boolean pivotUp(){
        return panelM.getRawButton(5); // to be corrected
    }

    public boolean pivotDown(){
        return panelM.getRawButton(6); // to be corrected
    }

    public boolean resetGyro(){
        return panelA.getRawButton(6); // to be corrected
    }

    public boolean isShooterSpeedUpPressed(){
        return panelA.getRawButtonPressed(12);
    }
    public boolean speedUpLevel1(){
        return panelA.getRawButton(11);
    }

    public boolean speedUpLevel2(){
        return panelA.getRawButton(10);
    }

    public boolean speedUpLevel3(){
        return panelA.getRawButton(9);
    }
    public boolean isShooterSpeedUpReleased(){
        return panelA.getRawButtonReleased(8);
    }
    public boolean climberUp(){
        return panelM.getRawButton(9);
    }

    public boolean climberDown(){
        return panelM.getRawButton(10);
    }

    public boolean autoAim(){
        return panelA.getRawButton(5);
    }
}