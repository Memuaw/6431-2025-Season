package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

    private final VictorSP shooterMotor1;
    private final VictorSP shooterMotor2;

    public Shooter() {
        shooterMotor1 = new VictorSP(4);
        shooterMotor2 = new VictorSP(5);
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
