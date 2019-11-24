package org.firstinspires.ftc.teamcode.Autonomous.Master;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name = "Blue Buildsite")

public class AutoBlueSite extends AutoMethods {
    public void runOpMode() {
        initRobot();
        waitForStart();

        //Move towards build site
        gyroMove(0, -0.5, 50, 500);
        //Drop blockservo
        sideServo.setPosition(1);
        sleep(1500);
        //Pull back buildsite
        gyroMove(0, 0.6, 50, 500);
        sideServo.setPosition(0);
        sleep(1000);
        gyroMove(90, 0.5, 60, 0);
        gyroMove(0, -0.5, 30, 0);
        gyroMove(90, -0.5, 30, 0);
        gyroMove(0, 0.5, 30, 0);
        gyroMove(90, 0.5, 60, 0);
    }
}
