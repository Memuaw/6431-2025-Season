package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends SubsystemBase {

    private final CANSparkMax shooterMotor1;
    private final CANSparkMax shooterMotor2;
    private final Timer timer; // Timer for runtime limit
    private static final double MAX_RUNTIME = 5.0; // 5 seconds

    public Shooter() {
        shooterMotor1 = new CANSparkMax(42, MotorType.kBrushless);
        shooterMotor2 = new CANSparkMax(34, MotorType.kBrushless);
        timer = new Timer(); // Initialize the timer
    }

    // Method to start the shooter
    public void startShooter() {
        shooterMotor1.set(-0.8); // Full speed; adjust as necessary
        shooterMotor2.set(0.8); // Full speed; adjust as necessary
        timer.reset(); // Reset the timer
        timer.start(); // Start the timer
        SmartDashboard.putBoolean("Shooter/Running", true);
    }

    // Method to stop the shooter
    public void stopShooter() {
        shooterMotor1.set(0); // Stop the motor
        shooterMotor2.set(0); // Stop the motor
        timer.stop(); // Stop the timer
        SmartDashboard.putBoolean("Shooter/Running", false);
    }

    @Override
    public void periodic() {
        if (timer.get() > MAX_RUNTIME) {
            stopShooter();
            timer.stop();
        }

        SmartDashboard.putNumber("Shooter/Elapsed Time", timer.get());
    }
}
