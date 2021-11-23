// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

/** Limelight Vision Code */
public class Vision {
    private static Vision mInstance = new Vision();

    public static Vision getInstance(){
        return mInstance;
    } 

    /*
    tv	Whether the limelight has any valid targets (0 or 1)
    tx	Horizontal Offset From Crosshair To Target (LL1: -27 degrees to 27 degrees | LL2: -29.8 to 29.8 degrees)
    ty	Vertical Offset From Crosshair To Target (LL1: -20.5 degrees to 20.5 degrees | LL2: -24.85 to 24.85 degrees)
    ta	Target Area (0% of image to 100% of image)
    ts	Skew or rotation (-90 degrees to 0 degrees)
    tl	The pipeline’s latency contribution (ms) Add at least 11ms for image capture latency.
    tshort	Sidelength of shortest side of the fitted bounding box (pixels)
    tlong	Sidelength of longest side of the fitted bounding box (pixels)
    thor	Horizontal sidelength of the rough bounding box (0 - 320 pixels)
    tvert	Vertical sidelength of the rough bounding box (0 - 320 pixels)
    getpipe	True active pipeline index of the camera (0 .. 9)
    camtran	Results of a 3D position solution, 6 numbers: Translation (x,y,y) Rotation(pitch,yaw,roll)
    */
    private NetworkTable table;
    private NetworkTableEntry tv;
    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry ta;
    /*private NetworkTableEntry ts;
    private NetworkTableEntry tl;
    private NetworkTableEntry tshort;
    private NetworkTableEntry tlong;
    private NetworkTableEntry thor;
    private NetworkTableEntry tvert;
    private NetworkTableEntry getpipe;
    private NetworkTableEntry camtran;*/

    /*
    Set Commands
    ledMode	Sets limelight’s LED state
    0	use the LED Mode set in the current pipeline
    1	force off
    2	force blink
    3	force on
    camMode	Sets limelight’s operation mode
    0	Vision processor
    1	Driver Camera (Increases exposure, disables vision processing)
    pipeline	Sets limelight’s current pipeline
    0 .. 9	Select pipeline 0..9
    stream	Sets limelight’s streaming mode
    0	Standard - Side-by-side streams if a webcam is attached to Limelight
    1	PiP Main - The secondary camera stream is placed in the lower-right corner of the primary camera stream
    2	PiP Secondary - The primary camera stream is placed in the lower-right corner of the secondary camera stream
    snapshot	Allows users to take snapshots during a match
    0	Stop taking snapshots
    1	Take two snapshots per second
    */
    private NetworkTableEntry camMode;
    private NetworkTableEntry ledMode;
    /*private NetworkTableEntry pipeline;
    private NetworkTableEntry stream;
    private NetworkTableEntry snapshot;*/

    private Vision(){
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tv = table.getEntry("tv");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        camMode = table.getEntry("camMode");
        ledMode = table.getEntry("ledMode");

    }

    /**
     * Display Vision Info
     */
    public void displayInfo(){
        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);
        double area = ta.getDouble(0.0);
        SmartDashboard.putNumber("LimelightX", x);
        SmartDashboard.putNumber("LimelightY", y);
        SmartDashboard.putNumber("LimelightArea", area);
    }

    public double[] getInfo(){
        double validTargets = tv.getDouble(0.0);
        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);
        double area = ta.getDouble(0.0);
        double[] values = {validTargets, x, y, area};
        return values;
    }

    public boolean isValidTarget() {
        return (tv.getDouble(0.0) == 1.0) ;
    }

    public double estimateDistance(){
        if (isValidTarget()){
            return estimateDistanceFromAngle(ty.getDouble(0.0));
        }
        return -1;
    }
    
    /**
     * Estimates Distance From Target Using Angle
     * Better than distance estimation from area
     */
    public double estimateDistanceFromAngle(double ty){
        double heightDifference = Constants.visionTargetHeightFromGround - Constants.limelightHeightFromGround;
        double mountingRadian = Math.toRadians(Constants.limelightAngle);
        double tyRadian = Math.toRadians(ty);
        return heightDifference / Math.tan(tyRadian + mountingRadian);
    }

    /**
     * Estimates Distance From Target Using Blob Area
     * Worse than distance estimation from angle
     * @param ta
     * @return Distance
     */
    public double estimateDistanceFromArea(double ta){
        return ta*Constants.limelightBlobAreaEstimation;
    }

    /**
     * Set Camera Mode for Driving or Processing
     * @param isDriverMode True for Driver Mode, False for Vision Processing Mode
     */
    public void setCameraMode(boolean isDriverMode){
        if (isDriverMode){
            camMode.setNumber(1);
        }
        else{
            camMode.setNumber(0);
        }
    }

    /**
     * Sets mode for Limelight's LEDs
     * @param _ledMode
     * 0 for using the pipeline mode 
     * 1 for Off 
     * 2 for Blink 
     * 3 for On
     */
    public void setLedMode(int _ledMode){
        ledMode.setNumber(_ledMode);
    }
}
