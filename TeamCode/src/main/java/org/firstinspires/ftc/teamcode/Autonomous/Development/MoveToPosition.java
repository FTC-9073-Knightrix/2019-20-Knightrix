package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Disabled
@Autonomous(name = "Move To Position", group = "Development")

public class MoveToPosition extends AutoMethods {
    public void runOpMode() {
        initRobot();

        double x = 0;
        double y = 0;

        double left = 0;
        double right = 0;

        int xInch = 50;
        int yInch = 50;

        //go to place*inches:
        double xFinal = xInch*ENCIN;
        double yFinal = yInch*ENCIN;

        float rot = 0;

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while(Math.abs(x)<Math.abs(xFinal)) {
            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            rot = orientation.firstAngle+180;

            leftFrontDrive.setPower(0.5 * (xInch/Math.abs(xInch)));
            leftBackDrive.setPower(0.5 * (xInch/Math.abs(xInch)));
            rightFrontDrive.setPower(0.5 * (xInch/Math.abs(xInch)));
            rightBackDrive.setPower(0.5 * (xInch/Math.abs(xInch)));

            double rad = Math.toRadians(rot);
            x += -1*Math.sin(rad)*(((((leftBackDrive.getCurrentPosition()+leftFrontDrive.getCurrentPosition())/2.0)-left) + (((rightBackDrive.getCurrentPosition()+rightFrontDrive.getCurrentPosition())/2.0)-right)) / 2.0);
            y += Math.cos(rad)*(((leftBackDrive.getCurrentPosition()-left) + (rightBackDrive.getCurrentPosition()-right)) / 2.0);
            left = (leftBackDrive.getCurrentPosition() + leftFrontDrive.getCurrentPosition()) / 2.0;
            right = (rightBackDrive.getCurrentPosition() + rightFrontDrive.getCurrentPosition()) / 2.0;
        }

        while(Math.abs(y)<Math.abs(yFinal)) {
            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            rot = orientation.firstAngle+180;

            leftFrontDrive.setPower(0.5 * (yInch/Math.abs(yInch)));
            leftBackDrive.setPower(-0.5 * (yInch/Math.abs(yInch)));
            rightFrontDrive.setPower(-0.5 * (yInch/Math.abs(yInch)));
            rightBackDrive.setPower(0.5 * (yInch/Math.abs(yInch)));

            double rad = Math.toRadians(rot);
            x += -1*Math.sin(rad)*(((((leftBackDrive.getCurrentPosition()+leftFrontDrive.getCurrentPosition())/2.0)-left) + (((rightBackDrive.getCurrentPosition()+rightFrontDrive.getCurrentPosition())/2.0)-right)) / 2.0);
            y += Math.cos(rad)*(((leftBackDrive.getCurrentPosition()-left) + (rightBackDrive.getCurrentPosition()-right)) / 2.0);
            left = (leftBackDrive.getCurrentPosition() + leftFrontDrive.getCurrentPosition()) / 2.0;
            right = (rightBackDrive.getCurrentPosition() + rightFrontDrive.getCurrentPosition()) / 2.0;
        }

        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);
    }
}
