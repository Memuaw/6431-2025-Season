package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Drivetrain;
import edu.wpi.first.wpilibj.XboxController;

public class DrivetrainCommand extends Command {
    private final Drivetrain drivetrain;
    private final XboxController controller;

    public DrivetrainCommand(Drivetrain drivetrain, XboxController controller) {
        this.drivetrain = drivetrain;
        this.controller = controller;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
         // Get joystick values from the controller
        double leftY = -controller.getLeftY();
        double rightY = -controller.getRightY();

        // Pass joystick values to the drivetrain
        drivetrain.tankDrive(leftY, rightY);
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
