package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

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

        while(opModeIsActive()) {
            runVuforia();

            if (VuX > 0) {
                telemetry.addLine("TURN LEFT");
                leftBackDrive.setPower(Range.clip(VuX / 300, -1, 1));
                rightBackDrive.setPower(Range.clip(-VuX / 300, -1, 1));
            } else if (VuX < 0) {
                telemetry.addLine("TURN RIGHT");
                leftBackDrive.setPower(Range.clip(VuX / 300, -1, 1));
                rightBackDrive.setPower(Range.clip(-VuX / 300, -1, 1));
            } else {
                telemetry.addLine("CENTERED");
                leftBackDrive.setPower(0);
                rightBackDrive.setPower(0);
            }
        }
    }
}
