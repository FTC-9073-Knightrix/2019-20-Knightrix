package org.firstinspires.ftc.teamcode.TeleOp.Master;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.TeleOp.TeleOpMethods;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "tapeReset", group = "Master")

public class RuntimeTest extends TeleOpMethods {
    public void loop() {
        if(gamepad1.x){
            tapeMotor.setPower(-1);
        }
        else if(gamepad1.y){
            tapeMotor.setPower(1);
        }
        else{
            tapeMotor.setPower(0);
        }
    }
}
