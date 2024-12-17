package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

    private final CANSparkMax intakeMotor; // Motor for the intake system
    private boolean isRunning; // Tracks whether the intake is running
    private boolean isOpposite;
    private long startTime;

    public Intake() {
        intakeMotor = new CANSparkMax(5, MotorType.kBrushless);
        isRunning = false; // Initially, the intake is off
        isOpposite = false;
        startTime = 0;
    }

    // Method to start the intake
    public void startIntake() {
        if (startTime == 0) startTime = System.currentTimeMillis();
        intakeMotor.set(Constants.IntakeSpeed); // Adjust speed as needed (positive for intake direction)
        isRunning = true;
        SmartDashboard.putBoolean("Intake/Running", true);
    }

    public void oppositeIntake() {
        if (startTime == 0) startTime = System.currentTimeMillis();
        intakeMotor.set(-Constants.IntakeSpeed);
        isOpposite = true;
    }

    // Method to stop the intake
    public void stopIntake() {
        startTime = 0;
        intakeMotor.set(0); // Stop the motor
        isRunning = false;
        SmartDashboard.putBoolean("Intake/Running", false);
    }

    public void stopOppositeIntake() {
        startTime = 0;
        intakeMotor.set(0);
        isOpposite = false;
    }

    // Method to toggle the intake state
    public void toggleIntake() {
        if (isOpposite) { // If the intake is running in the opposite direction, stop it
            stopOppositeIntake();
        }
        if (isRunning) {
            stopIntake();
        } else {
            startIntake();
        }
    }
    
    public void toggleOpposite() {
        if (isRunning) { // If the intake is running forward, stop it
            stopIntake();
        }
        if (isOpposite) {
            stopOppositeIntake();
        } else {
            oppositeIntake();
        }
    }
    

    @Override
public void periodic() {
    SmartDashboard.putBoolean("Intake/Running", isRunning);
    SmartDashboard.putBoolean("Intake/Opposite", isOpposite);
    SmartDashboard.putNumber("Intake/MotorSpeed", intakeMotor.get());

    // Safety timeout for forward intake
    if (isRunning && (System.currentTimeMillis() - startTime) > 10000) { 
        stopIntake();
    }
}

}
