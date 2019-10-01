package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public abstract class AutoHardwareMap extends LinearOpMode {
    //Create the four motors
    public DcMotor leftFrontDrive;
    public DcMotor rightFrontDrive;
    public DcMotor rightBackDrive;
    public DcMotor leftBackDrive;

    //Create the gyroscope
    public BNO055IMU gyro;
    //Create the orientation variable for the robot position
    public Orientation orientation;
    //Create the angle tracker
    public double angle = 0;

    public ModernRoboticsI2cRangeSensor RRB;
    public ModernRoboticsI2cRangeSensor RLB;

    //amount of clicks per cm
    public final double ENCDISTANCE = 35.4736842105;
}
