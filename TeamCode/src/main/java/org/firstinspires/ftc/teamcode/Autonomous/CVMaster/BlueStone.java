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

@Autonomous(name = "BlueStone", group = "CV")
public class BlueStone extends WebcamCV {
    public void runOpMode(){
        double distance;
        //Initialize robot
        initRobot();
        //Wait until the robot starts
        waitForStart();

        capstoneServo.setPosition(1);

        //Detects stone position (Left, Center, Right) until it is found
        String skystone = stageSwitchingPipeline.skystone();
        while(opModeIsActive() && skystone.equals("")) {
            sleep(100);
            skystone = stageSwitchingPipeline.skystone();
        }
        //Closes camera
        phoneCam.closeCameraDevice();

        //Grabber open
        blockGrabServo.setPosition(0.54);
        //Side servo out
        blockServo.setPosition(0.7);

        //Get closer to skystone wall using range sensor
        boolean TaskPending = true;
        double LeftRangeValue = 0;
        while (opModeIsActive() && TaskPending) {
            LeftRangeValue = leftRange.getDistance(DistanceUnit.CM);
            float MyPower = 0;
            //Too far away, get closer slowly
            if (LeftRangeValue > 18) {
                MyPower = (float) -0.3;
            }
            //Too far away, get closer faster
            if (LeftRangeValue > 28) {
                MyPower = (float) -0.6;
            }
            //Too close, move further slowly
            if (LeftRangeValue < 16) {
                MyPower = (float) 0.3;
            }
            //Perfect
            if (LeftRangeValue >= 16 && LeftRangeValue <= 17) {
                MyPower = 0;
                TaskPending = false;
            }
            move(90, MyPower, 0);
        }

        //Adjust to get ready to move
        straighten(0, 0.5);

        //Move robot forwards/backwards based on skystone position
        float MyPower = (float) 0.3;
        float MyDistance = 5;
        if (skystone.equals("Left")) { // Go Forwards
            MyPower = (float) 0.3;
            MyDistance = 21;
        }
        else if (skystone.equals("Right")) { // Go Backwards
            MyPower = (float)-0.3;
            MyDistance = 7;
        }
        else { // Center, move forwards a little
            MyPower = (float) 0.3;
            MyDistance =  4;
        }
        //straighten(0, 0.5);
        newGyroMove(0, MyPower, MyDistance, 60, 0);

        //Set arm down a little
        blockServo.setPosition(0.75);
        //Move closer to the stone
        newGyroMove(90,-0.2,8,60,0);
        //Set arm fully down once next to stone
        blockServo.setPosition(1);
        //Wait for arm to go down
        sleep(300);
        // Closes the hand
        blockGrabServo.setPosition(0);
        //Waits for the hand to fully close
        sleep(600);
        // Raises the skystone
        blockServo.setPosition(0.23);
        //Pull the stone out
        newGyroMove(90, 0.4, 13,60, 0);
        //Align to get ready to move to other side
        straighten(0, 0.5);
        //Move to the other side of the field to drop off the stone
        if (skystone.equals("Left")) {
            newGyroMove(0,1,117,75,0);
        }
        else if (skystone.equals("Right")) {
            newGyroMove(0,1,145,75,0);
        }
        else {
            newGyroMove(0,1,130,75,0);
        }

        blockServo.setPosition(0.6);
        distance = rightRange.getDistance(DistanceUnit.CM);
        // store distance
        // get closer towards foundation
        newGyroMove(-90, 0.5, Math.max(distance - 3,1), 60, 0);
        //distance = leftRange.getDistance(DistanceUnit.CM);
        //newGyroMove(-90,0.25,distance/2,60,0);
        // once reach distance
        //stop moving
        // drop the servo
        //Wait for the arm to fully Down
        //sleep(800);
        // Opens the hand
        blockGrabServo.setPosition(0.54);
        straighten(0, 0.5);
        //Wait for the grabber to open
        //sleep(300);
        // go back to start
        // Raises the hand

        //Wait for side servo to fully go up
        //sleep(300);


        //If the right skystone, turn 180 degrees to pick up with the front intake wheels
        if (skystone.equals("Right")) {
            newGyroMove(90, 1, Math.max(distance - 5,1), 60, 0);
            blockServo.setPosition(0.23);
            turn(180, 0.4);
            capstoneServo.setPosition(0.5);
        }

        // Goes back into stone zone based on three different distances
        if (skystone.equals("Left")) {
            newGyroMove(90, 1, Math.max(distance - 5,1), 60, 0);
            blockServo.setPosition(0.23);
            straighten(0, 0.5);
            newGyroMove(0,-1,155,75,0);
        }
        else if (skystone.equals("Right")) {
            newGyroMove(0,1,157,75,0);
        }
        else {
            newGyroMove(90, 1,Math.max(distance - 5,1), 60, 0);
            blockServo.setPosition(0.23);
            straighten(0, 0.5);
            newGyroMove(0,-1,165,75,0);
        }

        //Use the intake if the skystone is the right one (closest to wall)
        if (skystone.equals("Right")) {
            // Get Closer to skystone wall
            /*double RightRangeValue = 0;
            TaskPending = true;
            while (opModeIsActive() && TaskPending) {
                // Read leftRange Sensor
                RightRangeValue = rightRange.getDistance(DistanceUnit.CM);
                MyPower = 0;
                //Too far, move slowly closer
                if (RightRangeValue < 67) {
                    MyPower = (float) -0.3;
                }
                //Too close, move slowly away
                if (RightRangeValue > 73) {
                    MyPower = (float) 0.3;
                }
                //Perfect
                if (RightRangeValue >= 67 && RightRangeValue <= 73) {
                    MyPower = 0;
                    TaskPending = false;
                }
                move(90, MyPower, 0);
            }*/
            //Turn to angle the skystone
            //turn(-135, 0.5);
            newGyroMove(90,1,30,60,0);
            // Turn on the intake
            intakeLeft.setPower(1);
            intakeRight.setPower(1);
            //Move forwards to the skystone
            newGyroMove(0, 0.3, 5, 60, 0);
            newGyroMove(-90,1,30,60,0);
            //Wait a little until the skystone goes into the robot
            //sleep(600);
            //Take the robot out
            //newGyroMove(0, -0.3, 15, 60, 0);
            //Turn so that the robot can cross over to the other side again
            straighten(-180, 0.5);
            // Move to the Construction Area
            newGyroMove(0, -1, 115, 100, 0);
            // Drop the skystone as the robot moves backwards
            //intakeLeft.setPower(-0.6);
            //intakeRight.setPower(-0.6);
            blockServo.setPosition(0.6);
            liftMotor.setTargetPosition(-2600);
            //Don't run the motor yet
            liftMotor.setPower(1);
            // Turn OFF the intake
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
            // Continues moving backward
            newGyroMove(0, -1, 40, 75, 0);
            // Turn OFF the intake
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
            sideServo.setPosition(0.7);
            blockServo.setPosition(0);
            blockGrabServo.setPosition(0.2);
            distance = leftRange.getDistance(DistanceUnit.CM);
            turn(90,-0.5);
            newGyroMove(0,-0.7,Math.max(distance-3,1),60,0);
        }
        //If the skystone is the center or left one
        else {
            //Set the arm down a little
            blockServo.setPosition(.7);
            //Wait for the arm to go down a little
            sleep(300);

            // Get Closer to skystone wall using the range sensor
            TaskPending = true;
            double RightRangeValue = 0;
            while (opModeIsActive() && TaskPending) {
                // Read rightRange Sensor
                RightRangeValue = rightRange.getDistance(DistanceUnit.CM);
                MyPower = 0;
                //Too far, move slowly closer
                if (RightRangeValue > 20) {
                    MyPower = (float) -0.3;
                }
                //Too far, move quickly closer
                if (RightRangeValue > 28) {
                    MyPower = (float) -0.6;
                }
                //Too close, move slowly away
                if (RightRangeValue < 17) {
                    MyPower = (float) 0.3;
                }
                //Perfect
                if (RightRangeValue >= 17 && RightRangeValue <= 20) {
                    MyPower = 0;
                    TaskPending = false;
                }
                move(90, MyPower, 0);
            }
            //Align to the skystone
            straighten(0, 0.5);
            //Move the arm down
            blockServo.setPosition(0.75);
            //Move closer to the stone
            newGyroMove(90,-0.2,10,60,0);
            //Move the arm fully down once next to the skystone
            blockServo.setPosition(1);
            //Wait for the arm to fully go down
            sleep(300);
            // Closes the hand
            blockGrabServo.setPosition(0);
            //Wait for the grabber to fully close
            sleep(600);
            // Raises the skystone
            blockServo.setPosition(0.23);
            //Pull the stone out
            newGyroMove(90, 0.4, 6,60, 0);
            //Align to get ready to go to the other side
            straighten(0, 0.5);
            //Depending on the skystone get to the other side
            if (skystone.equals("Left")) {
                newGyroMove(0,1,175,75,0);
            }
            else if (skystone.equals("Center")) {
                newGyroMove(0,1,190,75,0);
            }
            blockServo.setPosition(0.6);
            distance = leftRange.getDistance(DistanceUnit.CM);
            // store distance
            // get closer towards foundation
            newGyroMove(-90,0.5,Math.max(distance-3,1),60,0);
            //distance = leftRange.getDistance(DistanceUnit.CM);
            //newGyroMove(-90,0.25,distance/2,60,0);
            // once reach distance
            //stop moving
            // drop the servo
            //blockServo.setPosition(1);
            //Wait for the arm to fully Down
            //sleep(800);
            //Make the lift go up to swap
            liftMotor.setTargetPosition(-2600);
            //Don't run the motor yet
            liftMotor.setPower(1);
            // Opens the hand
            blockGrabServo.setPosition(0.54);
            //Wait for the grabber to open
            //sleep(300);
            // go back to start
            newGyroMove(90, 1, Math.max(distance-3,1), 60, 300);
            // Raises the hand
            blockServo.setPosition(0);
            blockGrabServo.setPosition(0.2);
            //Wait for side servo to fully go up
            //sleep(300);

            /*//Set the arm down
            blockServo.setPosition(1);
            //Wait for the arm to fully Down
            sleep(600);
            // Opens the hand
            blockGrabServo.setPosition(0.54);
            //resets the angle of the robot
            straighten(0, 0.5);*/


            //turn foundation
            sideServo.setPosition(0.7);
            turn(90,0.5);
            newGyroMove(0,-0.7,Math.max(distance-3,1),60,0);
        }

        sideServo.setPosition(1);
        liftMotor.setTargetPosition(0);
        tapeMotor.setPower(0);
        sleep(600);
        //Pull back buildsite
        /*orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        //Get the current degree of the robot
        angle = orientation.firstAngle;

        //double timer = getRuntime();
        //tapeMotor.setPower(1);
        tapeMotor.setPower(0);

        double power = 1;
        //While the difference between the target angle and current angle is greater than three degrees
        while (opModeIsActive() && angle < 135) {
            leftFrontDrive.setPower(power/5);
            rightFrontDrive.setPower(power);
            leftBackDrive.setPower(power/5);
            rightBackDrive.setPower(power);

            //Get the current position of the robot
            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            //Get the current degree of the robot
            angle = orientation.firstAngle;
            if (tapeMotor.getCurrentPosition() > 1400) {
                tapeMotor.setPower(0);
            }
        }
        //sideServo.setPosition(0);
        /*while (opModeIsActive() && angle < 180 && angle > -170) {
            leftFrontDrive.setPower(-power);
            rightFrontDrive.setPower(-power/4);
            leftBackDrive.setPower(-power);
            rightBackDrive.setPower(-power/4);

            //Get the current position of the robot
            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            //Get the current degree of the robot
            angle = orientation.firstAngle;
        }*/
        //move straight back to align foundation
        //newGyroMove(0, -1,5,75,0);
        /*turn2(-145,0.5);
        if (tapeMotor.getCurrentPosition() > 1400) {
            tapeMotor.setPower(0);
        }
        sideServo.setPosition(0);
        sleep(300);
        if (tapeMotor.getCurrentPosition() > 1400) {
            tapeMotor.setPower(0);
        }
        newGyroMove(0, 1, 3, 100, 0);
        if (tapeMotor.getCurrentPosition() > 1400) {
            tapeMotor.setPower(0);
        }

        //straighten(-180, 1);
        //newGyroMove(0,-1,15,100,0);*/

        //newGyroMove(45,-1,15,100,0);


        newGyroMove(0,0.7,30,100,0);
        sideServo.setPosition(0);
        sleep(300);


        //Save the orientation of the robot so that it can be used for the teleop
        File gyroPosition = AppUtil.getInstance().getSettingsFile("gyroPosition.txt");
        orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        ReadWriteFile.writeFile(gyroPosition, String.valueOf(orientation.firstAngle + 90));
        //tapeMotor.setPower(0);
        //Stop the program (it's done)
        stop();
    }
}