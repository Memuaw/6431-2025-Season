package frc.robot;
import frc.robot.Commands.ShootCommand;
import frc.robot.Commands.ToggleIntakeCommand;
import frc.robot.Commands.ToggleOpIntakeCommand;
import frc.robot.Commands.PivotUp;
import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Commands.*;
import frc.robot.Subsystems.Drivetrain;
import frc.robot.Subsystems.Intake;
import frc.robot.Subsystems.Pivot;
import frc.robot.Subsystems.Shooter;

public class Constants {
    public final static PS5Controller Controller = new PS5Controller(0);
    public final static Drivetrain drivetrain = new Drivetrain();
    public final static Intake intake = new Intake();
    public final static Shooter shooter = new Shooter();
    public final static Pivot pivot = new Pivot();
    public final static DrivetrainCommand DrivetrainCommand = new DrivetrainCommand(drivetrain, Controller);
    public final static ToggleIntakeCommand ToggleIntakeCommand = new ToggleIntakeCommand(intake);
    public final static ToggleOpIntakeCommand ToggleOpIntakeCommand = new ToggleOpIntakeCommand(intake);
    public final static PivotUp PivotUp = new PivotUp(pivot);
    public final static PivotDown PivotDown = new PivotDown(pivot);
    public final static ShootCommand ShootCommand = new ShootCommand(shooter);
    public final static JoystickButton R2 = new JoystickButton(Controller, PS5Controller.Button.kR2.value);
    public final static JoystickButton L2 = new JoystickButton(Controller, PS5Controller.Button.kL2.value);
    public final static JoystickButton Circle = new JoystickButton(Controller, PS5Controller.Button.kCircle.value);
    public final static JoystickButton Cross = new JoystickButton(Controller, PS5Controller.Button.kCross.value);
    public final static JoystickButton Triangle = new JoystickButton(Controller, PS5Controller.Button.kTriangle.value);

    public final static double IntakeSpeed = 0.5;
    public final static double ShooterSpeed = 0.8;  
    public final static double ShooterMaxRuntime = 5.0;
    public final static double PivotkP = 0.2; 
    public final static double PivotkI = 0.0;
    public final static double PivotkD = 0.0;
    public final static double PivotkS = 0.2;
    public final static double PivotkG = 0.5;   
    public final static double PivotkV = 1.0;
    public final static double PivotUpPosition = -4000.0;
    public final static double PivotDownPosition = 4150.0;
    public final static double PivotStartPosition = 300.0;
    public final static double PivotAllowedError = 0;
    public final static double PivotMinOutput1 = -0.11;
    public final static double PivotMaxOutput1 = 0.11;
    public final static double PivotMinOutput2 = -0.15;
    public final static double PivotMaxOutput2 = 0.15;
    

}
