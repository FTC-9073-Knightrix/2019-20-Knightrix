package org.firstinspires.ftc.teamcode.TeleOp.Master;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

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
        drive();

        // 2. Get values from the hardware
        int liftposition = liftMotor.getCurrentPosition();

        // 3. Update variables and do IF Statements
        double Lift_Power = 0; // Lift


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


        // #########  Slow Motion  #########
        if (g1_b) {
            slowmode = false;
        }
        else if (g1_a){
            slowmode = true;
        }
        // #######################################



        // #########  Swap Skystone Servo and ARM  #########
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
        // #######################################


        // ### Block/Skystone servo  ######
        /*if (gamepad2.back) {
            blockServo.setPosition(0.7);   // Move plate OUT of the robot
        }*/

        if (gamepad2.back) {
            blockServo.setPosition(.23);
        }
        else {
            blockServo.setPosition(Range.clip(gamepad2.left_trigger, 0, .81));
        }
        if (gamepad2.left_trigger >= 0.73) {
            blockGrabServo.setPosition(Range.clip((1 - gamepad2.left_trigger) * 2, 0, .54));
        }
        else {
            blockGrabServo.setPosition(.54);
        }
        telemetry.addData("Trigger", gamepad2.left_trigger);

        // #######################################


        // ### Lift Mechanism  ######
        if(initRun) {
            if (stage == 0) {                                   //Lift Motor
                liftMotor.setPower(0.2);
                liftMotor.setTargetPosition(-3100);
                stage++;
            }
            else if (stage == 1) {                              // Move Servo IN
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
            else if (stage == 2) {                              //
                if (liftMotor.getCurrentPosition() > -2800) {
                    liftMotor.setPower(1);
                    stage++;
                }
            }
            else {                                              // Resets variables
                if (liftMotor.getCurrentPosition() > -25) {
                    initRun = false;
                }
            }
        }
        else {
            moveArm();

            /** Lift. Expressed in negative form (higher is negative) */
            // Use joystick to move Lift
            if (g2_leftstick_y < -0.1 ) {  //using 0.1 to avoid values like 0.0002 messing with code
                //Joystick is negative, Go UP
                Lift_Power = Math.pow(g2_leftstick_y/1.3,3); // Slower speed going up (about 0.45 Max)
            }
            else if (g2_leftstick_y > 0.1) {
                //Joystick is positive, Go DOWN
                Lift_Power = Math.pow(g2_leftstick_y,3); // Fast speed going down
                if (liftposition >= 0) {
                    Lift_Power = 0;
                }   // Prevents moving below ZERO
            }
            else {
                Lift_Power = -0.063;
            }

            // OVERRIDE Down if DPad is pressed
            if (gamepad2.x) {
                // Down Dpad, go DOWN
                Lift_Power = 0.1; // Fast speed going down
                liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            } ;

            /* Update LiftModes for the Motor if different from prior
            if (LiftMode <> PriorLiftMode) {
                if (LiftMode = 'RUN_USING_ENCODER') {liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);}
                else {liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);}
                // swap lift modes
                PriorLiftMode = LiftMode;
            }
            */

            // Move Motor
            liftMotor.setPower(Lift_Power);


            /** Nick Code
            if (g2_leftstick_y < 0 && liftMotor.getCurrentPosition() > -3700) {
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftMotor.setPower(Math.pow(g2_leftstick_y/1.3,3));
            }
            else if (g2_dpad_down) {
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftMotor.setPower(1/1.5);
            }
            else if (g2_leftstick_y > 0 && liftMotor.getCurrentPosition() < 0) {
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftMotor.setPower(Math.pow(g2_leftstick_y,3));
            }
            else if (g2_dpad_up) {
                liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftMotor.setPower(-1);
            }
            else if (gamepad2.x) {
                liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
            else {
                liftMotor.setPower(1);
                liftMotor.setTargetPosition(liftMotor.getCurrentPosition());
                liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
             */
        }




        // Push the logic into the hardware world
        // Update motors and sensors


        // Telemetry Section
        telemetry.addData("Should be false (Init): ", initRun);
        telemetry.addData("Lift Position: ", liftposition); // moves between ABS(10) and zero
        telemetry.addData("Lift Power:", Lift_Power);
        telemetry.addData("Joystick: ", g2_leftstick_y);
        telemetry.addData("Gpad 2 Down: ", g2_dpad_down);

        telemetry.addData("DOWN: ",g2_dpad_down);
        telemetry.addData("UP:   ",g2_dpad_up);
        telemetry.addData("Right:", g2_dpad_right);
        telemetry.addData("left: ",g2_dpad_left);
        telemetry.addData("A: ",g2_a);
        telemetry.addData("B: ", g2_b);
        telemetry.addData("Y: ",g2_y);
        telemetry.addData("X: ",gamepad2.x);


    }
}
