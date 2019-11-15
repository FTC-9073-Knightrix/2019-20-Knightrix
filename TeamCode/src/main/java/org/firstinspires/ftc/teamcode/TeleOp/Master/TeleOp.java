package org.firstinspires.ftc.teamcode.TeleOp.Master;

import org.firstinspires.ftc.teamcode.TeleOp.TeleOpMethods;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "Master")

public class TeleOp extends TeleOpMethods {
    public void loop () {
        getController();
        drive();
        moveArm();

        blockServo.setPosition(g2_right_trigger);
        telemetry.addData("Block", g2_right_trigger);
        //telemetry.update();

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

        liftMotor.setPower(1);
        if (g2_dpad_up && liftSet >= liftPosition - 3900) {
            liftSet -= 150;
            liftMotor.setTargetPosition(liftSet);
        }
        else if (g2_dpad_down && liftSet + 100 <= liftPosition) {
            liftSet += 150;
            liftMotor.setTargetPosition(liftSet);
        }
        else {
            liftSet = liftMotor.getCurrentPosition();
            liftMotor.setTargetPosition(liftSet);
        }

        telemetry.addData("LiftSet", liftSet);
        telemetry.addData("LiftMotor", liftMotor.getCurrentPosition());

        telemetry.update();
    }
}
