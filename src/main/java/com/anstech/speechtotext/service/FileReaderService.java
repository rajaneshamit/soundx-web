package com.anstech.speechtotext.service;

import java.io.IOException;
import com.anstech.speechtotext.model.Response;

public interface FileReaderService {

	public Response readFileData(String fileName) throws IOException;
}
