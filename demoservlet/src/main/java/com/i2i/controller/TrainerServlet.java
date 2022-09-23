package com.i2i.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.i2i.model.Trainee;
import com.i2i.model.Trainer;
import com.i2i.service.EmployeeService;
import com.i2i.service.impl.EmployeeServiceImpl;
import org.hibernate.HibernateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TrainerServlet extends HttpServlet {

    private static final String line = ("******************************************************");
    private static final EmployeeService employeeService = new EmployeeServiceImpl();
    private ObjectMapper mapper = new ObjectMapper();
/*
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        List<Trainer> listOfTrainers = null;
        if (uri.equals("/demoservlet/Trainers")) {
            try {
                listOfTrainers = employeeService.showAllTrainerDetails();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                if (CommonUtil.validateTrainers(listOfTrainers)) {
                    System.out.println(line);
                    for (Trainer list : listOfTrainers) {
                        response.getOutputStream().println(("\n Employee Name : ")
                                + (list.getName())
                                + (" \n EmployeeID : ")
                                + (list.getId())
                                + (" \n MobileNumber : ")
                                + (list.getMobileNumber())
                                + (" \n Role Of Employee : ")
                                + (list.getRole()) + ("\n")
                                + "\n " + line);
                    }
                } else {
                    response.getOutputStream().println("***** THERE IS AN EMPTY LIST ******");
                }

            } catch (NullListException ex) {
                throw new RuntimeException(ex);
            }
        } else {

            Trainer trainer = null;
            try {
                String value = request.getParameter("id");
                int id = Integer.parseInt(value);
                trainer = employeeService.showTrainerDetailsById(id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println(line);
            response.getOutputStream().println(("\n Employee Name : ")
                    + (trainer.getName()) + (" \n EmployeeID : ")
                    + (trainer.getId()) + (" \n MobileNumber : ")
                    + (trainer.getMobileNumber()) + (" \n Role Of Employee : ")
                    + ("List Of Trainers : ") + (trainer.getTraineeDetails())
                    + (trainer.getRole()) + ("\n") + (line));
        }

    }  */

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "***** Details Not Modify *****";
        Trainer trainer = null;
        String uri = request.getRequestURI();
        String value = request.getParameter("id");
        int id = Integer.parseInt(value);

        if (uri.equals("/demoservlet/Trainer")) {
            try {
                trainer = employeeService.showTrainerDetailsById(id);
                trainer.setName(request.getParameter("name"));
                trainer.setMail(request.getParameter("mail"));
                trainer.setAddress(request.getParameter("address"));
                trainer.setPanNumber(request.getParameter("panNumber"));
                trainer.setRole(request.getParameter("role"));
                int experience = Integer.parseInt(request.getParameter("experience"));
                trainer.setExperience(experience);
                String aadhar = request.getParameter("aadharNumber");
                trainer.setAadharNumber(Long.parseLong(aadhar));
                trainer.setPreviousCompanyName(request.getParameter("companyName"));
                String number = request.getParameter("mobileNumber");
                Long mobile = Long.parseLong(number);
                trainer.setMobileNumber(mobile);
                String date = request.getParameter("dateOfBirth");
                LocalDate birth = LocalDate.parse(date);
                trainer.setDateOfBirth(birth);
                LocalDate year = LocalDate.parse(request.getParameter("dateOfJoin"));
                trainer.setDateOfJoin(year);
                message = employeeService.modifyTrainerDetailsById(id, trainer);
                response.getOutputStream().println(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                List<Trainee> list = null;
                int trainerId = Integer.parseInt(request.getParameter("id"));
                trainer = employeeService.showTrainerDetailsById(trainerId);
                String ids = request.getParameter("traineeIds");
                String[] traineeId = ids.split(",");

                for (int i = 0; i < traineeId.length; i++) {
                    int traineesId = Integer.valueOf(traineeId[i]);
                    Trainee trainee = employeeService.showTraineeDetailsById(traineesId);

                    if (trainee != null) {
                        trainer.getTraineeDetails().add(trainee);

                    } else {
                        message = "**** THERE IS NO TRAINEE ID *****";
                    }
                }
                message = employeeService.assignTrainees(trainerId, trainer);
                response.getOutputStream().println(message);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
/*
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String message = "***** Not Inserted ******";
        System.out.println(uri);
        String name = request.getParameter("name")
        System.out.println(name);
        if (uri != null) {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            Trainer trainer = mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .findAndRegisterModules().readValue(payload, Trainer.class);

            try {
                message = employeeService.addTrainerDetails(trainer);
                response.getOutputStream().println(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            response.getOutputStream().println(message);
        }
    }
*/
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "******* there is no record to Delete *******";
        String uri = request.getRequestURI();
        if (uri.equals("/demoservlet/Trainer")) {
            String value = request.getParameter("id");
            int id = Integer.parseInt(value);
            try {
                message = employeeService.deleteTrainerDetails(id);
                response.getOutputStream().println(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            int trainerId = Integer.parseInt(request.getParameter("id"));
            Trainer trainer = null;
            message = "******* THERE IS NO ID TO REMOVE ********";

            try {
                trainer = employeeService.showTrainerDetailsById(trainerId);
                int traineeId = Integer.parseInt(request.getParameter("traineeId"));
                message = employeeService.removeIdFromAssignedTrainer(traineeId, removeTrainee(traineeId, trainer));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    private Trainer removeTrainee(int traineeId, Trainer trainer) throws Exception {
        List<Trainee> list = trainer.getTraineeDetails();
        try {
            if (list.size() >= 1) {
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(list.get(i).getId());
                    if ((list.get(i).getId()) == (traineeId)) {
                        list.remove(i);
                    }
                }
                trainer.setTraineeDetails(list);
            } else {
                String message = "***** THERE IS NO ID TO REMOVE ******";
            }

        } catch (HibernateException e) {
            throw e;
        }
        return trainer;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


        String uri = request.getRequestURI();
        List<Trainer> showTrainer = null;

        if (uri.equals("/demoservlet/Trainers")) {
            try {
                showTrainer = employeeService.showAllTrainerDetails();

            PrintWriter printWriter = response.getWriter();
            for(Trainer trainers : showTrainer) {
                Map<String, Object> trainer = employeeService.getTrainerObject(trainers);
                String jsonStr = mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                        .findAndRegisterModules().writeValueAsString(trainer);
                printWriter.print(jsonStr);
            }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            String id = request.getParameter("id");
            int trainerId = Integer.parseInt(id);
            PrintWriter printWriter = response.getWriter();
            Trainer trainer = null;
            try {
                trainer = employeeService.showTrainerDetailsById(trainerId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (trainer != null) {
                Map<String, Object> trainer1 = employeeService.getTrainerObject(trainer);
                String jsonStr = mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                        .findAndRegisterModules().writeValueAsString(trainer1);
                printWriter.print(jsonStr);
               // String jsonStr = mapper.writeValueAsString(trainer);


            } else {
                printWriter.println("Invalid Employee ID");
            }

        }
    }

   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "****** Not Inserted ******";
        Trainer trainer = new Trainer();
        trainer.setName(request.getParameter("name"));
        trainer.setMail(request.getParameter("mail"));
        trainer.setAddress(request.getParameter("address"));
        trainer.setPanNumber(request.getParameter("panNumber"));
        trainer.setRole(request.getParameter("role"));
        int experience = Integer.parseInt(request.getParameter("experience"));
        trainer.setExperience(experience);
        String temp1 = request.getParameter("aadharNumber");
        trainer.setAadharNumber(Long.parseLong(temp1));
        String temp2 = request.getParameter("mobileNumber");
        trainer.setMobileNumber(Long.parseLong(temp2));
        trainer.setPreviousCompanyName(request.getParameter("companyName"));
        String temp = request.getParameter("dateOfBirth");
        LocalDate birth = LocalDate.parse(temp);
        trainer.setDateOfBirth(birth);
        LocalDate date = LocalDate.parse(request.getParameter("dateOfJoin"));
        trainer.setDateOfJoin(date);

        try {
            message = employeeService.addTrainerDetails(trainer);
            response.getOutputStream().println(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void dataValidation(HttpServletRequest request) {


    }
}