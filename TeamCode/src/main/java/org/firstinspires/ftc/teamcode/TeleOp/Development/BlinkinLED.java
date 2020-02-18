package org.firstinspires.ftc.teamcode.TeleOp.Development;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.TeleOp.TeleOpMethods;

@TeleOp(name = "Blinkin", group = "Development")

public class BlinkinLED extends TeleOpMethods {
    public void loop() {
        telemetry.addData("Distance", intakeRange.getDistance(DistanceUnit.CM));

        //if (rangeSensor.cmUltrasonic() < 25) {
        if (intakeRange.getDistance(DistanceUnit.CM) < 10) {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            telemetry.addLine("LED Violet");
        }
        else {
            blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
            telemetry.addLine("LED Off");
        }
        telemetry.update();
    }
}
