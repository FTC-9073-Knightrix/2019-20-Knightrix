package org.firstinspires.ftc.teamcode.Autonomous.Master;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name="Red Stone")

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
        newGyroMove(0, 1,35,60, 500);
        //Turn to face stones with camera
        turn(90, 0.5);
        //Move to the first stone
        //newGyroMove(0, 0.4, 2,60, 0);
        //newGyroMove(0, 0.4, 1,60, 0);
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
            newGyroMove(0, 0.4, 13,60, 0);
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
                newGyroMove(0, 0.4, 13,60, 0);
            }
        }
        //Adjust so that the arm grabs from the middle of the stone
        ///newGyroMove(0, 0.4, 2, 0);
        blockServo.setPosition(0.75);
        sleep(1000);
        //Approach the stone
        //newGyroMove(90, -0.8, 6,60, 0);
        while (rightRange.getDistance(DistanceUnit.CM) > 9 && opModeIsActive()) {
            move(90, (float)-0.3, 0);
        }
        move(0,0,0);
        //Set the arm down
        blockServo.setPosition(0.81);
        blockGrabServo.setPosition(0);
        //Wait for the arm to fully go down
        sleep(500);
        blockServo.setPosition(0.23);
        sleep(500);
        //Move a bit so the arm can adjust in case it gets stuck on either side of gap
        //newGyroMove(0, 1, 2,60, 0);
        //Pull the stone out
        newGyroMove(90, 0.8, 11,60, 0);
        //Move to the other side of the field
        newGyroMove(0,-1,80 + ((setup-2)*10)+10,60,0);
        //Let go of the stone
        blockServo.setPosition(0.81);
        //Wait for the arm to fully go up
        sleep(500);
        blockGrabServo.setPosition(0.54);
        sleep(500);
        blockServo.setPosition(0.23);
        //Wait for the arm to fully go up
        sleep(500);
        //If it is the left or middle stone
        if (setup < 3) {
            //newGyroMove(90, -0.5, 1,60, 0);
            //Move to the second skystone
            newGyroMove(0, 1, 85 + ((setup + 2) * 10)+10,60, 0);
            //Approach the stone
            blockServo.setPosition(0.75);
            sleep(500);
            while (rightRange.getDistance(DistanceUnit.CM) < 5 && opModeIsActive()) {
                move(90, (float)0.3, 0);
            }
            while (rightRange.getDistance(DistanceUnit.CM) > 9 && opModeIsActive()) {
                move(90, (float)-0.3, 0);
            }
            move(0,0,0);
            //newGyroMove(90, -0.8, 12,60, 0);
            //Set the arm down
            blockServo.setPosition(0.81);
            blockGrabServo.setPosition(0);
            //Wait for the arm to fully go down
            sleep(500);
            blockServo.setPosition(0.23);
            sleep(500);
            //Move a bit to adjust the arm in case it gets stuck on either side of gap
            //newGyroMove(0, 1, 2,60, 0);
            //Pull the stone out
            newGyroMove(90, 0.8, 6,60, 0);
            //Take the skystone to the other side of the field
            newGyroMove(0, -1, 80 + ((setup+2) * 10),60, 0);
            //Let go of the arm
            blockServo.setPosition(0.81);
            //Wait for the arm to fully go up
            sleep(500);
            blockGrabServo.setPosition(0.54);
            liftMotor.setPower(1);
            liftMotor.setTargetPosition(-2500);
            while(liftMotor.getCurrentPosition() > -2300 && opModeIsActive()) {
                //wait
            }
            blockServo.setPosition(0);
            //Wait for the arm to fully go up
            sleep(500);
            liftMotor.setTargetPosition(0);
            //Turn to get ready for the drivers
            turn(0, -0.5);
            //Go park
            newGyroMove(90, 1, 20,60, 0);
        }
        //If it is the right stone
        else {
            //Turn to get ready for the drivers
            turn(0,-0.5);
            //Go park
            newGyroMove(90, 1, 10,60, 0);
        }
    }
}
