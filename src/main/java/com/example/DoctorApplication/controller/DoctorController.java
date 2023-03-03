package com.example.DoctorApplication.controller;

import com.example.DoctorApplication.model.Doctor;
import com.example.DoctorApplication.service.DoctorService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @PostMapping(value = "create-doctor")
    public ResponseEntity<String> saveDoctor(@RequestBody Doctor doctor) {
        JSONObject jsonObject = new JSONObject(doctor);
        List<String> validateDoctor = validateDoctor(jsonObject);
        if (validateDoctor.isEmpty()) {
            Doctor doctor1 = setDoctor(jsonObject);
            doctorService.saveDoctor(doctor1);
            return new ResponseEntity<>("Doctor Saved", HttpStatus.CREATED);
        } else {
            Object[] answer = Arrays.copyOf(validateDoctor.toArray(), validateDoctor.size(), String[].class);
            return new ResponseEntity<>("Please pass mandatory parameters" + Arrays.toString(answer), HttpStatus.BAD_REQUEST);
        }
    }

    private Doctor setDoctor(JSONObject jsonObject) {
        Doctor doctor = new Doctor();
        String doctorName = jsonObject.getString("doctorName");
        doctor.setDoctorName(doctorName);

        String specializedIn = jsonObject.getString("specializedIn");
        doctor.setSpecializedIn(specializedIn);

        Integer experience = jsonObject.getInt("experience");
        doctor.setExperience(experience);

        return doctor;
    }

    private List<String> validateDoctor(JSONObject jsonObject) {
        List<String> errorList = new ArrayList<>();

        if (!jsonObject.has("doctorName")) {
            errorList.add("doctorName");
        }
        if (!jsonObject.has("experience")) {
            errorList.add("experience");
        }
        if (!jsonObject.has("specializedIn")) {
            errorList.add("specializedIn");
        }
        return errorList;
    }

    @GetMapping(value = "get-doctor")
    public List<Doctor> getDoctors(@Nullable @RequestParam String doctorId) {
        return doctorService.getDoctor(doctorId);
    }

    @PutMapping(value = "update-doctor/{id}")
    public void updateDoctor(@PathVariable int id, @RequestBody Doctor doctor) {
        doctorService.updateDoctor(id, doctor);
    }

    @DeleteMapping(value = "delete-doctor/{id}")
    public void deleteDoctor(@PathVariable int id) {
        doctorService.deleteDoctor(id);
    }
}
