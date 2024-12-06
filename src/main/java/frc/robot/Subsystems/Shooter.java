package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Shooter extends SubsystemBase {

    private final CANSparkMax shooterMotor1;
    private final CANSparkMax shooterMotor2;

    public Shooter() {
        shooterMotor1 = new CANSparkMax(4, MotorType.kBrushless);
        shooterMotor2 = new CANSparkMax(5, MotorType.kBrushless);
    }

    // Method to start the shooter
    public void startShooter() {
        shooterMotor1.set(1.0); // Full speed; adjust as necessary
        shooterMotor2.set(1.0); // Full speed; adjust as necessary
    }

    // Method to stop the shooter
    public void stopShooter() {
        shooterMotor1.set(0); // Stop the motor
        shooterMotor2.set(0); // Stop the motor
    }

    @Override
    public void periodic() {
        // Periodic method for sensor updates or debugging (optional)
    }
}
