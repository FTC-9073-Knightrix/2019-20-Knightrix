package org.firstinspires.ftc.teamcode.TeleOp.Master;

import org.firstinspires.ftc.teamcode.TeleOp.TeleOpMethods;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "Master")

public class TeleOp extends TeleOpMethods {
    public void loop () {
        getController();
        drive();
        moveArm();

        if (g2_a) {
            intakeLeft.setPower(1);
            intakeRight.setPower(1);
        }
        else if (g2_b) {
            intakeLeft.setPower(-1);
            intakeRight.setPower(-1);
        }
        else {
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
        }

        if (g2_dpad_up) {
            liftMotor.setPower(1);
        }
        else if (g2_dpad_down) {
            liftMotor.setPower(-1);
        }
        else {
            liftMotor.setPower(0);
        }

        telemetry.update();
    }
}
