package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final VictorSP intakeMotor; // Motor for the intake system
    private final VictorSP pivotMotor; // Motor for the pivot system
    private boolean isRunning; // Tracks whether the intake is running

    public Intake() {
        intakeMotor = new VictorSP(2);
        pivotMotor = new VictorSP(0);
        isRunning = false; // Initially, the intake is off
    }

    // Method to start the intake
    public void startIntake() {
        intakeMotor.set(0.5); // Adjust speed as needed (positive for intake direction)
        isRunning = true;
    }

    // Method to stop the intake
    public void stopIntake() {
        intakeMotor.set(0); // Stop the motor
        isRunning = false;
    }

    // Method to toggle the intake state
    public void toggleIntake() {
        if (isRunning) {
            stopIntake();
        } else {
            startIntake();
        }
    }

    public void pivotUp() {
        
    }

    public void pivotDown() {
        
    }

    @Override
    public void periodic() {
        
    }
}
