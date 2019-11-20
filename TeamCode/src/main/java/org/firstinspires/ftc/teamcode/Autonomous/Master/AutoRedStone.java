package org.firstinspires.ftc.teamcode.Autonomous.Master;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name="RedStone")

public class AutoRedStone extends AutoMethods {
    public void runOpMode() {
        //Initialize the robot itself
        initRobot();
        //Initialize Vuforia
        initVuStone();
        //Wait until "Start" is pressed
        waitForStart();
        ///FIRST STONE
        //Move forwards to the line of stones
        gyroMove(0, 0.6, 35, 500);
        //Turn to face stones with camera
        turn(90, 0.5);
        //Move to the first stone
        gyroMove(0, 0.4, 3, 0);
        //Set the maximum time to look at the stone to 1.5 seconds
        timer = getRuntime() + 1.5;
        //Run Vuforia until either the skystone has been detected or 1.5 seconds have passed
        while (opModeIsActive() && !((VuforiaTrackableDefaultListener)stoneTarget.getListener()).isVisible() && getRuntime() < timer) {
            runVuforia();
        }
        //If it's not the first stone
        if (!((VuforiaTrackableDefaultListener)stoneTarget.getListener()).isVisible()) {
            //Mark that it is the second stone
            setup++;
            //Move right, to the second stone
            gyroMove(0, 0.4, 10, 0);
            //Set the maximum time to look at the stone to 1.5 seconds
            timer = getRuntime() + 1.5;
            //Run Vuforia until either the skystone has been detected or 1.5 seconds have passed
            while (opModeIsActive() && !((VuforiaTrackableDefaultListener)stoneTarget.getListener()).isVisible() && getRuntime() < timer) {
                runVuforia();
            }
            //If it's not the second stone, it is the third one
            if (!((VuforiaTrackableDefaultListener)stoneTarget.getListener()).isVisible()) {
                //Mark that it is the third stone
                setup++;
                //Move to the third stone
                gyroMove(0, 0.4, 13, 0);
            }
        }
        //Adjust so that the arm grabs from the middle of the stone
        ///gyroMove(0, 0.4, 2, 0);
        //Approach the stone
        gyroMove(90, -0.4, 11, 0);
        //Set the arm down
        blockServo.setPosition(.9);
        //Wait for the arm to fully go down
        sleep(1000);
        //Move a bit so the arm can adjust in case it gets stuck on either side of gap
        gyroMove(0, -0.4, 2, 0);
        //Pull the stone out
        gyroMove(90, 0.5, 16, 0);
        //Move to the other side of the field
        gyroMove(0,-0.6,80 + ((setup-2)*10),0);
        //Let go of the stone
        blockServo.setPosition(0);
        //Wait for the arm to fully go up
        sleep(500);
        //If it is the left or middle stone
        if (setup < 3) {
            //Move to the second skystone
            gyroMove(0, 0.7, 83 + ((setup + 2) * 10), 0);
            //Approach the stone
            gyroMove(90, -0.4, 23, 0);
            //Set the arm down
            blockServo.setPosition(.9);
            //Wait for the arm to fully go down
            sleep(500);
            //Move a bit to adjust the arm in case it gets stuck on either side of gap
            gyroMove(0, 0.4, 2, 0);
            //Pull the stone out
            gyroMove(90, 0.5, 20, 0);
            //Take the skystone to the other side of the field
            gyroMove(0, -1, 85 + ((setup) * 10), 0);
            //Let go of the arm
            blockServo.setPosition(0);
            //Wait until the arm is fully up
            sleep(500);
            //Turn to get ready for the drivers
            turn(0, -0.5);
            //Go park
            gyroMove(90, 1, 20, 0);
        }
        //If it is the right stone
        else {
            /*
            //Move to the second skystone
            gyroMove(0, 1, 85 + ((setup) * 10), 0);
            gyroMove(0, 0.5, 10, 0);
            //Approach the stone
            gyroMove(90, -0.4, 12, 0);
            //Set the arm down
            blockServo.setPosition(.9);
            //Wait for the arm to fully go down
            sleep(500);
            //If it is the left or middle stone
            //Turn while grabbing the skystone
            turn(-90, -0.4);
            //Move away from the line of stones
            gyroMove(90, -0.5, 20, 0);
            //Take the stone to the other side of the field
            gyroMove(0, -1, 80, 0);
            //Let go of the stone
            blockServo.setPosition(0);
            //Wait for the arm to fully go up
            sleep(500);*/
            //Turn to get ready for the drivers
            turn(0,-0.5);
            //Go park
            gyroMove(90, 1, 10, 0);
        }
    }
}
