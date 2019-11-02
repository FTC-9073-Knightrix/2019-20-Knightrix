package org.firstinspires.ftc.teamcode.TeleOp.Development;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.TeleOp.TeleOpMethods;

@TeleOp(name = "Arm", group = "Development")

public class ArmMove extends TeleOpMethods {
    public void loop() {
        //bodyTwistServo moves the entire mechanism around
        //blockTwistServo only moves the block around
        //clampServo grabs the block

        getController();

        if (g2_dpad_up) {
            blockTwistServo.setPosition(1);
            telemetry.addData("Block", 1);
        }
        else if (g2_dpad_down) {
            blockTwistServo.setPosition(0);
            telemetry.addData("Block", 0);
        }
        else {
            blockTwistServo.setPosition(0.5);
            telemetry.addData("Block", 0.5);
        }

        if (g2_dpad_right) {
            bodyTwistServo.setPosition(1);
            telemetry.addData("Body", 1);
        }
        else if (g2_dpad_left) {
            bodyTwistServo.setPosition(0);
            telemetry.addData("Body", 1);
        }
        else {
            bodyTwistServo.setPosition(0.5);
            telemetry.addData("Body", 0.5);
        }

        clampServo.setPosition(g2_left_trigger);
        telemetry.addData("Clamp", g2_left_trigger);

        telemetry.update();
    }
}
