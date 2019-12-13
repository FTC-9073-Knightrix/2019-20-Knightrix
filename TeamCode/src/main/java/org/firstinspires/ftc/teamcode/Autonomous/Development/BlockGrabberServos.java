package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name="Test Grabber",group="Development")

public class BlockGrabberServos extends AutoMethods {
    public void runOpMode() {
        initRobot();
        waitForStart();

        while(opModeIsActive()) {
            blockServo.setPosition(Range.clip(gamepad1.left_trigger,0.23,0.81));
            blockGrabServo.setPosition(Range.clip(1-gamepad1.right_trigger, 0,0.54));

            telemetry.addData("blockServo", Range.clip(gamepad1.left_trigger,0.23,0.81));
            telemetry.addData("blockGrabServo", Range.clip(1-gamepad1.right_trigger, 0,0.54));
            telemetry.update();
        }
    }
}
