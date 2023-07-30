package org.firstinspires.ftc.teamcode.opModes.teleop

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.arcrobotics.ftclib.command.CommandOpMode
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.arcrobotics.ftclib.hardware.motors.Motor
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.TouchSensor
import org.firstinspires.ftc.teamcode.commands.elevator.ElevatorSpinCommand
import org.firstinspires.ftc.teamcode.commands.intake.IntakeCommand
import org.firstinspires.ftc.teamcode.subsystems.elevator.ElevatorSubsystem
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeSubsystem

@TeleOp
class MainTeleOp : CommandOpMode() {
    private lateinit var intakeMotor: Motor
    private lateinit var elevatorLeftMotor: Motor
    private lateinit var elevatorRightMotor: Motor
    private lateinit var limit: TouchSensor

    private lateinit var intakeSubsystem: IntakeSubsystem
    private lateinit var elevatorSubsystem: ElevatorSubsystem

    private lateinit var intakeCommand: IntakeCommand
    private lateinit var outtakeCommand: IntakeCommand

    private lateinit var elevatorLowCommand: ElevatorSpinCommand
    private lateinit var elevatorHighCommand: ElevatorSpinCommand
    private lateinit var elevatorGroundCommand: ElevatorSpinCommand


    private lateinit var driver: GamepadEx

    override fun initialize() {
        telemetry = MultipleTelemetry(FtcDashboard.getInstance().telemetry, telemetry)

        intakeMotor = Motor(hardwareMap, "intake")
        elevatorLeftMotor = Motor(hardwareMap, "elevatorLeft")
        elevatorRightMotor = Motor(hardwareMap, "elevatorRight")
        limit = hardwareMap.get(TouchSensor::class.java, "limit")

        intakeSubsystem = IntakeSubsystem(intakeMotor)
        elevatorSubsystem = ElevatorSubsystem(leftMotor = elevatorLeftMotor, rightMotor = elevatorRightMotor, limit, telemetry)

        intakeCommand = IntakeCommand(intakeSubsystem, true)
        outtakeCommand = IntakeCommand(intakeSubsystem, false)

        elevatorGroundCommand = ElevatorSpinCommand(elevatorSubsystem, 0.0)
        elevatorLowCommand = ElevatorSpinCommand(elevatorSubsystem, 500.0)
        elevatorHighCommand = ElevatorSpinCommand(elevatorSubsystem, 2400.0)

        driver = GamepadEx(gamepad1)

        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whileHeld(intakeCommand)
        driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whileHeld(outtakeCommand)

        driver.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(elevatorLowCommand)
        driver.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(elevatorHighCommand.withTimeout(3000))
        driver.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(elevatorGroundCommand)
    }
}