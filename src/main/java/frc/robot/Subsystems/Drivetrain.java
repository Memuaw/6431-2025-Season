package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends SubsystemBase{
    // Declarations below
    private final CANSparkMax leftmotor1;
    private final CANSparkMax rightmotor1;
    private final CANSparkMax leftmotor2;
    private final CANSparkMax rightmotor2;
    private final DifferentialDrive differentialDrive;

    public Drivetrain() {
        leftmotor1 = new CANSparkMax(8, MotorType.kBrushless);
        rightmotor1 = new CANSparkMax(6, MotorType.kBrushless);
        leftmotor2 = new CANSparkMax(9, MotorType.kBrushless);
        rightmotor2 = new CANSparkMax(7, MotorType.kBrushless);

        leftmotor1.follow(leftmotor2);
        rightmotor1.follow(rightmotor2);

        leftmotor1.setInverted(true);
        differentialDrive = new DifferentialDrive(leftmotor1, rightmotor1);
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        differentialDrive.tankDrive(leftSpeed, rightSpeed);

        // Log joystick inputs and motor speeds
        SmartDashboard.putNumber("Drivetrain/Left Speed", leftSpeed);
        SmartDashboard.putNumber("Drivetrain/Right Speed", rightSpeed);
        System.out.println("Drivetrain: LeftSpeed=" + leftSpeed + ", RightSpeed=" + rightSpeed);
    }

    public static double applyDeadband(double value, double deadband) {
        if (Math.abs(value) < deadband) return 0;
        return value;
    }
    

    @Override
    public void periodic() {
        int faults = leftmotor1.getFaults();
        int stickyFaults = leftmotor1.getStickyFaults();

        // Example: Stop the drivetrain if a fault is detected
        if (faults != 0 || stickyFaults != 0) {
            differentialDrive.tankDrive(0, 0);
            System.out.println("FAULT: Drivetrain stopped because fault was detected!!");
        }

        SmartDashboard.putNumber("Drivetrain/Left Voltage", leftmotor1.getBusVoltage());
        SmartDashboard.putNumber("Drivetrain/Right Voltage", rightmotor1.getBusVoltage());
    }
    
}
