package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name = "Robot Position", group = "Development")

public class RobotPosition extends AutoMethods {
    public void runOpMode() {
        initRobot();

        while(opModeIsActive()) {
            telemetry.addData("Left", leftBackDrive.getCurrentPosition());
            telemetry.addData("Right", rightBackDrive.getCurrentPosition());
            telemetry.addData("Center", centerEncoder.getCurrentPosition());

            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            telemetry.addData("Gyro", orientation.firstAngle);

            telemetry.update();
        }
    }
}
