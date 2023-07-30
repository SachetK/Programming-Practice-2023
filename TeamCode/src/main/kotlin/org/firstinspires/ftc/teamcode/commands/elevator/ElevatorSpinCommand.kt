package org.firstinspires.ftc.teamcode.commands.elevator

import com.arcrobotics.ftclib.command.CommandBase
import org.firstinspires.ftc.teamcode.subsystems.elevator.ElevatorSubsystem

class ElevatorSpinCommand(
    private val subsystem: ElevatorSubsystem,
    private val target: Double
) : CommandBase() {
    override fun initialize() {
        subsystem.targetPos = target
    }
    override fun execute() = subsystem.moveToPosition()

    override fun isFinished() = if (target == 0.0) subsystem.isPressed() || subsystem.atTargetPosition() else subsystem.atTargetPosition()

    override fun end(interrupted: Boolean) = subsystem.stall()
}