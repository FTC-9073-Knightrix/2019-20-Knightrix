package org.firstinspires.ftc.teamcode.Autonomous.Master;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

public class AutoBlueLeft extends AutoMethods {
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
    }
}
