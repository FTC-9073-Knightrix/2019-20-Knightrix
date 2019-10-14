package org.firstinspires.ftc.teamcode.TeleOp.Development;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.TeleOp.TeleOpMethods;

@TeleOp(name = "Count Encoders", group = "Development")

public class CountEncoders extends OpMode {
    DcMotor Enc0;
    DcMotor Enc1;
    DcMotor Enc2;

    public void init() {
        Enc0 = hardwareMap.dcMotor.get("E0");
        Enc1 = hardwareMap.dcMotor.get("E1");
        Enc2 = hardwareMap.dcMotor.get("E2");

        Enc0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Enc1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Enc2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Enc0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Enc1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Enc2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void loop() {
        telemetry.addData("E0", Enc0.getCurrentPosition());
        telemetry.addData("E1", Enc1.getCurrentPosition());
        telemetry.addData("E2", Enc2.getCurrentPosition());
        telemetry.update();
    }
}
