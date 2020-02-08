package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

public abstract class TeleOpMethods extends TeleOpHardwareMap {
    public void getController() {
        //Gamepad
        g1_leftstick_x = gamepad1.left_stick_x;
        g2_leftstick_x = gamepad2.left_stick_x;

        g1_leftstick_y = gamepad1.left_stick_y;
        g2_leftstick_y = gamepad2.left_stick_y;

        g1_rightstick_x = gamepad1.right_stick_x;
        g2_rightstick_x = gamepad2.right_stick_x;


        //Gamepad buttons
        g1_dpad_down = gamepad1.dpad_down;
        g1_dpad_up = gamepad1.dpad_up;
        g2_dpad_down = gamepad2.dpad_down;
        g2_dpad_up   = gamepad2.dpad_up;
        g2_dpad_right= gamepad2.dpad_right;
        g2_dpad_left = gamepad2.dpad_left;
        g1_a = gamepad1.a;
        g1_b = gamepad1.b;
        g2_a = gamepad2.a;
        g2_b = gamepad2.b;
        g2_y = gamepad2.y;

        //Gamepad bumpers
        g1_right_bumper = gamepad1.right_bumper;
        g1_left_bumper = gamepad1.left_bumper;
        g2_right_bumper = gamepad2.right_bumper;
        g2_left_bumper  = gamepad2.left_bumper;

        //Gamepad triggers
        g2_right_trigger = gamepad2.right_trigger;
        g2_left_trigger = gamepad2.left_trigger;
    }

    public void move (double myangle, float mypower, float myrot) {

        // Record variables
        float Orig_power = mypower;

        //To maximize the motor power, first calculate the maximum absolute power from Trig, then increase power to match 100% (Disregarding rotation values)
        double LeftPower = Math.max( Math.abs(Math.sin((myangle + 135) / 180 * Math.PI)),Math.abs(Math.sin((myangle + 45) / 180 * Math.PI)));
        double RightPower = Math.max( Math.abs(Math.sin((myangle + 45) / 180 * Math.PI)),Math.abs(Math.sin((myangle + 135) / 180 * Math.PI)));
        double RobotPower = Math.max(Math.abs(LeftPower),Math.abs(RightPower));
        mypower = (float) (1/ RobotPower) * Orig_power; // Determine the new power to apply so that wheels are always running at the Power speed

        //Check for errors
        if (Double.isNaN(RobotPower )) {RobotPower = 0;}
        if (Float.isNaN(Orig_power )) {Orig_power = 0;}
        if (Float.isNaN(myrot )) {myrot = 0;}
        if (RobotPower==0) {mypower =0;}
        //Since mypower is -1/+1 and myrot can also be -1/+1, need to trim both down to ensure mypower + myrot are between -1/+1
        myrot = myrot * (Math.abs(myrot) / (Math.abs(myrot)+Math.abs(Orig_power)));
        mypower = mypower * (Math.abs(Orig_power) / (Math.abs(myrot)+Math.abs(Orig_power)));

        //If none of the motors are null, run each motor to an individual value based off the values inputted from the joystick
        if (leftFrontDrive != null && leftBackDrive != null && rightFrontDrive != null && rightBackDrive != null) {
            leftFrontDrive.setPower( Range.clip(( myrot + (mypower * ((Math.sin((myangle + 135) / 180 * Math.PI))))), -1, 1));
            leftBackDrive.setPower(  Range.clip(( myrot + (mypower * ((Math.sin((myangle +  45) / 180 * Math.PI))))), -1, 1));
            rightFrontDrive.setPower(Range.clip((-myrot + (mypower * ((Math.sin((myangle +  45) / 180 * Math.PI))))), -1, 1));
            rightBackDrive.setPower( Range.clip((-myrot + (mypower * ((Math.sin((myangle + 135) / 180 * Math.PI))))), -1, 1));
        }
    }

