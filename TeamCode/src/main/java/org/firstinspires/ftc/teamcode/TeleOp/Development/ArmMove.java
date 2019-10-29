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
    }
}
