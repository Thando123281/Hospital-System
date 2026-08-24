import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static HospitalSystem hospital = new HospitalSystem();

    public static void main(String[] args) {

        int choice;

        do {

            System.out.println("\n================================");
            System.out.println("     MEDICARE HOSPITAL SYSTEM");
            System.out.println("================================");
            System.out.println("1. Register Patient");
            System.out.println("2. Search Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Display All Patients");
            System.out.println("6. Allocate Bed");
            System.out.println("7. Release Bed");
            System.out.println("8. Display Ward");
            System.out.println("9. Display Available Beds");
            System.out.println("10. Display Occupied Beds");
            System.out.println("11. Generate Report");
            System.out.println("12. Sort by Surname");
            System.out.println("13. Sort by Patient ID");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    registerPatient();
                    break;

                case 2:
                    searchPatient();
                    break;

                case 3:
                    updatePatient();
                    break;

                case 4:
                    deletePatient();
                    break;

                case 5:
                    hospital.displayAllPatients();
                    break;

                case 6:
                    allocateBed();
                    break;

                case 7:
                    releaseBed();
                    break;

                case 8:
                    hospital.displayWard();
                    break;

                case 9:
                    hospital.displayAvailableBeds();
                    break;

                case 10:
                    hospital.displayOccupiedBeds();
                    break;

                case 11:
                    hospital.generateReport();
                    break;

                case 12:
                    hospital.sortBySurname();
                    break;

                case 13:
                    hospital.sortByPatientId();
                    break;

                case 0:
                    System.out.println("Thank you for using the system.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    public static void registerPatient() {

        System.out.println("\n--- REGISTER PATIENT ---");

        System.out.print("Patient ID: ");
        String id = scanner.nextLine();

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Medical Condition: ");
        String condition = scanner.nextLine();

        System.out.println("1. Inpatient");
        System.out.println("2. Outpatient");
        System.out.println("3. Emergency");
        System.out.print("Choose category: ");

        int category = scanner.nextInt();
        scanner.nextLine();

        Patient patient;

        if (category == 1) {

            System.out.print("Ward Number: ");
            String ward = scanner.nextLine();

            patient = new Inpatient(
                    id, firstName, lastName, age,
                    gender, condition, ward
            );

        } else if (category == 2) {

            patient = new Patient(
                    id, firstName, lastName, age,
                    gender, condition,
                    PatientCategory.OUTPATIENT
            );

        } else if (category == 3) {

            patient = new Patient(
                    id, firstName, lastName, age,
                    gender, condition,
                    PatientCategory.EMERGENCY
            );

        } else {

            System.out.println("Invalid category.");
            return;
        }

        if (hospital.registerPatient(patient)) {
            System.out.println("Patient registered successfully.");
        } else {
            System.out.println("Patient ID already exists.");
        }
    }

    public static void searchPatient() {

        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine();

        Patient patient = hospital.searchPatient(id);

        if (patient != null) {
            System.out.println("\nPatient found:");
            patient.displayDetails();
        } else {
            System.out.println("Patient not found.");
        }
    }

    public static void updatePatient() {

        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine();

        Patient patient = hospital.searchPatient(id);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("New First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("New Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("New Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("New Gender: ");
        String gender = scanner.nextLine();

        System.out.print("New Medical Condition: ");
        String condition = scanner.nextLine();

        hospital.updatePatient(
                id, firstName, lastName,
                age, gender, condition
        );

        System.out.println("Patient updated successfully.");
    }

    public static void deletePatient() {

        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine();

        if (hospital.deletePatient(id)) {
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("Patient not found.");
        }
    }

    public static void allocateBed() {

        System.out.print("Enter Inpatient ID: ");
        String id = scanner.nextLine();

        Patient patient = hospital.searchPatient(id);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        if (!(patient instanceof Inpatient)) {
            System.out.println("Only inpatients can be allocated a bed.");
            return;
        }

        System.out.print("Enter bed number (1-20): ");
        int bed = scanner.nextInt();
        scanner.nextLine();

        if (hospital.allocateBed(id, bed)) {
            System.out.println("Bed allocated successfully.");
        } else {
            System.out.println("Bed allocation failed.");
            System.out.println("Check that the bed is available and the patient has no other bed.");
        }
    }

    public static void releaseBed() {

        System.out.print("Enter bed number (1-20): ");
        int bed = scanner.nextInt();
        scanner.nextLine();

        if (hospital.releaseBed(bed)) {
            System.out.println("Bed released successfully.");
        } else {
            System.out.println("Bed is not occupied or the bed number is invalid.");
        }
    }
}