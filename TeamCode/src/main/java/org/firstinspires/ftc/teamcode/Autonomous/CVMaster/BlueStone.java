package org.firstinspires.ftc.teamcode.Autonomous.CVMaster;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Autonomous.WebcamCV;

@Autonomous(name = "BlueStoneCV", group = "CV")

public class BlueStone extends WebcamCV {
    public void runOpMode(){
        initRobot();

        waitForStart();

        while (opModeIsActive())
        {
            // Set Grabber Fully Open
            blockGrabServo.setPosition(0.54);

            //Detects stone position (Left, Center, Right) and closes camera
            String skystone = stageSwitchingPipeline.skystone();
            while(opModeIsActive() && skystone.equals("")) {
                sleep(100);
                skystone = stageSwitchingPipeline.skystone();
            }
            phoneCam.closeCameraDevice();

            // Set Servo UP
            blockServo.setPosition(0.7);

            // Get Closer to skystone wall
            boolean TaskPending = true;
            double LeftRangeValue = 0;

            while (opModeIsActive() && TaskPending) {
                // Read leftRange Sensor
                // that is located on the back of the robot
                LeftRangeValue = leftRange.getDistance(DistanceUnit.CM);
                //ReadWriteFile.writeFile(LeftRangeFile, String.valueOf(LeftRangeValue)); //Store value into a file
                float MyPower = 0;
                if (LeftRangeValue > 20) { MyPower = (float) -0.2; } //go slower
                if (LeftRangeValue > 28) { MyPower = (float) -0.4; } //go fast
                if (LeftRangeValue < 17) { MyPower = (float) 0.2; }  // you are too close move away
                if (LeftRangeValue >= 17 && LeftRangeValue <= 20) {  // you are right where I wanted
                    MyPower = 0;
                    TaskPending = false;
                }
                move(90, MyPower, 0);
            }
            straighten(0, 0.5);
            // Move End


            // Move robot forwards/backwards based on skystone position
            if (skystone.equals("Left")) { // Go Forwards
                newGyroMove(0,0.3,20,60,0);
            }
            else if (skystone.equals("Right")) { // Go Backwards
                newGyroMove(0,-0.3,10,60,0);
            }
            else {                                // Center, move forwards a little
                newGyroMove(0, 0.3, 5, 60, 0);
            }


            // Grabs the stone
            //Set the arm down
            blockServo.setPosition(1);
            // Move closer to the stone
            newGyroMove(90,-0.2,3,60,0);

            // Closes the hand
            blockGrabServo.setPosition(0);
            sleep(600);
            // Raises the skystone
            blockServo.setPosition(0.23);
            // sleep(600);
            //Pull the stone out
            newGyroMove(90, 0.4, 9,60, 0);
            straighten(0, 0.5);




            if (skystone.equals("Left")) {
                newGyroMove(0,1,95,60,0);
            }
            else if (skystone.equals("Right")) {
                newGyroMove(0,1,125,60,0);
            }
            else {
                newGyroMove(0,1,110,60,0);
            }


            // Drop the skystone
            //Set the arm down
            blockServo.setPosition(1);
            //Wait for the arm to fully Down
            sleep(600);
            // Opens the hand
            blockGrabServo.setPosition(0.54);
            sleep(300);
            // Raises the hand
            blockServo.setPosition(0.23);
            sleep(300);

            // Turn 180 degrees
            turn(180,0.5);


            // Goes back into stone zone based on three different distances
            if (skystone.equals("Left")) {
                newGyroMove(0,1,115,75,0);
            }
            else if (skystone.equals("Right")) {
                newGyroMove(0,1,145,75,0);
            }
            else {
                newGyroMove(0,1,130,75,0);
            }

            // Goes at an angle to skystone
            turn(-135,0.5);

            // Turn on the intake
            intakeLeft.setPower(1);
            intakeRight.setPower(1);

            newGyroMove(0, 0.3, 12, 60, 0);
            sleep(600);
            newGyroMove(0, -0.3, 12, 60, 0);

            // Aligh back to be straight to the walls
            turn(-180,-0.5);

            // Turn OFF the intake
            intakeLeft.setPower(0);
            intakeRight.setPower(0);

            if (skystone.equals("Left")) {
                newGyroMove(0,-1,55,100,0);
            }
            else if (skystone.equals("Right")) {
                newGyroMove(0,-1,85,100,0);
            }
            else {
                newGyroMove(0,-1,70,100,0);
            }

            // Drop the skystone
            intakeLeft.setPower(-0.6);
            intakeRight.setPower(-0.6);

            // Continues moving backward
            newGyroMove(0,-1,40,75,0);

            // Turn OFF the intake
            intakeLeft.setPower(0);
            intakeRight.setPower(0);

            stop();
        }
    }
}
