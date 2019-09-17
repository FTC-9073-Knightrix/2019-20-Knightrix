package org.firstinspires.ftc.teamcode.TeleOp.Development;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.TeleOp.TeleOpMethods;

//Define program in program registry
@TeleOp(name = "Basic TeleOp", group = "Development")

//Basic TeleOp Program to test just the wheels
public class BasicTeleOp extends TeleOpMethods {
    //Start OpMode loop
    public void loop () {
        //Use drive() method from TeleOpMethods
        drive();
    }
}
