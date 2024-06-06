package com.laiscarvalho.orderparser.entry;

import com.laiscarvalho.orderparser.usecase.port.OrderPort;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1", produces = {"application/json"})
public class Controller {

  private final OrderPort orderPort;

  @PostMapping(value = "/orders/importer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void importFile(@RequestParam("file") MultipartFile multipartFile) {
    try {
      log.info("[import-file-controller] file received: {}", multipartFile);
      orderPort.executeImporter(multipartFile.getInputStream());
    } catch (IOException e) {
      log.error("[import-file-controller] error to process file: {}", multipartFile, e);
    } catch (Exception e) {
      log.error("[import-file-controller] invalid input file: {}", multipartFile, e);
    }
  }
}
