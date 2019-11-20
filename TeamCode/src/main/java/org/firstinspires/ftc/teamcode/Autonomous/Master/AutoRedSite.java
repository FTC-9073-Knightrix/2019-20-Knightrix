package org.firstinspires.ftc.teamcode.Autonomous.Master;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name = "RedBuildsite")

public class AutoRedSite extends AutoMethods {
    public void runOpMode() {
        initRobot();
        waitForStart();

        /*
        setup robot to align to wall
        run program and go forwards and align 48 inches from wall left
        go until the left range sees stone
        go left and analyze each stone until skystone is centered
        when stone is found, record left/center/right for other block later
        grab stone and go right, under our bridge
        once crossed over, find build side and go to it
        align to the build site
        place block
        push block into the build site
        */


        //Move towards build site
        gyroMove(90, -0.5, 55, 500);
        //Drop blockservo
        blockServo.setPosition(1);
        sleep(1500);
        //Pull back buildsite
        gyroMove(90, 0.6, 80, 500);
        blockServo.setPosition(0);
        sleep(1000);
        gyroMove(0, 0.5, 50, 500);
        gyroMove(90, -0.5, 40, 500);
        gyroMove(0, -0.5, 30, 500);
        gyroMove(90, 0.5, 30, 500);
        gyroMove(0, 0.5, 40, 500);
    }
}
