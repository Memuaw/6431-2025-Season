package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Drivetrain;
import edu.wpi.first.wpilibj.PS5Controller;

public class DrivetrainCommand extends Command {
    private final Drivetrain drivetrain;
    private final PS5Controller controller;

    public DrivetrainCommand(Drivetrain drivetrain, PS5Controller controller) {
        this.drivetrain = drivetrain;
        this.controller = controller;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        double leftY = Drivetrain.applyDeadband(controller.getLeftY(), 0.15);
        double rightX = Drivetrain.applyDeadband(controller.getRightX(), 0.15);

        drivetrain.tankDrive(rightX, leftY);
    }

    @Override
    public void end(boolean interrupted) {
        // Stop the drivetrain when the command ends
        drivetrain.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        // This command should never finish during teleop
        return false;
    }

}
