package com.wallparisoft.controller;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wallparisoft.service.IQueryService;

@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private IQueryService service;

	@GetMapping(value = "/query", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<?> generarReporte(@RequestParam boolean isBase64) {
		byte[] data = service.generateReport();
		return loadFile(isBase64, data);
	}

	private ResponseEntity<?> loadFile(boolean isBase64, byte[] data) {
		if (isBase64) {
			String report = new String(Base64.encodeBase64(data));
			return new ResponseEntity<>(report, HttpStatus.OK);
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Description", "File");
			headers.add("Content-Disposition", "attachment; filename=report.pdf");
			headers.add("Content-type", "application/pdf");
			return new ResponseEntity<>(data, headers, HttpStatus.OK);
		}
	}
}
