package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pivot extends SubsystemBase {
    
    private final CANSparkMax pivotMotor;
    private SparkPIDController pivotPID;
    private DutyCycleEncoder pivotEncoder;
    private Boolean IsUp;
    private static final double upPosition = 0.0;
    private static final double downPosition = 0.0;
    private static final double maxOutput = 1.0;
    private static final double minOutput = -1.0;
    private static final double kP = 0.1;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    private static final double allowedError = 0.0;

    public Pivot() {
        pivotMotor = new CANSparkMax(3, MotorType.kBrushless);
        IsUp = true;
        pivotPID = pivotMotor.getPIDController();
        pivotEncoder = new DutyCycleEncoder(0);

        pivotPID.setP(kP);
        pivotPID.setI(kI);
        pivotPID.setD(kD);

        pivotPID.setOutputRange(minOutput, maxOutput);
    }

    public void pivotUp() {
        pivotPID.setReference(upPosition, com.revrobotics.ControlType.kPosition);
        IsUp = true; 
    }

    public void pivotDown() {
        pivotPID.setReference(downPosition, com.revrobotics.ControlType.kPosition);
        IsUp = false;
    }

    public void togglePivot() {
        if (IsUp) {
            pivotDown();
        } else {
            pivotUp();
        }
    }


}
