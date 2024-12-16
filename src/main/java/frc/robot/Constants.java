package frc.robot;
import frc.robot.Commands.ShootCommand;
import frc.robot.Commands.ToggleIntakeCommand;
import frc.robot.Commands.TogglePivotCommand;
import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Commands.*;
import frc.robot.Subsystems.Drivetrain;
import frc.robot.Subsystems.Intake;
import frc.robot.Subsystems.Pivot;
import frc.robot.Subsystems.Shooter;

public class Constants {
    public final static  PS5Controller Controller = new PS5Controller(0);
    public final static  Drivetrain drivetrain = new Drivetrain();
    public final static  Intake intake = new Intake();
    public final static  Shooter shooter = new Shooter();
    public final static  Pivot pivot = new Pivot();
    public final static  DrivetrainCommand DrivetrainCommand = new DrivetrainCommand(drivetrain, Controller);
    public final static  ToggleIntakeCommand ToggleIntakeCommand = new ToggleIntakeCommand(intake);
    public final static  TogglePivotCommand TogglePivotCommand = new TogglePivotCommand(pivot);
    public final static  ShootCommand ShootCommand = new ShootCommand(shooter);
    public final static  JoystickButton R2 = new JoystickButton(Controller, PS5Controller.Button.kR2.value);
    public final static  JoystickButton L2 = new JoystickButton(Controller, PS5Controller.Button.kL2.value);
    public final static  JoystickButton Circle = new JoystickButton(Controller, PS5Controller.Button.kCircle.value);
    public final static  JoystickButton Cross = new JoystickButton(Controller, PS5Controller.Button.kSquare.value);
}
