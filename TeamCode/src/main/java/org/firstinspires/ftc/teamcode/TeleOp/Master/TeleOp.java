package org.firstinspires.ftc.teamcode.TeleOp.Master;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.TeleOp.TeleOpMethods;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "Master")

public class TeleOp extends TeleOpMethods {
    public void loop () {

        // Basic Code loop
        // 1. Get values from gamepad
        // 2. Get values from the hardware
        // 3. Update variables and do IF Statements
        // 4. Update hardware values

        getController();

        // Define SlowMotion status
        if (g1_b) {
            slowmode = false;
        }
        else if (g1_a){
            slowmode = true;
        }
        drive();


        // Logic


        // #########  Intake Mechanism  #########
        double IntakePower = 0;
        // If Driver OR Gunner buttons pressed, IN take
        if (g1_right_bumper || g2_a) {
            IntakePower = 1;
        }
        // Driver or Gunner buttons pressed, OUT take
        else if (g1_left_bumper || g2_b) {
            IntakePower = -1;
        }
        // No buttons pressed, STOP motors
        else {
            IntakePower = 0;
        }

        // Apply variables
        intakeLeft.setPower(IntakePower);
        intakeRight.setPower(IntakePower);
        // #######################################

        // Code to Move the side arm during the TeleOp
        // ???
        if (g1_dpad_up) {
            sideDown = true;
        }
        else if (g1_dpad_down) {
            sideDown = false;
        }
        // ???
        if (sideDown) {
            siteServo.setPosition(1);
        }
        else {
            siteServo.setPosition(0);
        }
        // ???
        if (gamepad2.back) {
            blockServo.setPosition(0.7);
        }
        // END OF Arm Code


        // Do Karate move if Start button is pressed
        if (gamepad2.start && !ready) {
            initRun = true;
            ready = true;
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
        else { // No Karate Move; Driver can move the Robot freely

            // Variables used for the Lift

            //double NewTime = getRuntime();
            double rateOfChange;
            //        int oldValue = liftMotor.getCurrentPosition();






            moveArm(); // g2_; bumper; clamp  AND input was height of the LIFT

            //hard limit at 3700 - should go higher
            if (g2_leftstick_y < 0) {
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                if(Math.abs(g2_leftstick_y) > 0.3  && (getRuntime()-oldTime) > 0.2){
                    rateOfChange = Math.abs((encoderValue-oldValue)/(getRuntime()-oldTime));
                    if(rateOfChange < 200){
                        liftMotor.setPower(0);
                    }
                    else{
                        liftMotor.setPower(Math.pow(g2_leftstick_y/1.3,3));
                    }
                }
                else if(Math.abs(g2_leftstick_y) > 0.5){
                    liftMotor.setPower(Math.pow(g2_leftstick_y/1.3,3));
                }
                else{
                    liftMotor.setPower(Math.pow(g2_leftstick_y/1.3,3));
                    oldTime = getRuntime();
                    oldValue = encoderValue;
                }
            }


            if (g2_dpad_up) {
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftMotor.setPower(1/1.5);
            }
            else if (g2_leftstick_y > 0) {
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                if(Math.abs(g2_leftstick_y) > 0.5  && getRuntime()-oldTime > 0.2){
                    rateOfChange = Math.abs((encoderValue-oldValue)/(getRuntime()-oldTime));
                    if(rateOfChange < 500){
                        liftMotor.setPower(0.1);
                    }
                    else{
                        liftMotor.setPower(Math.pow(g2_leftstick_y/1.3,3));
                    }
                    oldTime = getRuntime();
                    oldValue = encoderValue;
                }
                else if(Math.abs(g2_leftstick_y) > 0.5){
                    liftMotor.setPower(Math.pow(g2_leftstick_y/1.3,3));
                }
                else{
                    liftMotor.setPower(Math.pow(g2_leftstick_y/1.3,3));
                    oldTime = getRuntime();
                    oldValue = encoderValue;
                }
            }
            else if (g2_dpad_down) {
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftMotor.setPower(-1);
            }
            else if (gamepad2.x) {
                liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
            else {
                String i = "RUN_USING_ENCODER";
                liftMotor.setPower(1);
                liftMotor.setTargetPosition(liftMotor.getCurrentPosition());
                liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            if(g2_leftstick_y == 0){
                oldTime = getRuntime();
                oldValue = encoderValue;
            }
        }



        // Push the logic into the hardware world
        // Update motors and sensors


        // Telemetry Section

    }
}
