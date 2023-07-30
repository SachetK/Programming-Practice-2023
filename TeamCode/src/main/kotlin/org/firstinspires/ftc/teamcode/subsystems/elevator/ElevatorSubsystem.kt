package org.firstinspires.ftc.teamcode.subsystems.elevator

import com.acmerobotics.dashboard.config.Config
import com.arcrobotics.ftclib.command.SubsystemBase
import com.arcrobotics.ftclib.controller.PIDController
import com.arcrobotics.ftclib.hardware.motors.Motor
import com.arcrobotics.ftclib.hardware.motors.MotorGroup
import com.qualcomm.robotcore.hardware.TouchSensor
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.constants.ElevatorConst

@Config
class ElevatorSubsystem(
    leftMotor: Motor,
    rightMotor: Motor,
    private val limit: TouchSensor,
    private val telemetry: Telemetry
) : SubsystemBase() {
    private val motors: MotorGroup = MotorGroup(leftMotor, rightMotor)

    private val controller = PIDController(0.0,0.0,0.0)
    private val currentPos
        get() = motors.positions[0]

    var targetPos = 0.0
        set(targetPos) {
            controller.setPoint = targetPos
            field = targetPos
        }

    companion object {
        @JvmField
        var p = 0.0

        @JvmField
        var i = 0.0

        @JvmField
        var d = 0.0
    }

    init {
//        leftMotor.inverted = true
        rightMotor.inverted = true
        motors.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE)
        targetPos = 0.0
        motors.resetEncoder()
    }

    override fun periodic() {
        telemetry.addData("Target Position", targetPos)
        telemetry.addData("Current Position", currentPos)
        telemetry.update()
    }
    fun moveToPosition() {
        // For Tuning
        controller.setPID(p, i, d)

        val power = controller.calculate(currentPos) + ElevatorConst.PID.G.coeff
        if (!(isPressed() && (power >= 0)))
            motors.set(power)
    }

    fun atTargetPosition() = controller.atSetPoint()

    fun spinUp() = motors.set(0.7)

    fun spinDown() = if (!isPressed()) motors.set(-0.7) else motors.stopMotor()

    fun stall() = motors.set(ElevatorConst.PID.G.coeff)

    fun stop() = motors.stopMotor()

    fun isPressed() = limit.isPressed
}