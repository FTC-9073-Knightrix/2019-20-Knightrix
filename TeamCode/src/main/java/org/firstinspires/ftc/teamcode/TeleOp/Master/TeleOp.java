package org.firstinspires.ftc.teamcode.TeleOp.Master;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.TeleOp.TeleOpMethods;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "Master")

public class TeleOp extends TeleOpMethods {
    public void loop () {
        if (gamepad1.start && !ready) {
            initRun = true;
            ready = true;
        }
        if(initRun) {
            if (stage == 0) {
                blockServo.setPosition(0.7);
                liftMotor.setPower(0.2);
                liftMotor.setTargetPosition(-3500);
                stage++;
            }
            else if (stage == 1) {
                if (liftMotor.getCurrentPosition() < -3000) {
                    blockServo.setPosition(0);
                    liftMotor.setPower(0.3);
                    liftMotor.setTargetPosition(0);
                    stage++;
                }
                else if (liftMotor.getCurrentPosition() < -200) {
                    liftMotor.setPower(1);
                }
            }
            else if (stage == 2) {
                if (liftMotor.getCurrentPosition() > -2800) {
                    liftMotor.setPower(1);
                    stage++;
                }
            }
            else {
                if (liftMotor.getCurrentPosition() > -25) {
                    initRun = false;
                }
            }
        }
        else {
            getController();
            drive();

            //MAKE THE ARM THING ONE BUTTON!
            moveArm();

            if (g2_a) {
                intakeLeft.setPower(1);
                intakeRight.setPower(1);
            } else if (g2_b) {
                intakeLeft.setPower(-1);
                intakeRight.setPower(-1);
            } else {
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
            }

            if (g2_dpad_up && liftMotor.getCurrentPosition() > -3900) {
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftMotor.setPower(-1);
            }
            else if (g2_dpad_down && liftMotor.getCurrentPosition() < 0) {
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftMotor.setPower(1);
            }
            else {
                liftMotor.setPower(1);
                liftMotor.setTargetPosition(liftMotor.getCurrentPosition());
                liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }


            /*liftMotor.setPower(1);
            if (g2_dpad_up && liftSet >= liftPosition - 3900) {
                liftSet -= 150;
                liftMotor.setTargetPosition(liftSet);
            } else if (g2_dpad_down && liftSet + 100 <= liftPosition) {
                liftSet += 150;
                liftMotor.setTargetPosition(liftSet);
            } else {
                liftSet = liftMotor.getCurrentPosition();
                liftMotor.setTargetPosition(liftSet);
            }

            telemetry.addData("LiftSet", liftSet);
            telemetry.addData("LiftMotor", liftMotor.getCurrentPosition());*/

            telemetry.update();
        }
    }
}
