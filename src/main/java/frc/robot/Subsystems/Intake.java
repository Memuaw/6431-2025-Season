package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends SubsystemBase {

    private final CANSparkMax intakeMotor; // Motor for the intake system
    private boolean isRunning; // Tracks whether the intake is running
    private long startTime;

    public Intake() {
        intakeMotor = new CANSparkMax(5, MotorType.kBrushless);
        isRunning = false; // Initially, the intake is off
        startTime = 0;
    }

    // Method to start the intake
    public void startIntake() {
        if (startTime == 0) startTime = System.currentTimeMillis();
        intakeMotor.set(0.5); // Adjust speed as needed (positive for intake direction)
        isRunning = true;
        SmartDashboard.putBoolean("Intake/Running", true);
    }

    // Method to stop the intake
    public void stopIntake() {
        startTime = 0;
        intakeMotor.set(0); // Stop the motor
        isRunning = false;
        SmartDashboard.putBoolean("Intake/Running", false);
    }

    // Method to toggle the intake state
    public void toggleIntake() {
        if (isRunning) {
            stopIntake();
        } else {
            startIntake();
        }
    }

    @Override
    public void periodic() {
        if (isRunning && (System.currentTimeMillis() - startTime) > 10000) { // 10 seconds
            stopIntake();
        }
    }
}
