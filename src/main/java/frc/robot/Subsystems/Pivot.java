package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class Pivot extends SubsystemBase {

    // Motor and encoder
    private final CANSparkMax pivotMotor;
    private final SparkPIDController pivotPID;
    private final DutyCycleEncoder pivotEncoder;

    // Gravity compensation using ArmFeedforward
    private final ArmFeedforward armFeedforward;

    // PID and feedforward constants
    private static final double kP = Constants.PivotkP;
    private static final double kI = Constants.PivotkI;
    private static final double kD = Constants.PivotkD;

    private static final double kS = Constants.PivotkS;  // Static friction compensation (volts)
    private static final double kG = Constants.PivotkG;  // Gravity compensation (volts)
    private static final double kV = Constants.PivotkV;  // Velocity feedforward (optional)

    // Target positions
    private static final double upPosition = Constants.PivotUpPosition;      // Degrees
    private static final double downPosition = Constants.PivotDownPosition;  // Degrees
    private static final double allowedError = Constants.PivotAllowedError;    // Degrees

    private static final double maxOutput = Constants.PivotMaxOutput;
    private static final double minOutput = Constants.PivotMinOutput;

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
