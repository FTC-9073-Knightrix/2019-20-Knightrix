package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name="Encoder Test", group = "Development")

public class AutoEncoders extends AutoMethods {
    public void runOpMode() {
        initRobot();
        waitForStart();

        final double encoderCM = 30000/252.0;

        /*while (Math.abs(intakeLeft.getCurrentPosition()) < 30000 || Math.abs(intakeRight.getCurrentPosition()) < 30000) {
            if (Math.abs(intakeLeft.getCurrentPosition()) < 30000) {
                leftFrontDrive.setPower(0.1);
                leftBackDrive.setPower(0.1);
            }
            else {
                leftFrontDrive.setPower(0);
                leftBackDrive.setPower(0);
            }
            if (Math.abs(intakeRight.getCurrentPosition()) < 30000) {
                rightFrontDrive.setPower(0.1);
                rightBackDrive.setPower(0.1);
            }
            else {
                rightFrontDrive.setPower(0);
                rightBackDrive.setPower(0);
            }*/

        //gyroMoveSide(0, 1, 304.8,0);

        while(opModeIsActive()) {
//            telemetry.addData("Should be", 304.8*encoderCM);
           telemetry.addData("Left  Encoder :", intakeLeft.getCurrentPosition());
           telemetry.addData("Right Encoder :", intakeRight.getCurrentPosition() );
           telemetry.addData("Back  Encoder :", BackEncoder.getCurrentPosition() );
           telemetry.update();
        }
    }
}
