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


        // Get Hardware Values
        liftencoderValue = -liftMotor.getCurrentPosition() - LiftEncoderOffset;

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
            /*if (stage == 0) {
                liftMotor.setPower(0.2);
                liftMotor.setTargetPosition(-2300);
                stage++;
            }
            else if (stage == 1) {
                if (liftMotor.getCurrentPosition() < -2000) {
                    blockServo.setPosition(0);
                    liftMotor.setPower(0.3);
                    liftMotor.setTargetPosition(0);
                    stage++;
                }
                else if (liftMotor.getCurrentPosition() < -300) {
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
            }*/
        }
        else { // No Karate Move; Driver can move the Robot freely

            // Variables used for the Lift

            //double NewTime = getRuntime();
            double rateOfChange;
            //        int oldValue = liftMotor.getCurrentPosition();






            moveArm(liftencoderValue); // g2_; bumper; clamp  AND input was height of the LIFT

            // ########## Move Lift UP / DOWN ##########
            float MyPower = 0;
            MyPower = g2_leftstick_y * (float) 1;                   // Limit maximum power (optional)

            // Going DOWN, stop on touch sensor
            if (g2_leftstick_y > 0 && LiftTouch.getState()==false) { //joystick DOWN and button pressed (false) => Limit reached
                MyPower = 0;                                        // stop motor
                LiftEncoderOffset=-liftMotor.getCurrentPosition();   // Reset Encoder
            }
            if (g2_leftstick_y > 0 && liftencoderValue < 200) {     //joystick DOWN and close to bottom => Reduce Speed
                MyPower = MyPower * (float) 0.5;
            }

            // Going UP, reduce speed on the TOP
            if (g2_leftstick_y < 0 && liftencoderValue > 2900) { //joystick UP and Encoder close to Limit => reduce speed
                MyPower = MyPower * (float) 0.5;
            }
            // Going UP, stop on Encoder Value
            if (g2_leftstick_y < 0 && liftencoderValue > 3350) { //joystick UP and Encoder over Limit => Limit reached
                MyPower = 0; //false = button pressed
            }
            //Move
            liftMotor.setPower(MyPower);
            // Telemetry
            telemetry.addData("Joy Y: ", g2_leftstick_y);
            telemetry.addData("Power: ", MyPower);
            telemetry.addData("Encoder: ", liftencoderValue);

            // ########## END Move Lift ##########


            /*
            if (g2_dpad_up) {
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftMotor.setPower(1/1.5);
            }
            else if (g2_leftstick_y > 0) {
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                if(Math.abs(g2_leftstick_y) > 0.5  && getRuntime()-oldTime > 0.2){
                    rateOfChange = Math.abs((liftencoderValue-oldValue)/(getRuntime()-oldTime));
                    if(rateOfChange < 500){
                        liftMotor.setPower(0.1);
                    }
                    else{
                        liftMotor.setPower(Math.pow(g2_leftstick_y/1.3,3));
                        oldTime = getRuntime();
                        oldValue = liftencoderValue;
                    }
                    oldTime = getRuntime();
                    oldValue = liftencoderValue;
                }
                else if(Math.abs(g2_leftstick_y) > 0.5){
                    liftMotor.setPower(Math.pow(g2_leftstick_y/1.3,3));
                }
                else{
                    liftMotor.setPower(Math.pow(g2_leftstick_y/1.3,3));
                    oldTime = getRuntime();
                    oldValue = liftencoderValue;
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
                oldValue = liftencoderValue;
            }

             */
        }



        // Push the logic into the hardware world
        // Update motors and sensors


        // Telemetry Section

    }
}
