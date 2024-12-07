package frc.robot;

import frc.robot.Subsystems.Drivetrain;
import frc.robot.Subsystems.Intake;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.Pivot;
import frc.robot.Commands.DrivetrainCommand;
import frc.robot.Commands.ToggleIntakeCommand;
import frc.robot.Commands.ShootCommand;
import frc.robot.Commands.TogglePivotCommand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
    private final Drivetrain drivetrain = new Drivetrain();
    private final Intake intake = new Intake();
    private final Shooter shooter = new Shooter();
    private final Pivot pivot = new Pivot();
    private final XboxController controller = new XboxController(0);


    public RobotContainer() {
        configureButtonBindings();
        drivetrain.setDefaultCommand(new DrivetrainCommand(drivetrain, controller));
    }

    private void configureButtonBindings() {
        // Bind the ToggleIntakeCommand to the A button on the Xbox controller
        new JoystickButton(controller, XboxController.Button.kA.value).whileTrue(new ToggleIntakeCommand(intake));
        new JoystickButton(controller, XboxController.Button.kB.value).whileTrue(new ShootCommand(shooter));
        new JoystickButton(controller, XboxController.Button.kY.value).whileTrue(new TogglePivotCommand(pivot));
    }

    public Command getAutonomousCommand() {
        // Return an autonomous command here when defined
        return null;
    }
}
