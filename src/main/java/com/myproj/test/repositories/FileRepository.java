package com.myproj.test.repositories;

import com.myproj.test.entities.File;
import com.myproj.test.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FileRepository extends PagingAndSortingRepository<File, Long> {
    List<File> findAllByUser(User user);
    File findBySignature(String signature);
}
