package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Subsystems.Shooter;

public class ShootCommand extends Command {

    private final Shooter shooter;
    private final Timer timer = new Timer(); // Timer to track elapsed time

    public ShootCommand(Shooter shooter) {
        this.shooter = shooter;

        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        // Start the shooter when the command initializes
        shooter.startShooter();
        System.out.println("ShootCommand started");

        // Start the timer
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void end(boolean interrupted) {
        // Stop the shooter when the command ends
        shooter.stopShooter();
        System.out.println("ShootCommand ended. Interrupted: " + interrupted);

        // Stop the timer
        timer.stop();
    }

    @Override
    public boolean isFinished() {
        // Command finishes after 1 second
        return timer.hasElapsed(1.0);
    }
}
