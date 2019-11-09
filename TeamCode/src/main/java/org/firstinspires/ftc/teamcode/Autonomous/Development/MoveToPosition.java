package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name = "Move To Position", group = "Development")

public class MoveToPosition extends AutoMethods {
    public void runOpMode() {
        initRobot();

        ///DON"T DO THIS
        ////INSTEAD DO THE THING WHERE IT GOES FOR Y FIRST THEN FOR X OR SOMETHING
        ////LIKE ONE AFTER OTHER NOT BOTH AT ONCE

        double x = 0;
        double y = 0;

        double left = 0;
        double right = 0;

        //go to:
        double xFinal = 50*ENCIN;
        double yFinal = 50*ENCIN;

        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        double degreeFinal = 0;
        if (xFinal != Math.abs(xFinal) && yFinal != Math.abs(yFinal)) {
            degreeFinal = Math.toDegrees(Math.atan((yFinal-y)/(xFinal-x))) + 180;
        }
        else if (xFinal != Math.abs(xFinal)) {
            degreeFinal = Math.toDegrees(Math.atan((yFinal-y)/(xFinal-x))) + 180;
        }
        else if (yFinal != Math.abs(yFinal)) {
            degreeFinal = Math.toDegrees(Math.atan((yFinal-y)/(xFinal-x)));
        }
        else {
            degreeFinal = Math.toDegrees(Math.atan((yFinal-y)/(xFinal-x)));
        }

        orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        float rot = orientation.firstAngle+180;
        while(degreeFinal < rot) {
            leftBackDrive.setPower(0.5);
            rightBackDrive.setPower(-0.5);
            telemetry.addData("Gyro+180", rot);
            telemetry.update();
            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            rot = orientation.firstAngle+180;
        }
        while (degreeFinal > rot) {
            leftBackDrive.setPower(0.3);
            rightBackDrive.setPower(-0.3);
            telemetry.addData("Gyro+180", rot);
            telemetry.update();
            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            rot = orientation.firstAngle+180;
        }

        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);

        while(xFinal > x && yFinal > y) {

            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            rot = orientation.firstAngle+180;
            telemetry.addData("Gyro+180", rot);

            leftBackDrive.setPower(Range.clip(rot/degreeFinal, -1, 1));
            rightBackDrive.setPower(Range.clip(degreeFinal/rot, -1, 1));

            double rad = Math.toRadians(rot);
            x += -1*Math.sin(rad)*(((leftBackDrive.getCurrentPosition()-left) + (rightBackDrive.getCurrentPosition()-right)) / 2.0);
            y += Math.cos(rad)*(((leftBackDrive.getCurrentPosition()-left) + (rightBackDrive.getCurrentPosition()-right)) / 2.0);
            left = leftBackDrive.getCurrentPosition();
            right = rightBackDrive.getCurrentPosition();

            telemetry.update();
        }
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
    }
}
