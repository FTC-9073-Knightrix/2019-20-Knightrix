package org.firstinspires.ftc.teamcode.Autonomous.Master;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name = "Blue Buildsite")

public class AutoBlueSite extends AutoMethods {
    public void runOpMode() {
        initRobot();
        waitForStart();

        //Move towards build site
        gyroMove(90, -0.5, 55, 500);
        //Drop blockservo
        blockServo.setPosition(1);
        sleep(1500);
        //Pull back buildsite
        gyroMove(90, 0.3, 80, 500);
        blockServo.setPosition(0);
        sleep(1000);
        gyroMove(0, -0.5, 50, 500);
        gyroMove(90, -0.5, 40, 500);
        gyroMove(0, 0.5, 30, 500);
        gyroMove(90, 0.5, 30, 500);
        gyroMove(0, -0.5, 40, 500);
    }
}
