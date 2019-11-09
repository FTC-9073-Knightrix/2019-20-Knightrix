package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name = "Robot Position", group = "Development")

public class RobotPosition extends AutoMethods {
    public void runOpMode() {
        initRobot();

        double x = 0;
        double y = 0;

        double left = 0;
        double right = 0;

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while(opModeIsActive()) {
            leftFrontDrive.setPower(Range.clip(gamepad1.left_stick_y - gamepad1.left_stick_x,-1,1));
            leftBackDrive.setPower(Range.clip(gamepad1.left_stick_y - gamepad1.left_stick_x,-1,1));
            rightFrontDrive.setPower(Range.clip(gamepad1.left_stick_y + gamepad1.left_stick_x, -1, 1));
            rightBackDrive.setPower(Range.clip(gamepad1.left_stick_y + gamepad1.left_stick_x, -1, 1));
            //telemetry.addData("X CM", centerEncoder.getCurrentPosition()/ENCCM);
            //telemetry.addData("Y CM", (leftBackDrive.getCurrentPosition() + rightBackDrive.getCurrentPosition()) / 2 / ENCCM);
            //telemetry.addData("Left", leftBackDrive.getCurrentPosition());
            //telemetry.addData("Right", rightBackDrive.getCurrentPosition());
            //telemetry.addData("Center", centerEncoder.getCurrentPosition());

            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);

            float rot = orientation.firstAngle+180;
            telemetry.addData("Gyro+180", rot);

            double rad = Math.toRadians(rot);
            x += -1*Math.sin(rad)*(((((leftBackDrive.getCurrentPosition()+leftFrontDrive.getCurrentPosition())/2.0)-left) + (((rightBackDrive.getCurrentPosition()+rightFrontDrive.getCurrentPosition())/2.0)-right)) / 2.0);
            y += Math.cos(rad)*(((leftBackDrive.getCurrentPosition()-left) + (rightBackDrive.getCurrentPosition()-right)) / 2.0);
            left = (leftBackDrive.getCurrentPosition() + leftFrontDrive.getCurrentPosition()) / 2.0;
            right = (rightBackDrive.getCurrentPosition() + rightFrontDrive.getCurrentPosition()) / 2.0;
            telemetry.addData("X (CM)", x/ENCCM);
            telemetry.addData("Y (IN)", y/ENCIN);

            telemetry.update();
        }
    }
}
