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
    private static final double upPosition = 2;
    private static final double downPosition = 135;
    private static final double maxOutput = 0.3;
    private static final double minOutput = -0.3;
    private static final double kP = 0.2;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    private static final double allowedError = 15;

    public Pivot() {
        pivotMotor = new CANSparkMax(33, MotorType.kBrushless);
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
    public void resetEncoder(){
        pivotEncoder.reset();
    }

    public boolean isUp() {
        double angle_value = getEncoderAngle();

        if (angle_value >= upPosition - allowedError && angle_value <= upPosition + allowedError) {
            IsUp = true;
        } else if (angle_value >= downPosition - allowedError && angle_value <= downPosition + allowedError){
            IsUp = false;
        } else {
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
        
        IsUp = isUp();
    }

    public void pivotDown() {
        double currentAngle = getEncoderAngle();

        double error = downPosition - currentAngle;

        double output = kP * error;

        output = Math.max(minOutput, Math.min(maxOutput, output));

        pivotMotor.set(output);
        SmartDashboard.putBoolean("Pivot/Moving Down", true);
        
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
    }

}
