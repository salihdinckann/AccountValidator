package com.hku;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class AccountValidator {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public List<String> validate(CreateAccountRequest req) {
        List<String> errors = new ArrayList<>();

        // First Name
        if (req.getFirstName() == null || req.getFirstName().isBlank()) {
            errors.add("First name is required.");
        } else {
            if (req.getFirstName().length() > 50)
                errors.add("First name max 50 characters.");
            if (!req.getFirstName().matches("^[a-zA-Z\\-]+$"))
                errors.add("First name only letters and hyphens.");
        }

        // Last Name
        if (req.getLastName() == null || req.getLastName().isBlank()) {
            errors.add("Last name is required.");
        } else {
            if (req.getLastName().length() > 50)
                errors.add("Last name max 50 characters.");
            if (!req.getLastName().matches("^[a-zA-Z\\-]+$"))
                errors.add("Last name only letters and hyphens.");
        }

        // Email
        if (req.getEmail() == null || req.getEmail().isBlank()) {
            errors.add("Email is required.");
        } else {
            if (req.getEmail().length() > 100)
                errors.add("Email max 100 characters.");
            else if (!req.getEmail().matches("^[^@]+@[^@]+\\.[^@]+$"))
                errors.add("Invalid email format.");
        }

        // Date of Birth
        if (req.getDateOfBirth() == null || req.getDateOfBirth().isBlank()) {
            errors.add("Date of birth is required.");
        } else {
            try {
                LocalDate dob = LocalDate.parse(req.getDateOfBirth(), DATE_FORMAT);
                if (dob.isAfter(LocalDate.now())) {
                    errors.add("Date cannot be in the future.");
                } else if (dob.isAfter(LocalDate.now().minusYears(18))) {
                    errors.add("Must be at least 18 years old.");
                }
            } catch (DateTimeParseException e) {
                errors.add("Invalid date format. dd/mm/yyyy.");
            }
        }

        // Password
        if (req.getPassword() == null || req.getPassword().isBlank()) {
            errors.add("Password is required.");
        } else {
            if (req.getPassword().length() < 8)
                errors.add("Password min 8 characters.");
            else if (req.getPassword().length() > 20)
                errors.add("Password max 20 characters.");
            else if (!req.getPassword().matches(".*[A-Z].*"))
                errors.add("Password must include uppercase.");
            else if (!req.getPassword().matches(".*[a-z].*"))
                errors.add("Password must include lowercase.");
            else if (!req.getPassword().matches(".*\\d.*"))
                errors.add("Password must include a digit.");
            else if (!req.getPassword().matches(".*[@$!%*?&].*"))
                errors.add("Password must include special char (@$!%*?&).");
        }

        // Confirm Password
        if (req.getConfirmPassword() == null || req.getConfirmPassword().isBlank()) {
            errors.add("Confirm password is required.");
        } else if (!req.getConfirmPassword().equals(req.getPassword())) {
            errors.add("Passwords do not match.");
        }

        return errors;
    }
}