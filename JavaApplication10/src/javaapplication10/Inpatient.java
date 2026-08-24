public class Inpatient extends Patient {

    private String wardNumber;
    private int bedNumber;

    public Inpatient(String patientId, String firstName, String lastName,
                     int age, String gender, String medicalCondition,
                     String wardNumber) {

        super(patientId, firstName, lastName, age, gender,
              medicalCondition, PatientCategory.INPATIENT);

        this.wardNumber = wardNumber;
        this.bedNumber = 0;
    }

    public String getWardNumber() {
        return wardNumber;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Ward Number: " + wardNumber);

        if (bedNumber == 0) {
            System.out.println("Bed Number: Not allocated");
        } else {
            System.out.println("Bed Number: B" +
                    String.format("%02d", bedNumber));
        }
    }
}