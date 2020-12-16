class Person {
    protected String name;
    protected int yearOfBirth;
    protected String homeAddress;
}

class Employee extends Person {
    protected int startYear;
    protected float salary;
}

class Doctor extends Employee {
    protected String specialty;
}

class Patient extends Person {
    java.util.Date dateOfAdmittance;
}