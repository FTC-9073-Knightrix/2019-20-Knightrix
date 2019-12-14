package org.firstinspires.ftc.teamcode.Autonomous.Master;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name = "Red Buildsite")

public class AutoRedSite extends AutoMethods {
    public void runOpMode() {
        initRobot();
        waitForStart();

        //Move towards build site
        newGyroMove(0, -0.3, 53, 70,0);
        //Drop blockservo
        sideServo.setPosition(1);
        sleep(1500);
        //Pull back buildsite
        newGyroMove(0, 0.2, 40, 70, 0);
        sideServo.setPosition(0);
        sleep(1000);
        straighten(0, 0.5);
        newGyroMove(90, -0.2, 50, 70, 0);
        straighten(0, 0.5);
        newGyroMove(0, -0.2, 70, 70, 0);
        straighten(0, 0.5);
        newGyroMove(90, 0.2, 60, 70, 0);
        newGyroMove(0, 0.2, 30, 70, 0);
        straighten(0, 0.5);
        newGyroMove(90, -0.2, 60, 70, 0);
        turn(180, 0.5);
        newGyroMove(0, -0.3, 60, 70, 0);
        newGyroMove(90, -0.2, 10, 70, 0);
    }
}
