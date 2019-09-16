package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public abstract class AutoMethods extends AutoHardwareMap {
    //Create the initialization method to run at the start of the programs
    public void initRobot() {

        //Add the motors to the configuration on the phones
        leftFrontDrive = hardwareMap.dcMotor.get("LF");
        rightFrontDrive = hardwareMap.dcMotor.get("RF");
        rightBackDrive = hardwareMap.dcMotor.get("RB");
        leftBackDrive = hardwareMap.dcMotor.get("LB");

        //Set the direction of the motors
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);

        //Set the mode the motors are going to be running in
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Add the gyroscope to the configuration on the phones
        gyro = hardwareMap.get(BNO055IMU.class, "gyro");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        gyro.initialize(parameters);
    }

    // Move for a number of clicks based on the Gyro, Power/Speed, and desired direction of the robot
    public void gyroMove(int direction, double power, int distance, int wait){
        orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        int StartingOrientation = (int) orientation.firstAngle;

        resetEncoders();
        distance *= ENCDISTANCE; //converts cm to encoder rotations
        while(opModeIsActive() &&  (distance > (Math.abs(leftFrontDrive.getCurrentPosition()) + Math.abs(rightFrontDrive.getCurrentPosition()) + Math.abs(leftBackDrive.getCurrentPosition()) + Math.abs(rightBackDrive.getCurrentPosition())) / 4)) {
            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            int gyroDegrees = (int) orientation.firstAngle;

            if (gyroDegrees < -359) {
                gyroDegrees += 360;
            }

            int CorrectionDegrees = (StartingOrientation - gyroDegrees);
            float myrot = (float)(CorrectionDegrees / 180.0) * -1;

            move(direction, (float) power, myrot);

            // DEBUG with Telemetry
            telemetry.addData("Directin Goal: ", direction);
            telemetry.addData("Gyro Position: ", gyroDegrees);
            telemetry.addData("Correction :   ",  CorrectionDegrees );
            telemetry.addData("MyRot -1/+1 :  ",  myrot);

            telemetry.addData("Position", (
                    Math.abs(leftFrontDrive.getCurrentPosition()) +
                            Math.abs(rightFrontDrive.getCurrentPosition()) +
                            Math.abs(leftBackDrive.getCurrentPosition()) +
                            Math.abs(rightBackDrive.getCurrentPosition()) )/ 4);
            telemetry.update();
        }

        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);

        sleep(wait);
    }

    //Create the move method to move the robot based off the angle, power, and rotation of the robot applied
    public void move (double myangle, float mypower, float myrot) {

        //If none of the motors are null, run each motor to an individual value based off the values inputted from the joystick
        if (leftFrontDrive != null && leftBackDrive != null && rightFrontDrive != null && rightBackDrive != null) {
            leftFrontDrive.setPower(Range.clip((myrot + (mypower * ((Math.sin((myangle + 135) / 180 * Math.PI))))), -1, 1));
            leftBackDrive.setPower(Range.clip((myrot + (mypower * ((Math.sin((myangle + 45) / 180 * Math.PI))))), -1, 1));
            rightFrontDrive.setPower(Range.clip((-myrot + (mypower * ((Math.sin((myangle + 45) / 180 * Math.PI))))), -1, 1));
            rightBackDrive.setPower(Range.clip((-myrot + (mypower * ((Math.sin((myangle + 135) / 180 * Math.PI))))), -1, 1));

            telemetry.addData("LeftFront", leftFrontDrive.getCurrentPosition());
            telemetry.addData("LeftBack", leftBackDrive.getCurrentPosition());
            telemetry.addData("RightFront", rightFrontDrive.getCurrentPosition());
            telemetry.addData("RightBack", rightBackDrive.getCurrentPosition());
        }
    }

    public void resetEncoders() {
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
