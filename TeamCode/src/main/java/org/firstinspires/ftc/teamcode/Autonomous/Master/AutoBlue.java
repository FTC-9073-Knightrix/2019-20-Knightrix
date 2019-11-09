package org.firstinspires.ftc.teamcode.Autonomous.Master;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

public class AutoBlue extends AutoMethods {
    public void runOpMode() {
        initRobot();
        waitForStart();

        /* setup robot aligned to the wall
        align robot 48 inches from left
        go forwards until left range sees block close
        go left until spots the block
        take note of which block position it is (left/center/right)
            to come back for second block after
        grab block
         */
    }
}
