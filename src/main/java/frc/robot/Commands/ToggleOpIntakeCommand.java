package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Intake;

public class ToggleOpIntakeCommand extends Command {

    private final Intake intake;

    public ToggleOpIntakeCommand(Intake intake) {
        this.intake = intake;

        // Declare subsystem dependencies
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        // Toggle the intake when the command is initialized
        intake.toggleOpposite();
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}