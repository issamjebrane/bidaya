package com.bidaya.bidaya.projects.projectRepositories;

import com.bidaya.bidaya.projects.Imagedata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Imagedata, Long> {
}
