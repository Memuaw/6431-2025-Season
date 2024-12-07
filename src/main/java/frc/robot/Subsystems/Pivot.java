package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pivot extends SubsystemBase {
    
    private final CANSparkMax pivotMotor;
    private SparkPIDController pivotPID;
    private DutyCycleEncoder pivotEncoder;
    private Boolean IsUp;
    private static final double upPosition = 244.2;
    private static final double downPosition = 119.6;
    private static final double maxOutput = 1.0;
    private static final double minOutput = -1.0;
    private static final double kP = 0.1;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    private static final double allowedError = 15;

    public Pivot() {
        pivotMotor = new CANSparkMax(3, MotorType.kBrushless);
        pivotPID = pivotMotor.getPIDController();
        pivotEncoder = new DutyCycleEncoder(0);
        pivotEncoder.setDistancePerRotation(360);

        pivotPID.setP(kP);
        pivotPID.setI(kI);
        pivotPID.setD(kD);

        pivotPID.setOutputRange(minOutput, maxOutput);
        IsUp = isUp();
    }

    public double getEncoderAngle() {
        double angle = pivotEncoder.getDistance();
        SmartDashboard.putNumber("Pivot/Angle", angle);
        return angle;
    }

    public boolean isUp() {
        double angle_value = getEncoderAngle();

        if (angle_value >= upPosition - allowedError && angle_value <= upPosition + allowedError) {
            IsUp = true;
        } else if (angle_value >= downPosition - allowedError && angle_value <= downPosition + allowedError){
            IsUp = false;
        } else {
            System.out.println("Pivot motor isn't detected to be up or down so isUp is equal to whichever target position it is closer to!!");
            double diff1 = Math.abs(angle_value - upPosition);
            double diff2 = Math.abs(angle_value - downPosition);

            if (diff1 < diff2) {
                IsUp = true;
            } else {
                IsUp = false;
            }
            
        }
        SmartDashboard.putBoolean("Pivot/isUp", IsUp);

        return IsUp;
    }

    public void pivotUp() {
        double currentAngle = getEncoderAngle();

        double error = upPosition - currentAngle;

        double output = kP * error;

        output = Math.max(minOutput, Math.min(maxOutput, output));

        pivotMotor.set(output);
        SmartDashboard.putBoolean("Pivot/Moving Up", true);
        System.out.println("Pivot moving up");
        
        IsUp = isUp();
    }

    public void pivotDown() {
        double currentAngle = getEncoderAngle();

        double error = downPosition - currentAngle;

        double output = kP * error;

        output = Math.max(minOutput, Math.min(maxOutput, output));

        pivotMotor.set(output);
        SmartDashboard.putBoolean("Pivot/Moving Down", true);
        System.out.println("Pivot moving down");
        
        IsUp = isUp();
    }

    public void togglePivot() {
        if (IsUp) {
            pivotDown();
        } else {
            pivotUp();
        }
    }

    @Override
    public void periodic() {
        double angle = getEncoderAngle();
        SmartDashboard.putNumber("Pivot/Angle", angle);
        if (angle < downPosition || angle > upPosition) {
            pivotMotor.set(0); // Stop motor if out of bounds
            System.out.println("PIVOT STOPPED: Pivot motor was stopped because the angle was detected to be out of bounds!!");
        }
    }

}
