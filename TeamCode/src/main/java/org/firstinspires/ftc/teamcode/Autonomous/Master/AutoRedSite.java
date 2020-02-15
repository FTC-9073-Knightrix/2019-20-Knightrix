package org.firstinspires.ftc.teamcode.Autonomous.Master;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name = "Red Buildsite")
@Disabled
public class AutoRedSite extends AutoMethods {
    public void runOpMode() {
        initRobot();
        waitForStart();

        //Move towards build site
        newGyroMove(0, -0.3, 54, 70,0);
        //Drop blockservo
        sideServo.setPosition(1);
        sleep(1500);
        //Pull back buildsite
        orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        //Get the current degree of the robot
        angle = orientation.firstAngle;

        int degrees = -90;
        double power = -0.3;
        //While the difference between the target angle and current angle is greater than three degrees
        while (opModeIsActive() && Math.abs(degrees - angle) > 1) {
            leftFrontDrive.setPower(-power);
            rightFrontDrive.setPower(-power/4);
            leftBackDrive.setPower(-power);
            rightBackDrive.setPower(-power/4);

            //Get the current position of the robot
            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            //Get the current degree of the robot
            angle = orientation.firstAngle;
        }
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);

        newGyroMove(0, -0.2, 30, 70, 0);
        sideServo.setPosition(0);
        sleep(1000);

        newGyroMove(90, 0.2, 15, 70, 0);
        straighten(-90,0.3);
        newGyroMove(0, 0.3, 70, 70, 0);
        turn(-179,-0.3);
    }
}
