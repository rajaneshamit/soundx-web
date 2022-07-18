package com.anstech.speechtotext.service;

import java.nio.file.Path;

public interface FileExporterService {

	public Path export(String fileContent, String fileName);

}
