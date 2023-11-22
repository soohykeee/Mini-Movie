package com.example.minimovie.repository;

import com.example.minimovie.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

//    @Query("SELECT t FROM Theater t JOIN FETCH t s JOIN FETCH s.movie m WHERE t.theaterId = :theaterId")
//    List<Theater> findTheaterWithScreensAndMoviesByTheaterId(Long theaterId);

}
