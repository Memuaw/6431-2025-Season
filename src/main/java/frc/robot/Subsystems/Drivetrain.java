package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

public class Drivetrain extends SubsystemBase{
    // Declarations below
    private final VictorSP leftmotor1;
    private final VictorSP rightmotor1;
    private final VictorSP leftmotor2;
    private final VictorSP rightmotor2;
    private final DifferentialDrive differentialDrive;

    public Drivetrain() {
        leftmotor1 = new VictorSP(8);
        rightmotor1 = new VictorSP(6);
        leftmotor2 = new VictorSP(9);
        rightmotor2 = new VictorSP(7);

        leftmotor1.addFollower(leftmotor2);
        rightmotor1.addFollower(rightmotor2);

        leftmotor1.setInverted(true);
        differentialDrive = new DifferentialDrive(leftmotor1, rightmotor1);
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        differentialDrive.tankDrive(leftSpeed, rightSpeed);
    }

    @Override
    public void periodic() {
        
    }
    
}
