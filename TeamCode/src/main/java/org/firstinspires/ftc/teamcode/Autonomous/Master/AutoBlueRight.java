package org.firstinspires.ftc.teamcode.Autonomous.Master;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

public class AutoBlueRight extends AutoMethods {
    public void runOpMode() {
        initRobot();

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
        gyroMove(0, 0.5, 120, 500);
        //Turn to face stones with camera
        turn(-90, -0.5);
        //Go slowly as camera detects
        while (!((VuforiaTrackableDefaultListener)stoneTarget.getListener()).isVisible()) {
            leftFrontDrive.setPower(-0.3);
            leftBackDrive.setPower(-0.3);
            rightFrontDrive.setPower(-0.3);
            rightBackDrive.setPower(-0.3);

            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            float rot = orientation.firstAngle+180;

            double rad = Math.toRadians(rot);
            x += -1*Math.sin(rad)*(((((leftBackDrive.getCurrentPosition()+leftFrontDrive.getCurrentPosition())/2.0)-left) + (((rightBackDrive.getCurrentPosition()+rightFrontDrive.getCurrentPosition())/2.0)-right)) / 2.0);
            left = (leftBackDrive.getCurrentPosition() + leftFrontDrive.getCurrentPosition()) / 2.0;
            right = (rightBackDrive.getCurrentPosition() + rightFrontDrive.getCurrentPosition()) / 2.0;
        }
        //Once stone found pick it up
        blockServo.setPosition(90);
        gyroMove(90, -0.5, 20, 500);
        //Go back to original position
        left = 0;
        right = 0;
        while (Math.abs(x) > Math.abs(x2)) {
            leftFrontDrive.setPower(0.3);
            leftBackDrive.setPower(0.3);
            rightFrontDrive.setPower(0.3);
            rightBackDrive.setPower(0.3);

            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            float rot = orientation.firstAngle+180;

            double rad = Math.toRadians(rot);
            x2 += -1*Math.sin(rad)*(((((leftBackDrive.getCurrentPosition()+leftFrontDrive.getCurrentPosition())/2.0)-left) + (((rightBackDrive.getCurrentPosition()+rightFrontDrive.getCurrentPosition())/2.0)-right)) / 2.0);
            left = (leftBackDrive.getCurrentPosition() + leftFrontDrive.getCurrentPosition()) / 2.0;
            right = (rightBackDrive.getCurrentPosition() + rightFrontDrive.getCurrentPosition()) / 2.0;
        }
        //move under bar
        gyroMove(90, -0.5, 50, 500);
        gyroMove(0, 0.5, 100, 500);
        //Release stone
        blockServo.setPosition(0);

        ///SECOND STONE
        //Go back to stones
        gyroMove(0, -0.5, 100, 500);
        gyroMove(90, 0.5, 70, 500);
        //Go to second stone
        x2 = 0;
        left = 0;
        right = 0;
        while(2*Math.abs(x) > Math.abs(x2)) {
            leftFrontDrive.setPower(-0.3);
            leftBackDrive.setPower(-0.3);
            rightFrontDrive.setPower(-0.3);
            rightBackDrive.setPower(-0.3);

            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            float rot = orientation.firstAngle+180;

            double rad = Math.toRadians(rot);
            x2 += -1*Math.sin(rad)*(((((leftBackDrive.getCurrentPosition()+leftFrontDrive.getCurrentPosition())/2.0)-left) + (((rightBackDrive.getCurrentPosition()+rightFrontDrive.getCurrentPosition())/2.0)-right)) / 2.0);
            left = (leftBackDrive.getCurrentPosition() + leftFrontDrive.getCurrentPosition()) / 2.0;
            right = (rightBackDrive.getCurrentPosition() + rightFrontDrive.getCurrentPosition()) / 2.0;
        }
        //Once stone found pick it up
        blockServo.setPosition(90);
        gyroMove(90, -0.5, 20, 500);
        //Go back to original position
        x2 = 0;
        left = 0;
        right = 0;
        while(2*Math.abs(x) > Math.abs(x2)) {
            leftFrontDrive.setPower(0.3);
            leftBackDrive.setPower(0.3);
            rightFrontDrive.setPower(0.3);
            rightBackDrive.setPower(0.3);

            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            float rot = orientation.firstAngle+180;

            double rad = Math.toRadians(rot);
            x2 += -1*Math.sin(rad)*(((((leftBackDrive.getCurrentPosition()+leftFrontDrive.getCurrentPosition())/2.0)-left) + (((rightBackDrive.getCurrentPosition()+rightFrontDrive.getCurrentPosition())/2.0)-right)) / 2.0);
            left = (leftBackDrive.getCurrentPosition() + leftFrontDrive.getCurrentPosition()) / 2.0;
            right = (rightBackDrive.getCurrentPosition() + rightFrontDrive.getCurrentPosition()) / 2.0;
        }
        //move under bar
        gyroMove(90, -0.5, 50, 500);
        gyroMove(0, 0.5, 100, 500);
        //Release stone
        blockServo.setPosition(0);
        //Park under bar
        gyroMove(0, -0.5, 40, 0);
    }
}
