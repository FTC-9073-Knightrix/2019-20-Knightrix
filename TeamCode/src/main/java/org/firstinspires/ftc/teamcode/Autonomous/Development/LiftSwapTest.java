package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name = "Lift Swap Test", group = "Development")

public class LiftSwapTest extends AutoMethods {
    public void runOpMode() {
        initRobot();

        waitForStart();

        liftMotor.setTargetPosition(-2600);
        liftMotor.setPower(0);
        blockServo.setPosition(0.81);
        //Wait for the arm to fully go up
        sleep(1000);
        blockGrabServo.setPosition(0.54);
        //Turn to get ready for the drivers
        liftMotor.setPower(1);
        while (liftMotor.getCurrentPosition() > -2300 && opModeIsActive()) {
            telemetry.addData("Position", liftMotor.getCurrentPosition());
            telemetry.update();
        }
        blockServo.setPosition(0);
        //Wait for the arm to fully go up
        sleep(500);
        blockGrabServo.setPosition(0.2);
        liftMotor.setTargetPosition(0);
        while (liftMotor.getCurrentPosition() < -300 && opModeIsActive()) {
            telemetry.addData("Position", liftMotor.getCurrentPosition());
            telemetry.update();
        }
    }
}
