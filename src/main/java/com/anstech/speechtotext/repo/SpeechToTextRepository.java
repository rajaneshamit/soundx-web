package com.anstech.speechtotext.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anstech.speechtotext.entity.VoiceText;

@Repository
public interface SpeechToTextRepository extends JpaRepository<VoiceText, Long> {

	
}
