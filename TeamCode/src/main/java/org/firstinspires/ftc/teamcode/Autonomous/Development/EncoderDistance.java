package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name = "EncDistance", group = "Development")

public class EncoderDistance extends AutoMethods {
    public void runOpMode() {
        initRobot();
        leftBackDrive.setTargetPosition(5000);
        rightBackDrive.setTargetPosition(5000);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("Left", leftBackDrive.getCurrentPosition());
            telemetry.addData("Right", rightBackDrive.getCurrentPosition());
            telemetry.update();

            leftBackDrive.setPower(0.4);
            rightBackDrive.setPower(0.4);
        }
    }
}
