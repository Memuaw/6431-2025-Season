package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkPIDController;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class Pivot extends SubsystemBase {

    // Motor and encoder
    private final CANSparkMax pivotMotor;
    private final SparkPIDController pivotPID;
    private final RelativeEncoder pivotEncoder; // Use a RelativeEncoder now

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
    private static final double startPosition = Constants.PivotStartPosition;  // Degrees
    private static final double allowedError = Constants.PivotAllowedError;    // Degrees

    private static final double maxOutput1 = Constants.PivotMaxOutput1;
    private static final double minOutput1 = Constants.PivotMinOutput1;

    private boolean isUp = false;  // Tracks the target position (up or down)

    public Pivot() {   
        // Motor and PID setup
        pivotMotor = new CANSparkMax(33, MotorType.kBrushless);
        pivotPID = pivotMotor.getPIDController();

        // Encoder setup: Use the built-in RelativeEncoder from the NEO
        pivotEncoder = pivotMotor.getEncoder();
        pivotEncoder.setPositionConversionFactor(360.0); // 1 rotation = 360 degrees

        // Feedforward setup
        armFeedforward = new ArmFeedforward(kS, kG, kV);

        // PID controller setup
        pivotPID.setP(kP);
        pivotPID.setI(kI);
        pivotPID.setD(kD);
        pivotPID.setOutputRange(minOutput1, maxOutput1);

        // Reset encoder at subsystem initialization
        resetEncoder();
        updateIsUp();
    }

    /**
     * Resets the encoder position to 0.
     */
    public void resetEncoder() {
        pivotEncoder.setPosition(0.0);  // Reset the RelativeEncoder to 0
        SmartDashboard.putString("Pivot", "Encoder Reset");
    }

    /**
     * Gets the current pivot angle from the encoder.
     *
     * @return The current angle in degrees.
     */
    public double getEncoderAngle() {
        double angle = pivotEncoder.getPosition(); // Get the angle in degrees
        return angle;
    }

    /**
     * Determines if the pivot is in the "up" position.
     *
     * @return True if the pivot is at the "up" position, false otherwise.
     */
    public boolean isUp() {
        double currentAngle = getEncoderAngle();

        // Check if the current angle is within the tolerance of the up position
        boolean up = Math.abs(currentAngle - upPosition) <= allowedError;
        SmartDashboard.putBoolean("Pivot/Is Up", up);
        return up;
    }

    /**
     * Moves the pivot to the "up" position, using PID and feedforward.
     */
    public void pivotUp() {
        double currentAngleRadians = Math.toRadians(getEncoderAngle());
        double feedforward = armFeedforward.calculate(currentAngleRadians, 0);  // Feedforward with 0 velocity

        pivotPID.setReference(upPosition, ControlType.kPosition, 0, feedforward);
        isUp = isUp();  // Update isUp status using the isUp() method

        SmartDashboard.putString("Pivot", "Moving Up");
    }

    public void pivotToStart() {
        double currentAngleRadians = Math.toRadians(getEncoderAngle());
        double feedforward = armFeedforward.calculate(currentAngleRadians, 0);  // Feedforward with 0 velocity

        pivotPID.setReference(startPosition, ControlType.kPosition, 0, feedforward);
        isUp = isUp();  // Update isUp status using the isUp() method
        resetEncoder();
    }

    /**
     * Moves the pivot to the "down" position, using PID and feedforward.
     */
    public void pivotDown() {
        double currentAngleRadians = Math.toRadians(getEncoderAngle());
        double feedforward = armFeedforward.calculate(currentAngleRadians, 0);  // Feedforward with 0 velocity

        pivotPID.setReference(downPosition, ControlType.kPosition, 0, feedforward);
        isUp = isUp();  // Update isUp status using the isUp() method

        SmartDashboard.putString("Pivot", "Moving Down");
    }

    /**
     * Checks if the pivot is at the target position.
     *
     * @return True if the pivot is within the allowed error of the target position.
     */
    public boolean atTargetPosition() {
        double currentAngle = getEncoderAngle();
        double targetPosition = isUp ? upPosition : downPosition;

        boolean atTarget = Math.abs(currentAngle - targetPosition) <= allowedError;
        SmartDashboard.putBoolean("Pivot/At Target", atTarget);
        return atTarget;
    }

    /**
     * Updates the internal isUp flag based on the current position.
     */
    private void updateIsUp() {
        isUp = isUp();  // Update isUp status
    }

    @Override
    public void periodic() {
        // Update SmartDashboard with pivot status
        SmartDashboard.putBoolean("Pivot/Is Up", isUp);
        SmartDashboard.putNumber("Pivot/Encoder", this.getEncoderAngle());
        SmartDashboard.putBoolean("Pivot/At Target", atTargetPosition());
        SmartDashboard.putNumber("Pivot/Angle", this.getEncoderAngle());
    }
}
