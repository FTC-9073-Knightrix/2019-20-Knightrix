package org.firstinspires.ftc.teamcode.Autonomous.Master;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name="BlueStone")

public class AutoBlueRight extends AutoMethods {
    public void runOpMode() {
        initRobot();
        initVuStone();

        double x = 0;
        double x2 = 0;

        double left = 0;
        double right = 0;

        waitForStart();

        /* setup robot aligned to the wall
        align robot 48 inches from left
        go forwards until left range sees block close
        go left until spots the block
        take note of which block position it is (left/center/right)
            to come back for second block after
        grab block
         */

        ///FIRST STONE
        //Move forwards
        gyroMove(0, 0.6, 35, 500);
        //Turn to face stones with camera
        turn(90, 0.5);

        //Move to each stone individually
        gyroMove(0, -0.4, 5, 0);
        timer = getRuntime() + 1.5;
        while (opModeIsActive() && !((VuforiaTrackableDefaultListener)stoneTarget.getListener()).isVisible() && getRuntime() < timer) {
            runVuforia();
        }
        //SECOND OR THIRD BLOCK...
        if (!((VuforiaTrackableDefaultListener)stoneTarget.getListener()).isVisible()) {
            setup++; //2nd block
            gyroMove(0, -0.4, 10, 0);

            timer = getRuntime() + 1.5;
            while (opModeIsActive() && !((VuforiaTrackableDefaultListener)stoneTarget.getListener()).isVisible() && getRuntime() < timer) {
                runVuforia();
            }
            //THIRD BLOCK...
            if (!((VuforiaTrackableDefaultListener)stoneTarget.getListener()).isVisible()) {
                setup++; //3rd block
                gyroMove(0, -0.4, 10, 500);
            }
        }
        gyroMove(0, 0.2, 2, 0);
        gyroMove(90, -0.2, 11, 0);
        blockServo.setPosition(.9);
        sleep(500);
        gyroMove(0, -0.2, 1, 0);
        gyroMove(90, 0.2, 16, 0);
        gyroMove(0,0.6,80 + ((setup-1)*10),0);
        blockServo.setPosition(0);
        sleep(500);

        //if (setup < 3) { //if 1 or 2
            gyroMove(0, -0.6, 82 + ((setup + 3) * 10), 0);
            gyroMove(90, -0.2, 16, 0);
            blockServo.setPosition(.9);
            sleep(500);
            gyroMove(0, -0.2, 1, 0);
            gyroMove(90, 0.2, 16, 0);
            gyroMove(0, 0.6, 80 + ((setup + 3) * 10), 0);
            blockServo.setPosition(0);
            sleep(500);
            gyroMove(0, -0.6, 20, 0);
        /*}
        else {
            gyroMove(0, -0.6, 82 + ((setup + 1) * 10), 0);
            gyroMove(0, 1, 1,0);
            gyroMove(0, -1, 1,500);
            turn(-45, -0.5);
            gyroMove(-45, 0.6, 25,0);
            timer = getRuntime() + 1;
            while (opModeIsActive() && getRuntime() < timer) {
                intakeLeft.setPower(1);
                intakeRight.setPower(1);
            }
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
            gyroMove(-45, -0.6, 25,0);
            turn(90, 0.5);
            gyroMove(0, 0.6, 80 + ((setup + 3) * 10), 0);
            gyroMove(0, -0.6, 20, 0);
        }*/
    }
}
