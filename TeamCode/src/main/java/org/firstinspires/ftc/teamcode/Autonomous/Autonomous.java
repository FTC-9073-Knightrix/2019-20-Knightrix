package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

public abstract class Autonomous extends AutoMethods {
    public void initRobot() {

        //Add the motors to the configuration on the phones
        //leftFrontDrive = hardwareMap.dcMotor.get("LF");
        //rightFrontDrive = hardwareMap.dcMotor.get("RF");
        rightBackDrive = hardwareMap.dcMotor.get("RB");
        leftBackDrive = hardwareMap.dcMotor.get("LB");

        //Set the direction of the motors
        //rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        //leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);

        //Set the mode the motors are going to be running in
        //leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Add the gyroscope to the configuration on the phones
        gyro = hardwareMap.get(BNO055IMU.class, "gyro");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        gyro.initialize(parameters);

        //Add range sensors
        /*RRB = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "RRB");
        RLB = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "RLB");
        RLB.setI2cAddress(I2cAddr.create8bit(0x16));
        RRB.setI2cAddress(I2cAddr.create8bit(0x16));*/
    }
    // Move for a number of clicks based on the Gyro, Power/Speed, and desired direction of the robot
}
