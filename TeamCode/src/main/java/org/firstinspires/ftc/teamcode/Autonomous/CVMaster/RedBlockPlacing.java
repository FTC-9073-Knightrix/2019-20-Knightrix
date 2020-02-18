package org.firstinspires.ftc.teamcode.Autonomous.CVMaster;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.Autonomous.WebcamCV;

import java.io.File;

@Autonomous(name = "RedBlockPlacing", group = "CV")


public class RedBlockPlacing extends WebcamCV{
    double LeftRangeValue;
    public void runOpMode(){
        initRobot();
        waitForStart();
        double distance;
        Boolean TaskPending = true;
        while(opModeIsActive() && TaskPending){

            newGyroMove(0,-1,115, 75,0);
            distance = leftRange.getDistance(DistanceUnit.CM);
            // store distance
            // get closer towards foundation
            newGyroMove(-90,0.5,distance/2,60,0);
            distance = leftRange.getDistance(DistanceUnit.CM);
            newGyroMove(-90,0.25,distance/2,60,0);
            // once reach distance
            //stop moving
            // drop the servo
            blockServo.setPosition(1);
            //Wait for the arm to fully Down
            sleep(800);
            // Opens the hand
            blockGrabServo.setPosition(0.54);
            //Wait for the grabber to open
            sleep(500);
            // go back to start
            newGyroMove(90, 1, distance, 60, 0);
            // Raises the hand
            blockServo.setPosition(0.23);
            //Wait for side servo to fully go up
            sleep(300);

            TaskPending = false;



            /*while(opModeIsActive() && TaskPending) {
                if (LeftRangeValue > 0.5) {
                    MyPower = (float) -0.2;  //get closer
                }
                //Too far, move quickly closer
                if (LeftRangeValue > 28) {
                    MyPower = (float) -0.4;//get closer faster
                }
                //Perfect
                if (LeftRangeValue >= 0 && LeftRangeValue <= 0.5) {
                    MyPower = 0; //stops moving
                    TaskPending = false;
                }
                move(90, MyPower, 0);
                //Set the arm down
                blockServo.setPosition(1);
                //Wait for the arm to fully Down
                sleep(600);
                // Opens the hand
                blockGrabServo.setPosition(0.54);
                //Wait for the grabber to open
                sleep(300);
                // Raises the hand
                blockServo.setPosition(0.23);
                //Wait for side servo to fully go up
                sleep(300);
                //Move away from the foundation a little to make sure the robot doesn't run into the center beam
                newGyroMove(90,0.2,10,60,0);
            }*/
        }
    }
}
