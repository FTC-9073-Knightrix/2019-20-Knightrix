package org.firstinspires.ftc.teamcode.TeleOp.Master;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.TeleOp.TeleOpMethods;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "RuntimeTest", group = "Master")
@Disabled
public class RuntimeTest extends TeleOpMethods {
    int iterations = 0;
    double t1 = getRuntime();
    public void loop() {
        if ((getRuntime() - t1) > 0.5) {
            telemetry.addData("iterations per 0.5 seconds", iterations);
            iterations = 0;
            t1 = getRuntime();
        } else {
            iterations++;
        }
        telemetry.update();
    }
}
