package org.firstinspires.ftc.teamcode.subsystems.elevator

import com.arcrobotics.ftclib.command.SubsystemBase
import com.arcrobotics.ftclib.hardware.motors.Motor
import com.arcrobotics.ftclib.hardware.motors.MotorGroup
import com.qualcomm.robotcore.hardware.TouchSensor

class ElevatorSubsystem(
    leftMotor: Motor,
    rightMotor: Motor,
    private val limit: TouchSensor
) : SubsystemBase() {
    private val motors: MotorGroup = MotorGroup(leftMotor, rightMotor)

    init {
//        leftMotor.inverted = true
        rightMotor.inverted = true
    }

    fun spinUp() = motors.set(0.7)

    fun spinDown() = if (!isPressed()) motors.set(-0.7) else motors.stopMotor()

    fun stop() = motors.stopMotor()

    fun isPressed() = limit.isPressed
}