package org.firstinspires.ftc.teamcode.TeleOp.Master;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.TeleOp.TeleOpMethods;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "Master")

public class TeleOp extends TeleOpMethods {
    public void loop () {
        getController();
        drive();

        if (g1_right_bumper || g2_a) {
            intakeLeft.setPower(1);
            intakeRight.setPower(1);
        }
        else if (g1_left_bumper || g2_b) {
            intakeLeft.setPower(-1);
            intakeRight.setPower(-1);
        }
        else {
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
        }

        if (g1_b) {
            slowmode = false;
        }
        else if (g1_a){
            slowmode = true;
        }

        if (gamepad2.start && !ready) {
            initRun = true;
            ready = true;
        }
        if (g1_dpad_up) {
            sideDown = true;
        }
        else if (g1_dpad_down) {
            sideDown = false;
        }
        if (sideDown) {
            siteServo.setPosition(1);
        }
        else {
            siteServo.setPosition(0);
        }

        if (gamepad2.back) {
            blockServo.setPosition(0.7);
        }

        if(initRun) {
            if (stage == 0) {
                liftMotor.setPower(0.2);
                liftMotor.setTargetPosition(-3100);
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
            moveArm();

            if (g2_leftstick_y < 0 && liftMotor.getCurrentPosition() > -3700) {
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftMotor.setPower(g2_leftstick_y / 1.5);
            }
            else if (g2_dpad_up) {
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftMotor.setPower(-1/1.5);
            }
            else if (g2_leftstick_y > 0 && liftMotor.getCurrentPosition() < 0) {
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftMotor.setPower(g2_leftstick_y / 1.5);
            }
            else if (g2_dpad_down) {
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftMotor.setPower(1/1.5);
            }
            else if (gamepad2.x) {
                liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
            else {
                liftMotor.setPower(1);
                liftMotor.setTargetPosition(liftMotor.getCurrentPosition());
                liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        }
    }
}
