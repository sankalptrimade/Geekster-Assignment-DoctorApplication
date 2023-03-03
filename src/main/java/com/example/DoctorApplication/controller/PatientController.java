package com.example.DoctorApplication.controller;

import com.example.DoctorApplication.model.Doctor;
import com.example.DoctorApplication.model.Patient;
import com.example.DoctorApplication.repository.DoctorRepository;
import com.example.DoctorApplication.service.PatientService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping(value = "/api/v1/patient")
public class PatientController {
    @Autowired
    PatientService patientService;
    @Autowired
    DoctorRepository doctorRepository;

    @PostMapping(value = "create-patient")
    public ResponseEntity<String> savePatient(@RequestBody String patientData) {
        JSONObject jsonObject = new JSONObject(patientData);
        Patient patient = setPatient(jsonObject);
        patientService.savePatient(patient);
        return new ResponseEntity<>("Patient Saved", HttpStatus.CREATED);
    }

    private Patient setPatient(JSONObject jsonObject) {
        Patient patient = new Patient();
        patient.setPatientName(jsonObject.getString("patientName"));
        patient.setAge(jsonObject.getInt("age"));
        patient.setPhoneNumber(jsonObject.getString("phoneNumber"));
        patient.setDiseaseType(jsonObject.getString("diseaseType"));
        patient.setGender(jsonObject.getString("gender"));
        Timestamp currTime = new Timestamp(System.currentTimeMillis());
        patient.setAdmitDate(currTime);
        int doctorId = jsonObject.getInt("doctorId");
        Doctor doctor = doctorRepository.findById(doctorId).get();
        System.out.println(doctor);
        patient.setDoctor(doctor);
        return patient;
    }

    @GetMapping(value = "get-patient")
    public ResponseEntity<String> getPatients(@Nullable @RequestParam String doctorId, @Nullable @RequestParam String patientId) {
        JSONArray jsonArray = patientService.getPatients(doctorId, patientId);
        return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
    }

    @PutMapping(value = "update-patient/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable int id, @RequestBody Patient patient) {
        patientService.updatePatient(id, patient);
        return new ResponseEntity<>("Updated Patient", HttpStatus.OK);
    }
    @DeleteMapping(value = "delete-patient/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable int id){
        patientService.deletePatient(id);
        return new ResponseEntity<>("Deleted Patient", HttpStatus.NO_CONTENT);
    }
}
