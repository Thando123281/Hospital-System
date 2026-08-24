import java.util.ArrayList;
import java.util.Comparator;

public class HospitalSystem {

    private ArrayList<Patient> patients;
    private Patient[] beds;

    public HospitalSystem() {
        patients = new ArrayList<>();
        beds = new Patient[20];
    }

    // Register patient
    public boolean registerPatient(Patient patient) {

        if (searchPatient(patient.getPatientId()) != null) {
            return false;
        }

        patients.add(patient);
        return true;
    }

    // Search patient
    public Patient searchPatient(String patientId) {

        for (Patient p : patients) {
            if (p.getPatientId().equalsIgnoreCase(patientId)) {
                return p;
            }
        }

        return null;
    }

    // Update patient
    public boolean updatePatient(String patientId, String firstName,
                                 String lastName, int age,
                                 String gender, String condition) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        patient.updateDetails(firstName, lastName, age,
                              gender, condition);

        return true;
    }

    // Delete patient
    public boolean deletePatient(String patientId) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        // Release bed if patient is an inpatient
        if (patient instanceof Inpatient) {

            Inpatient inpatient = (Inpatient) patient;

            if (inpatient.getBedNumber() != 0) {
                releaseBed(inpatient.getBedNumber());
            }
        }

        patients.remove(patient);
        return true;
    }

    // Display all patients
    public void displayAllPatients() {

        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }

        for (Patient p : patients) {
            System.out.println("-------------------------");
            p.displayDetails();
        }
    }

    // Allocate bed
    public boolean allocateBed(String patientId, int bedNumber) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        if (!(patient instanceof Inpatient)) {
            return false;
        }

        if (bedNumber < 1 || bedNumber > 20) {
            return false;
        }

        if (beds[bedNumber - 1] != null) {
            return false;
        }

        Inpatient inpatient = (Inpatient) patient;

        // Patient cannot have two beds
        if (inpatient.getBedNumber() != 0) {
            return false;
        }

        beds[bedNumber - 1] = patient;
        inpatient.setBedNumber(bedNumber);

        return true;
    }

    // Release bed
    public boolean releaseBed(int bedNumber) {

        if (bedNumber < 1 || bedNumber > 20) {
            return false;
        }

        Patient patient = beds[bedNumber - 1];

        if (patient == null) {
            return false;
        }

        if (patient instanceof Inpatient) {
            Inpatient inpatient = (Inpatient) patient;
            inpatient.setBedNumber(0);
        }

        beds[bedNumber - 1] = null;

        return true;
    }

    // Display complete ward
    public void displayWard() {

        System.out.println("\n--- WARD LAYOUT ---");

        for (int i = 0; i < 20; i++) {

            if (beds[i] == null) {
                System.out.print("B" + String.format("%02d", i + 1)
                        + "[Available] ");
            } else {
                System.out.print("B" + String.format("%02d", i + 1)
                        + "[" + beds[i].getPatientId() + "] ");
            }

            if ((i + 1) % 5 == 0) {
                System.out.println();
            }
        }
    }

    // Display available beds
    public void displayAvailableBeds() {

        System.out.println("\nAvailable Beds:");

        boolean found = false;

        for (int i = 0; i < 20; i++) {

            if (beds[i] == null) {
                System.out.print("B" + String.format("%02d", i + 1) + " ");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No beds available.");
        }

        System.out.println();
    }

    // Display occupied beds
    public void displayOccupiedBeds() {

        System.out.println("\nOccupied Beds:");

        boolean found = false;

        for (int i = 0; i < 20; i++) {

            if (beds[i] != null) {

                System.out.println(
                    "B" + String.format("%02d", i + 1)
                    + " - Patient: "
                    + beds[i].getPatientId()
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No occupied beds.");
        }
    }

    // Number of patients
    public int getPatientCount() {
        return patients.size();
    }

    // Number of occupied beds
    public int getOccupiedBedCount() {

        int count = 0;

        for (Patient p : beds) {
            if (p != null) {
                count++;
            }
        }

        return count;
    }

    // Occupancy percentage
    public double getOccupancyPercentage() {

        return (getOccupiedBedCount() / 20.0) * 100;
    }

    // Sort by surname
    public void sortBySurname() {

        patients.sort(Comparator.comparing(
                Patient::getLastName,
                String.CASE_INSENSITIVE_ORDER));

        System.out.println("Patients sorted by surname.");
    }

    // Sort by Patient ID
    public void sortByPatientId() {

        patients.sort(Comparator.comparing(
                Patient::getPatientId,
                String.CASE_INSENSITIVE_ORDER));

        System.out.println("Patients sorted by Patient ID.");
    }

    // Reports
    public void generateReport() {

        System.out.println("\n========== WARD REPORT ==========");

        System.out.println("Total Registered Patients: "
                + getPatientCount());

        System.out.println("Total Occupied Beds: "
                + getOccupiedBedCount());

        System.out.println("Available Beds: "
                + (20 - getOccupiedBedCount()));

        System.out.printf("Ward Occupancy: %.2f%%\n",
                getOccupancyPercentage());

        System.out.println("\n--- REGISTERED PATIENTS ---");
        displayAllPatients();

        System.out.println("\n--- AVAILABLE BEDS ---");
        displayAvailableBeds();

        System.out.println("\n--- OCCUPIED BEDS ---");
        displayOccupiedBeds();

        System.out.println("=================================");
    }
}