package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pivot extends SubsystemBase {

    // Motor and encoder
    private final CANSparkMax pivotMotor;
    private final SparkPIDController pivotPID;
    private final DutyCycleEncoder pivotEncoder;

    // Gravity compensation using ArmFeedforward
    private final ArmFeedforward armFeedforward;

    // PID and feedforward constants
    private static final double kP = 0.2;
    private static final double kI = 0.0;
    private static final double kD = 0.0;

    private static final double kS = 0.2;  // Static friction compensation (volts)
    private static final double kG = 0.4;  // Gravity compensation (volts)
    private static final double kV = 1.0;  // Velocity feedforward (optional)

    // Target positions
    private static final double upPosition = 2.0;      // Degrees
    private static final double downPosition = 135.0;  // Degrees
    private static final double allowedError = 5.0;    // Degrees

    private static final double maxOutput = 0.3;
    private static final double minOutput = -0.3;

    private boolean isUp = false;  // Tracks the target position (up or down)

    public Pivot() {
        // Motor and PID setup
        pivotMotor = new CANSparkMax(33, MotorType.kBrushless);
        pivotPID = pivotMotor.getPIDController();

        // Encoder setup
        pivotEncoder = new DutyCycleEncoder(0);
        pivotEncoder.setDistancePerRotation(360.0);  // 1 full rotation = 360 degrees

        // Feedforward setup
        armFeedforward = new ArmFeedforward(kS, kG, kV);

        // PID controller setup
        pivotPID.setP(kP);
        pivotPID.setI(kI);
        pivotPID.setD(kD);
        pivotPID.setOutputRange(minOutput, maxOutput);

        // Reset encoder at subsystem initialization
        resetEncoder();
        updateIsUp();
    }

    public void resetEncoder() {
        pivotEncoder.reset();
        SmartDashboard.putString("Pivot", "Encoder Reset");
    }

    public double getEncoderAngle() {
        double angle = pivotEncoder.getDistance();
        SmartDashboard.putNumber("Pivot/Angle", angle);
        return angle;
    }

    public boolean isUp() {
        double currentAngle = getEncoderAngle();

        // Check if the current angle is within the tolerance of the up position
        boolean up = Math.abs(currentAngle - upPosition) <= allowedError;
        SmartDashboard.putBoolean("Pivot/Is Up", up);
        return up;
    }

    public void pivotUp() {
        double currentAngleRadians = Math.toRadians(getEncoderAngle());
        double feedforward = armFeedforward.calculate(currentAngleRadians, 0);  // Feedforward with 0 velocity

        pivotPID.setReference(upPosition, ControlType.kPosition, 0, feedforward);
        isUp = isUp();  // Update isUp status using the isUp() method

        SmartDashboard.putString("Pivot", "Moving Up");
    }

    public void pivotDown() {
        double currentAngleRadians = Math.toRadians(getEncoderAngle());
        double feedforward = armFeedforward.calculate(currentAngleRadians, 0);  // Feedforward with 0 velocity

        pivotPID.setReference(downPosition, ControlType.kPosition, 0, feedforward);
        isUp = isUp();  // Update isUp status using the isUp() method

        SmartDashboard.putString("Pivot", "Moving Down");
    }

    public void togglePivot() {
        if (isUp) {
            pivotDown();
        } else {
            pivotUp();
        }
    }

    public boolean atTargetPosition() {
        double currentAngle = getEncoderAngle();
        double targetPosition = isUp ? upPosition : downPosition;

        boolean atTarget = Math.abs(currentAngle - targetPosition) <= allowedError;
        SmartDashboard.putBoolean("Pivot/At Target", atTarget);
        return atTarget;
    }

    private void updateIsUp() {
        isUp = isUp();  // Update isUp status
    }

    @Override
    public void periodic() {
        // Update SmartDashboard with pivot status
        getEncoderAngle();
        SmartDashboard.putBoolean("Pivot/Is Up", isUp);
        SmartDashboard.putBoolean("Pivot/At Target", atTargetPosition());
    }
}
