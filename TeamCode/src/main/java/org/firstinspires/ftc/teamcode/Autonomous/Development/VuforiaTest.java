package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name = "Vuforia Test", group = "Development")

public class VuforiaTest extends AutoMethods {
    public void runOpMode() {
        initVuStone();
        initRobot();

        waitForStart();

        movePosition(5, 0.4);

        orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        int Angle = (int) orientation.firstAngle + 180;
        //while (Angle > 130) {
            while(opModeIsActive()) {
            rightBackDrive.setPower(-0.3);
            leftBackDrive.setPower(0.3);
            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            Angle = (int) orientation.firstAngle + 180;
            telemetry.addData("Angle", Angle);
            telemetry.update();

            //FOR NEXT MEETING... FLIP THE REV SIDEWAYS! SO THAT THE ANGLE WORKS!!!
        }
        rightBackDrive.setPower(0);

        movePosition(22, 0.4);

        runVuforia();
    }
}
