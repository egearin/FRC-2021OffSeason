// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;


/** Utilities Class*/
public final class Utils {
    /**
     * Return deadband value
     * @param value
     * @param minValue
     * @param maxValue
     * @return Value
     */
    public static double applyDeadband(double value, double minValue, double maxValue){
        if(value > maxValue)
            return maxValue;
        else if(value<minValue){
            return minValue;
        }
        else{
            return value;
        }
    }
    /**
     * Tolerance
     * @param value
     * @param control_value
     * @param tolerance
     * @return value = +- control_value
     */
    public static boolean tolerance(double value,double control_value, double tolerance){
        return ((value >= (control_value - tolerance)) && (value <= (control_value + tolerance)));
    }

}
