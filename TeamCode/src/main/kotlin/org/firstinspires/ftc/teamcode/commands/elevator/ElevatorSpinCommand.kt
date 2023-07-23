package org.firstinspires.ftc.teamcode.commands.elevator

import com.arcrobotics.ftclib.command.CommandBase
import org.firstinspires.ftc.teamcode.subsystems.elevator.ElevatorSubsystem

class ElevatorSpinCommand(
    private val subsystem: ElevatorSubsystem,
    private val down: Boolean
) : CommandBase() {
    override fun execute() = if (down && !subsystem.isPressed()) subsystem.spinDown() else subsystem.spinUp()

    override fun isFinished() = subsystem.isPressed()

    override fun end(interrupted: Boolean) = subsystem.stop()
}