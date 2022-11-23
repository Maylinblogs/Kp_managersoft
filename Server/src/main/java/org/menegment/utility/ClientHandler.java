package org.menegment.utility;

import org.menegment.enums.Action;
import org.menegment.enums.Roles;
import org.menegment.models.*;
import org.menegment.service.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    //enums-----------------------------------
    private Roles role;
    private UserService userService = new UserService();

    private PersonService personService = new PersonService();
    private WorkerService workerService = new WorkerService();

    private ResumeService resumeService = new ResumeService();

    private VacanciesService vacanciesService = new VacanciesService();

    private ClaimService claimService = new ClaimService();

    private DepartmentService departmentService = new DepartmentService();

    public ClientHandler(Socket acceptSocket) throws IOException {
        this.clientSocket = acceptSocket;
        this.objectOutputStream = new ObjectOutputStream(acceptSocket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(acceptSocket.getInputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Action a = (Action) objectInputStream.readObject();
                switch (a) {
                    case LOGIN: {
                        User user;
                        user = (User) objectInputStream.readObject();
                        if (userService.isEntity(user)) {
                            user = userService.correctFindEntityString(user);
                            objectOutputStream.writeObject(Action.OK);
                            objectOutputStream.flush();
                            objectOutputStream.writeObject(user);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;
                    }
                    case REGISTER: {
                        User user;
                        boolean isTrue = false;
                        user = (User) objectInputStream.readObject();
                        user.setRole_user(Roles.WORKER);
                        if (!userService.isEntityToOne(user)) {
                            isTrue = userService.correctSaveEntity(user);
                            if (isTrue) {
                                user = userService.correctFindEntityString(user);
                                objectOutputStream.writeObject(Action.OK);
                                objectOutputStream.flush();
                                objectOutputStream.writeObject(user);
                                objectOutputStream.flush();
                            } else {
                                objectOutputStream.writeObject(Action.NO);
                                objectOutputStream.flush();
                            }
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;
                    }

                    case ALL_USER: {
                        ArrayList<User> users;
                        users = userService.correctAllEntity();
                        if (users != null) {
                            objectOutputStream.writeObject(Action.OK);
                            objectOutputStream.flush();
                            objectOutputStream.writeObject(users);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;
                    }
                    case DELETE_USER: {
                        boolean isTrue = false;
                        User user = (User) objectInputStream.readObject();
                        if (userService.isEntityToOne(user)) {
                            isTrue = userService.correctDeleteEntity(user);
                            if (isTrue) {
                                objectOutputStream.writeObject(Action.OK);
                                objectOutputStream.flush();
                            } else {
                                objectOutputStream.writeObject(Action.NO);
                                objectOutputStream.flush();
                            }

                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;
                    }
                    case ADD_USER: {
                        User user = (User) objectInputStream.readObject();
                        if (!userService.isEntityToOne(user)) {
                            userService.correctSaveEntity(user);
                            user = userService.correctFindEntityString(user);
                            objectOutputStream.writeObject(Action.OK);
                            objectOutputStream.flush();
                            objectOutputStream.writeObject(user);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;
                    }
                    case REDAKT_USER: {
                        boolean isTrue;
                        User user = (User) objectInputStream.readObject();
                        if (!userService.isEntityToOne(user)) {
                            isTrue = userService.correctUpdateEntity(user);
                            if (isTrue) {
                                user = userService.correctFindEntityString(user);
                                objectOutputStream.writeObject(Action.OK);
                                objectOutputStream.flush();
                                objectOutputStream.writeObject(user);
                                objectOutputStream.flush();
                            } else {
                                objectOutputStream.writeObject(Action.NO);
                                objectOutputStream.flush();
                            }
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;

                    }
                    case ORDER_USER: {
                        ArrayList<User> userss;
                        userss = userService.correctAllEntity();
                        if (userss != null) {
                            objectOutputStream.writeObject(Action.OK);
                            objectOutputStream.flush();
                            objectOutputStream.writeObject(userss);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;
                    }
                    case POISK_USER: {
                        User user = (User) objectInputStream.readObject();
                        user = userService.poisk(user);
                        if (user != null) {
                            objectOutputStream.writeObject(Action.OK);
                            objectOutputStream.flush();
                            objectOutputStream.writeObject(user);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;

                    }
                    case GET_PERSON_WORKER_RESUME: {
                        User user = (User) objectInputStream.readObject();
                        user = userService.correctFindEntityString(user);
                        Worker worker = workerService.correctFindEntityInt(user.getUser_id());
                        objectOutputStream.writeObject(Action.OK);
                        objectOutputStream.flush();
                        objectOutputStream.writeObject(worker);
                        objectOutputStream.flush();
                        break;
                    }
                    case REDAKT_USER_WORKER: {
                        boolean isTrue;
                        User user = (User) objectInputStream.readObject();
                        User user1 = (User) objectInputStream.readObject();
                        System.out.println(user);
                        System.out.println(user1);
                        if (!userService.isEntityToOne(user)) {
                            user1 = userService.correctFindEntityString(user1);
                            user1.setLogin(user.getLogin());
                            user1.setPassword(user.getPassword());
                            isTrue = userService.correctUpdateEntity(user1);
                            if (isTrue) {
                                user = userService.correctFindEntityString(user1);
                                objectOutputStream.writeObject(Action.OK);
                                objectOutputStream.flush();
                                objectOutputStream.writeObject(user);
                                objectOutputStream.flush();
                            } else {
                                objectOutputStream.writeObject(Action.NO);
                                objectOutputStream.flush();
                            }
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;
                    }
                    case CREATE_WORKER_RESUME: {
                        Worker worker;
                        worker = (Worker) objectInputStream.readObject();
                        System.out.println(worker);
                        if (!workerService.isEntityToOne(worker)) {
                            Resume resume = worker.getResume();
                            workerService.correctSaveEntity(worker);
                            worker = workerService.correctFindEntityString(worker.getPassport());
                            resume.setResume_id(worker.getWorker_id());
                            System.out.println(resume);
                            resumeService.correctSaveEntity(resume);
                            objectOutputStream.writeObject(Action.OK);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;
                    }
                    case ALL_VACANCIES: {
                        ArrayList<Vacancies> vacanciesArrayList;
                        vacanciesArrayList = vacanciesService.correctAllEntity();
                        if (vacanciesArrayList != null) {
                            objectOutputStream.writeObject(Action.OK);
                            objectOutputStream.flush();
                            objectOutputStream.writeObject(vacanciesArrayList);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;
                    }
                    case POISK_VACANCIES: {
                        Vacancies vacancies = (Vacancies) objectInputStream.readObject();
                        vacancies = vacanciesService.poisk(vacancies);
                        if (vacancies != null) {
                            objectOutputStream.writeObject(Action.OK);
                            objectOutputStream.flush();
                            objectOutputStream.writeObject(vacancies);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;
                    }

                    case CREATE_CLAIM: {
                        Claim claim = (Claim) objectInputStream.readObject();
                        System.out.println(claim);
                        if (claimService.correctSaveEntity(claim)) {
                            objectOutputStream.writeObject(Action.OK);
                            objectOutputStream.flush();
                            objectOutputStream.writeObject(claim);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;

                    }
                    case DELETE_CLAIM: {
                        Claim claim = (Claim) objectInputStream.readObject();
                        boolean isTrue = false;
                        if (claimService.isEntityToOne(claim)) {
                            isTrue = claimService.correctDeleteEntity(claim);
                            System.out.println(isTrue);
                            if (isTrue) {
                                objectOutputStream.writeObject(Action.OK);
                                objectOutputStream.flush();
                            } else {
                                objectOutputStream.writeObject(Action.NO);
                                objectOutputStream.flush();
                            }
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;

                    }
                    case UPDATE_CLAIM: {

                        Worker worker = (Worker) objectInputStream.readObject();
                        System.out.println(worker);
                        ArrayList<Claim> claims = claimService.correctAllEntity();
                        System.out.println(claims);
                        ArrayList<Claim> claimsYes = new ArrayList<>();
                        ArrayList<Claim> claimsNo = new ArrayList<>();
                        ArrayList<Claim> claimsIoI = new ArrayList<>();
                        for (Claim c : claims) {
                            if (Objects.equals(c.getId_worker(), worker.getWorker_id())) {

                                if (c.getStatus_claim() == 0) {
                                    claimsIoI.add(c);
                                } else {
                                    if (c.getStatus_claim() == 1) {
                                        claimsNo.add(c);
                                    } else {
                                        claimsYes.add(c);
                                    }

                                }
                            }
                        }
                        objectOutputStream.writeObject(Action.OK);
                        objectOutputStream.flush();
                        objectOutputStream.writeObject(claimsIoI);
                        objectOutputStream.flush();
                        objectOutputStream.writeObject(claimsNo);
                        objectOutputStream.flush();
                        objectOutputStream.writeObject(claimsYes);
                        objectOutputStream.flush();

                        break;
                    }
                    case ALL_CLIAM: {
                        ArrayList<Claim> claimArrayList;
                        claimArrayList = claimService.correctAllEntity();
                        if (claimArrayList != null) {
                            objectOutputStream.writeObject(Action.OK);
                            objectOutputStream.flush();
                            objectOutputStream.writeObject(claimArrayList);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;
                    }
                    case NO_CLAIM: {
                        boolean isTrue;
                        Claim claim = (Claim) objectInputStream.readObject();
                        System.out.println(claim);
                        isTrue = claimService.correctUpdateEntity(claim);
                        if (isTrue) {
                            objectOutputStream.writeObject(Action.OK);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;
                    }
                    case POISK_CLIAM: {
                        Claim claim = (Claim) objectInputStream.readObject();
                        claim = claimService.poisk(claim);
                        if (claim != null) {
                            objectOutputStream.writeObject(Action.OK);
                            objectOutputStream.flush();
                            objectOutputStream.writeObject(claim);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;
                    }
                    case GET_CLAIM_WORKER: {
                        Claim claim = (Claim) objectInputStream.readObject();
                        if (claim != null) {
                            Worker worker = workerService.correctFindEntityIntID(claim.getId_worker());
                            System.out.println(worker);
                            if (worker != null) {
                                objectOutputStream.writeObject(Action.OK);
                                objectOutputStream.flush();
                                objectOutputStream.writeObject(worker);
                                objectOutputStream.flush();
                            } else {
                                objectOutputStream.writeObject(Action.NO);
                                objectOutputStream.flush();
                            }
                            break;
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                    }
                    case ALL_DEPARTMENT: {
                        ArrayList<Department> departmentArrayList;
                        departmentArrayList = departmentService.correctAllEntity();
                        System.out.println(departmentArrayList);
                        if (departmentArrayList != null) {
                            objectOutputStream.writeObject(Action.OK);
                            objectOutputStream.flush();
                            objectOutputStream.writeObject(departmentArrayList);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;
                    }
                    case ALL_VACANCIES_USER: {
                        Vacancies vacancies = (Vacancies) objectInputStream.readObject();
                        if (vacanciesService.correctSaveEntity(vacancies)) {
                            objectOutputStream.writeObject(Action.OK);
                            objectOutputStream.flush();
                            objectOutputStream.writeObject(vacancies);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }

                        break;
                    }
                    case ADD_DEPARTMENT: {
                        Department department = (Department) objectInputStream.readObject();
                        if (departmentService.correctSaveEntity(department)) {
                            objectOutputStream.writeObject(Action.OK);
                            objectOutputStream.flush();
                            objectOutputStream.writeObject(department);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }
                        break;
                    }
                    case DELETE_VACANCIES:
                        boolean isTrue = false;
                        Vacancies vacancies = (Vacancies) objectInputStream.readObject();
                        isTrue = vacanciesService.correctDeleteEntity(vacancies);
                        if (isTrue) {
                            objectOutputStream.writeObject(Action.OK);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject(Action.NO);
                            objectOutputStream.flush();
                        }

                        break;
                }


            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
