package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@TeleOp(name = "Vuforia Test", group = "Development")

public class VuforiaTest extends AutoMethods {
    public void runOpMode() {
        initVuStone();

        waitForStart();

        runVuforia();
    }
}
