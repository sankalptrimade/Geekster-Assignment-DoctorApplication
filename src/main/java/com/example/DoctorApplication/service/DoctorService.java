package com.example.DoctorApplication.service;

import com.example.DoctorApplication.model.Doctor;
import com.example.DoctorApplication.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {
    @Autowired
    DoctorRepository doctorRepository;
    public Doctor saveDoctor(Doctor doctor1) {
        String docName = doctor1.getDoctorName();
        docName = "Dr. "+docName;
        doctor1.setDoctorName(docName);
        return doctorRepository.save(doctor1);
    }

    public List<Doctor> getDoctor(String doctorId) {
        List<Doctor> doctorList;
        if(null != doctorId){
            doctorList = new ArrayList<>();
            doctorList.add(doctorRepository.findById(Integer.valueOf(doctorId)).get());
        }else{
            doctorList = doctorRepository.findAll();
        }
        return doctorList;
    }

    public void updateDoctor(int id, Doctor newDoctor) {
        Doctor doctor = doctorRepository.findById(id).get();
        doctor.setDoctorName(newDoctor.getDoctorName());
        doctor.setSpecializedIn(newDoctor.getSpecializedIn());
        doctor.setExperience(newDoctor.getExperience());
        doctorRepository.save(doctor);
    }

    public void deleteDoctor(int id) {
        doctorRepository.deleteById(id);
    }
}
