package com.laiscarvalho.orderparser.usecase.port;

import java.io.InputStream;

public interface OrderPort {
  void executeImporter(InputStream file);
}
