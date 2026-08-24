public class Patient {

    protected String patientId;
    protected String firstName;
    protected String lastName;
    protected int age;
    protected String gender;
    protected String medicalCondition;
    protected PatientCategory category;

    public Patient(String patientId, String firstName, String lastName,
                   int age, String gender, String medicalCondition,
                   PatientCategory category) {

        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.category = category;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getLastName() {
        return lastName;
    }

    public PatientCategory getCategory() {
        return category;
    }

    public void updateDetails(String firstName, String lastName,
                              int age, String gender,
                              String medicalCondition) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
    }

    public void displayDetails() {
        System.out.println("Patient ID: " + patientId);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Medical Condition: " + medicalCondition);
        System.out.println("Category: " + category);
    }
}