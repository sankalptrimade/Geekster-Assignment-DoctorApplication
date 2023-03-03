package com.example.DoctorApplication.service;

import com.example.DoctorApplication.model.Patient;
import com.example.DoctorApplication.repository.PatientRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    public JSONArray getPatients(String doctorId, String patientId) {
        JSONArray jsonArray;
        if (null == doctorId && null == patientId) {
            List<Patient> patientList = patientRepository.findAll();
            jsonArray = new JSONArray();
            for (Patient patient : patientList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("patientId", patient.getPatientId());
                jsonObject.put("patientName", patient.getPatientName());
                jsonObject.put("age", patient.getAge());
                jsonObject.put("phoneNumber", patient.getPhoneNumber());
                jsonObject.put("diseaseType", patient.getDiseaseType());
                jsonObject.put("gender", patient.getGender());
                jsonObject.put("admitDate", patient.getAdmitDate());
                jsonObject.put("doctorId", patient.getDoctor());
                jsonArray.put(jsonObject);
            }
            return jsonArray;
        } else if (null != patientId) {
            Patient patient = patientRepository.findById(Integer.parseInt(patientId)).get();
            jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("patientId", patient.getPatientId());
            jsonObject.put("patientName", patient.getPatientName());
            jsonObject.put("age", patient.getAge());
            jsonObject.put("phoneNumber", patient.getPhoneNumber());
            jsonObject.put("diseaseType", patient.getDiseaseType());
            jsonObject.put("gender", patient.getGender());
            jsonObject.put("admitDate", patient.getAdmitDate());
            jsonObject.put("doctorId", patient.getDoctor());
            jsonArray.put(jsonObject);
            return jsonArray;
        } else if (null != doctorId) {
            List<Patient> patientList = patientRepository.findAll();
            jsonArray = new JSONArray();
            for (Patient patient : patientList) {
                if(patient.getDoctor().getDoctorId() == Integer.valueOf(doctorId)) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("patientId", patient.getPatientId());
                    jsonObject.put("patientName", patient.getPatientName());
                    jsonObject.put("age", patient.getAge());
                    jsonObject.put("phoneNumber", patient.getPhoneNumber());
                    jsonObject.put("diseaseType", patient.getDiseaseType());
                    jsonObject.put("gender", patient.getGender());
                    jsonObject.put("admitDate", patient.getAdmitDate());
                    jsonObject.put("doctorId", patient.getDoctor());
                    jsonArray.put(jsonObject);
                }
            }
            return jsonArray;
        }else {
            jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Invalid", false);
            jsonArray.put(jsonObject);
            return jsonArray;
        }
    }

    public void updatePatient(int id, Patient newPatient) {
        Patient patient = patientRepository.findById(id).get();
        patient.setPatientName(newPatient.getPatientName());
        patient.setAge(newPatient.getAge());
        patient.setPhoneNumber(newPatient.getPhoneNumber());
        patient.setDiseaseType(newPatient.getDiseaseType());
        patient.setGender(newPatient.getGender());
        patient.setDoctor(newPatient.getDoctor());
        patientRepository.save(patient);
    }

    public void deletePatient(int id) {
        patientRepository.deleteById(id);
    }
}
