package org.firstinspires.ftc.teamcode.opModes.teleop

import com.arcrobotics.ftclib.command.CommandOpMode
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.arcrobotics.ftclib.hardware.motors.Motor
import org.firstinspires.ftc.teamcode.commands.intake.IntakeCommand
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeSubsystem

class MainTeleOp : CommandOpMode() {
    private lateinit var intakeMotor: Motor

    private lateinit var intakeSubsystem: IntakeSubsystem

    private lateinit var intakeCommand: IntakeCommand
    private lateinit var outtakeCommand: IntakeCommand

    private lateinit var driver: GamepadEx

    override fun initialize() {
        intakeMotor = Motor(hardwareMap, "intake")

        intakeSubsystem = IntakeSubsystem(intakeMotor)

        intakeCommand = IntakeCommand(intakeSubsystem, true)
        outtakeCommand = IntakeCommand(intakeSubsystem, false)

        driver = GamepadEx(gamepad1)

        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(intakeCommand)
        driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(outtakeCommand)
    }
}