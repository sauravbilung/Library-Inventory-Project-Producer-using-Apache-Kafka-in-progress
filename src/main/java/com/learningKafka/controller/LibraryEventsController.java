package com.learningKafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learningKafka.domain.LibraryEvent;
import com.learningKafka.producer.LibraryEventProducer;

@RestController
public class LibraryEventsController {

	@Autowired
	LibraryEventProducer libraryEventProducer;

	@PostMapping("/v1/libraryevent")
	public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent)
			throws JsonProcessingException {

		// ############################################################################
		// ############ Asynchronous Behavior #########################################

		// invoke kafka producer
		// This line will invoke asynchronous behavior
		// means even before kafka topic will get the data the return will execute.
		// Implement threading here and check.
		// We will see that a new thread will be created for library event producer
		// whose task is to send data to kafka topic and even before that task finishes
		// the return statement will already be executed.
		// The current thread (thread in which controller is) will be in wait queue
		// until it gets the command back. Once it gets command back
		// it will go to ready queue and it will be scheduled once again by OS.

		/*
		 * libraryEventProducer.sendLibraryEventAsynchronous(libraryEvent); return
		 * ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
		 */

		// #############################################################################
		// ############ Synchronous Behavior ###########################################

		// No new thread. Same thread proceeds. Once sendLibraryEventSynchronous()
		// completes publishing to kafka topic then return will be executed.

		/*
		 * libraryEventProducer.sendLibraryEventSynchronous(libraryEvent); return
		 * ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
		 */

		// ############################################################################
		// ########### Asynchronous Behavior with producer record #####################

		libraryEventProducer.sendLibraryEventUsingProducerRecord(libraryEvent);
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);

	}

}
