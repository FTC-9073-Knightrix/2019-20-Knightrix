package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name="Lift Test", group="Development")
@Disabled
public class LiftMotor extends AutoMethods {
    public void runOpMode() {
        initRobot();

        waitForStart();
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setTargetPosition(1000);
        liftMotor.setPower(0.7);
        while (opModeIsActive() && liftMotor.getCurrentPosition() < 1000) { }
        liftMotor.setTargetPosition(0);
        while (opModeIsActive() && liftMotor.getCurrentPosition() > 0) { }
        stop();
    }
}