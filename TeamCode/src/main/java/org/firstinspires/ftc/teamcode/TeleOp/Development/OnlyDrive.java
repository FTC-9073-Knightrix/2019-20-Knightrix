package org.firstinspires.ftc.teamcode.TeleOp.Development;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.TeleOp.TeleOpMethods;

@Disabled
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Drive Only", group = "Master")

public class OnlyDrive extends TeleOpMethods {
    public void loop () {
        getController();
        drive();
    }
}
