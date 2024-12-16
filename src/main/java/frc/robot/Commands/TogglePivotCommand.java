package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Pivot;

public class TogglePivotCommand extends Command {

    private final Pivot pivot;

    public TogglePivotCommand(Pivot pivot) {
        this.pivot = pivot;
        addRequirements(pivot);
    }

    @Override
    public void initialize() {
        pivot.togglePivot();
    }

    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}