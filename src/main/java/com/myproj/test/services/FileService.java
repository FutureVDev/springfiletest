package com.myproj.test.services;

import com.myproj.test.entities.File;
import com.myproj.test.entities.User;
import com.myproj.test.repositories.FileRepository;
import com.myproj.test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Service
public class FileService {
    private FileRepository fileRepository;
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setFileRepository(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    public List<File> getAllByUserName(String userName){
        return getAllByUser(userRepository.findOneByUserName(userName));
    }

    public List<File> getAllByUser(User user){
        return fileRepository.findAllByUser(user);
    }
}
