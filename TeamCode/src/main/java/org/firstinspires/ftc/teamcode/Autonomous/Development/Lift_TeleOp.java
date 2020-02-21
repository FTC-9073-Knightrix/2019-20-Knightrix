package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.TeleOp.TeleOpMethods;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Lift_TeleOp", group = "Master")
@Disabled

public class Lift_TeleOp extends TeleOpMethods {

    public void loop() {
        // Define Variables
        float MyPower = 0;

        // Read controllers
        getController();
        // Read Hardware
        liftencoderValue = -liftMotor.getCurrentPosition() - LiftEncoderOffset;

        // Program Logic
        // g2_leftstick_y UP = negative number
        // Motor UP = negative power

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
        if (g2_leftstick_y < 0 && liftencoderValue > 2500) { //joystick UP and Encoder close to Limit => reduce speed
            MyPower = MyPower * (float) 0.5;
        }
        // Going UP, stop on Encoder Value
        if (g2_leftstick_y < 0 && liftencoderValue > 3100) { //joystick UP and Encoder over Limit => Limit reached
            MyPower = 0; //false = button pressed
        }


        //Move
        liftMotor.setPower(MyPower);

        telemetry.addData("Joy Y: ", g2_leftstick_y);
        telemetry.addData("Power: ", MyPower);
        telemetry.addData("Encoder: ", liftencoderValue);
    }

}
