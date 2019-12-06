package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name="Encoder Test", group = "Development")

public class AutoEncoders extends AutoMethods {
    public void runOpMode() {
        initRobot();
        waitForStart();

        while (Math.abs(intakeLeft.getCurrentPosition()) < 30000 && Math.abs(intakeRight.getCurrentPosition()) < 30000) {
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
            }

            telemetry.addData("LeftPos", intakeLeft.getCurrentPosition());
            telemetry.addData("LeftPos%", (Math.abs(intakeLeft.getCurrentPosition())/30000.0));
            telemetry.addData("RightPos", intakeRight.getCurrentPosition());
            telemetry.addData("RightPos%", (Math.abs(intakeRight.getCurrentPosition())/30000.0));

            telemetry.update();
        }
    }
}
