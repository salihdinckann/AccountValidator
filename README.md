# Account Validator 

## 1. Project Overview

This project was developed within the scope of the Software Verification and Validation course. The objective is to test and validate a “Create New Account” form by applying systematic testing techniques and automating the process.

The system includes the following fields:

* First Name
* Last Name
* E-mail
* Date of Birth
* Password
* Confirm Password

All inputs are validated according to predefined rules, and the system is tested using unit tests.

---

## 2. Validation Rules

* First Name / Last Name: Required, only letters and hyphens, maximum 50 characters
* E-mail: Required, valid format, maximum 100 characters
* Date of Birth: Required, valid date, not in the future, user must be at least 18 years old
* Password: Required, 8–20 characters, must include uppercase, lowercase, digit, and special character
* Confirm Password: Must match the password

---

## 3. Testing Approach

Two black-box testing techniques were applied:

* Equivalence Partitioning: Inputs were divided into valid and invalid classes, and representative values were tested
* Boundary Value Analysis: Edge values such as minimum and maximum limits were tested

A total of 16 test cases were implemented covering all fields and scenarios.

---

## 4. Technologies

* Java 17
* Maven
* JUnit 5
* Git and GitHub
* GitHub Actions

---

## 5. Project Structure

```
AccountValidator/
├── .github/workflows/test.yml
├── src/main/java/com/hku/
├── src/test/java/com/hku/
├── pom.xml
```

---

## 6. Running Tests

Tests can be executed using Maven:

```
mvn test
```

All test cases pass successfully with no failures.

---

## 7. Continuous Integration

GitHub Actions is used to automate test execution. The workflow runs on every push and pull request, ensuring continuous validation of the system.

---

## 8. Collaboration

The project was developed collaboratively using Git. Contributions were managed through branches and pull requests, with code reviews and merge approvals.

---

## 9. Conclusion

This project demonstrates the application of software testing principles, including validation design, unit testing, and continuous integration. The system is fully tested and automatically verified through a CI pipeline.

---

