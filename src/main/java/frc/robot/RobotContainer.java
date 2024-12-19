package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
  

    public RobotContainer() {
        configureButtonBindings();
        Constants.drivetrain.setDefaultCommand(Constants.DrivetrainCommand);
    }

    private void configureButtonBindings() {
       Constants.Circle.onTrue(Constants.ShootCommand);
       Constants.Cross.onTrue(Constants.ToggleIntakeCommand);
       Constants.Triangle.onTrue(Constants.ToggleOpIntakeCommand);
       Constants.L2.onTrue(Constants.PivotUp);
       Constants.R2.onTrue(Constants.PivotDown);
    }

    public Command getAutonomousCommand() {
        // Return an autonomous command here when defined
        return null;
    }
}
