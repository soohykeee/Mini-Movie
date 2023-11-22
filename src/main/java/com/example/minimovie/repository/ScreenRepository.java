package com.example.minimovie.repository;

import com.example.minimovie.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {

    List<Screen> findScreensByMovie_Id(Long movieId);

    List<Screen> findScreensByTheater_Id(Long theaterId);

}
