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
        drive();

        // 3. Update variables and do IF Statements


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
        if (gamepad2.back) {
            blockServo.setPosition(0.7);   // Move plate OUT of the robot
        }
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

            // Lift. Expressed in negative form (higher is negative)
            double liftposition = liftMotor.getCurrentPosition();
            double LiftPower = 0;
            String LiftMode = 'RUN_USING_ENCODER';
            String PriorLiftMode = 'RUN_USING_ENCODER';


            // Use joystick to move Lift
            if (g2_leftstick_y < 0 ) {
                //Joystick is negative, Go UP
                LiftPower = Math.pow(g2_leftstick_y/1.3,3); // Slower speed going up
                LiftMode = 'RUN_USING_ENCODER';
            } else if (g2_leftstick_y > 0) {
                //Joystick is positive, Go DOWN
                LiftPower = Math.pow(g2_leftstick_y,3); // Fast speed going down
                LiftMode = 'RUN_USING_ENCODER';
            }

            // Update LiftModes for the Motor if different from prior
            if (LiftMode <> PriorLiftMode) {
                if (LiftMode = 'RUN_USING_ENCODER') {liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);}
                else {liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);}
                // swap lift modes
                PriorLiftMode = LiftMode;
            }

            // Move using encoder
            if (LiftMode = 'RUN_USING_ENCODER') {liftMotor.setPower(LiftPower);}


            // Nick Code
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
        }


        // Push the logic into the hardware world
        // Update motors and sensors


        // Telemetry Section
        telemetry.addData("Lift Position: ", liftMotor.getCurrentPosition());
    }
}
