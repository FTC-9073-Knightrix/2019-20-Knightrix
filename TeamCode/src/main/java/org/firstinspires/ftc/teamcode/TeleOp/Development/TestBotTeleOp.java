package org.firstinspires.ftc.teamcode.TeleOp.Development;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.TeleOp.TeleOpMethods;

@Disabled
@TeleOp(name="TestBotTeleOp", group="Development")

public class TestBotTeleOp extends TeleOpMethods {
    public void loop(){
        getController();

        leftBackDrive.setPower(Range.clip(g1_leftstick_x - g1_leftstick_y,-1,1));
        rightBackDrive.setPower(Range.clip(g1_leftstick_x + g1_leftstick_y, -1, 1));

        telemetry.addData("Left Encoder", intakeLeft.getCurrentPosition());
        telemetry.addData("Right Encoder", intakeRight.getCurrentPosition());
    }
}