    public void drive() {
        //Get the current position of the robot
        orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        gyroDegrees = (int) orientation.firstAngle;

        //Get the rotation of the robot based off the position of the right joystick x
        if (!slowmode) {
            myrot = Math.round(g1_rightstick_x * (float) 100) / (float) 100;
        }
        else {
            myrot = Math.round(g1_rightstick_x / 2.5 * (float) 100) / (float) 100;
        }

        if (!slowmode) {
            //Declare the left joystick x
            leftstick_x = -g1_leftstick_x;
        }
        else {
            leftstick_x = (float) (-g1_leftstick_x / 2.5);
        }
        if (!slowmode) {
            //Declare the left joystick y
            leftstick_y = g1_leftstick_y;
        }
        else {
            leftstick_y = (float) (g1_leftstick_y / 2.5);
        }

        //Calculate the angle of the joystick based off the x and y values
        //If the joystick is in quadrant I or IV
        if ((leftstick_x > 0 && leftstick_y > 0) || (leftstick_x > 0 && leftstick_y < 0)) {
            myangle = (int) (90 + Math.toDegrees(Math.atan(leftstick_y / leftstick_x)));
        }
        //If the joystick is in quadrant II or III
        else if ((leftstick_x < 0 && leftstick_y < 0) || (leftstick_x < 0 && leftstick_y > 0)) {
            myangle = (int) (270 + Math.toDegrees(Math.atan(leftstick_y / leftstick_x)));
        }
        //If the joystick is at the origin
        else if ((leftstick_x == 0 && leftstick_y == 0) || (leftstick_x == 0 && leftstick_y < 0)) {
            myangle = 0;
        }
        //If the joystick is on the x axis and x is positive
        else if (leftstick_x > 0  && leftstick_y == 0) {
            myangle = 90;
        }
        //If the joystick is on the y axis and y is positive
        else if (leftstick_x == 0 && leftstick_y > 0) {
            myangle = 180;
        }
        //If the joystick is on the x axis and the x is negative
        else if (leftstick_x < 0 && leftstick_y == 0) {
            myangle = 270;
        }
        //Determine the power of the motors based off the pythagorean theorem
        mypower = (float) Range.clip(Math.sqrt((leftstick_x*leftstick_x)+(leftstick_y*leftstick_y)),0.0,1.0);

        //Limit the angle to within 0-365
        myangle -= gyroDegrees;
        if (myangle < -359) {
            myangle += 360;
        }

        //Move the robot based off the calculated values
        move(myangle,mypower,myrot);
    }

    public void moveArm(int liftencoder) {
        clampServo.setPosition(g2_right_trigger);
        double twistPosition = bodyTwistServo.getPosition();
        if (liftencoder < -1300) {
            if (g2_rightstick_x > 0 && twistPosition + 0.03 <= 0.75) {
                bodyTwistServo.setPosition(twistPosition + .03);
            }
            else if (g2_rightstick_x > 0 && twistPosition + 0.01 <= 1) {
                bodyTwistServo.setPosition(twistPosition + .01);
            }
            else if (g2_rightstick_x > 0 && twistPosition + 0.01 > 1) {
                bodyTwistServo.setPosition(1);
            }
            else if (g2_rightstick_x < 0 && twistPosition - 0.01 > 0.75) {
                bodyTwistServo.setPosition(twistPosition - .01);
            }
            else if (g2_rightstick_x < 0 && twistPosition - 0.03 >= 0) {
                bodyTwistServo.setPosition(twistPosition - .03);
            }
            else if (g2_rightstick_x < 0 && twistPosition - 0.03 < 0) {
                bodyTwistServo.setPosition(0);
            }
        }

        telemetry.addData("servo", bodyTwistServo.getPosition());
        //telemetry.update();

        if (g2_left_bumper) {
            blockTwistServo.setPosition(1);
        }
        else if (g2_right_bumper) {
            blockTwistServo.setPosition(0);
        }
        else if (g2_y) {
            blockTwistServo.setPosition(0.5);
        }
    }

    // Create a method to get values from Hardware
    // public void Get_Hardware_Values(MyGyro){

    //}
}