package org.firstinspires.ftc.teamcode.subsystems.elevator

import com.acmerobotics.dashboard.config.Config
import com.arcrobotics.ftclib.command.SubsystemBase
import com.arcrobotics.ftclib.hardware.motors.Motor
import com.arcrobotics.ftclib.hardware.motors.MotorGroup
import com.qualcomm.robotcore.hardware.TouchSensor
import org.firstinspires.ftc.teamcode.constants.ElevatorConst

class ElevatorSubsystem(
    leftMotor: Motor,
    rightMotor: Motor,
    private val limit: TouchSensor
) : SubsystemBase() {
    private val motors: MotorGroup = MotorGroup(leftMotor, rightMotor)

    init {
//        leftMotor.inverted = true
        rightMotor.inverted = true
        motors.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE)
    }

    fun spinUp() = motors.set(0.7)

    fun spinDown() = if (!isPressed()) motors.set(-0.7) else motors.stopMotor()

    fun stop() = motors.set(ElevatorConst.PID.F.coeff)

    fun isPressed() = limit.isPressed
}