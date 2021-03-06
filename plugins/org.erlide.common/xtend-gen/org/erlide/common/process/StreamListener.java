package org.erlide.common.process;

import com.google.common.base.Objects;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class StreamListener extends Thread {
  private InputStream stream;
  
  private Procedure1<? super String> callback;
  
  public StreamListener(final InputStream stream, final Procedure1<? super String> callback) {
    this.stream = stream;
    this.callback = callback;
    this.start();
  }
  
  public void run() {
    try {
      InputStreamReader _inputStreamReader = new InputStreamReader(this.stream);
      BufferedReader _bufferedReader = new BufferedReader(_inputStreamReader);
      final BufferedReader reader = _bufferedReader;
      String line = null;
      try {
        String _readLine = reader.readLine();
        String _line = line = _readLine;
        boolean _notEquals = (!Objects.equal(_line, null));
        boolean _while = _notEquals;
        while (_while) {
          this.callback.apply(line);
          String _readLine_1 = reader.readLine();
          String _line_1 = line = _readLine_1;
          boolean _notEquals_1 = (!Objects.equal(_line_1, null));
          _while = _notEquals_1;
        }
      } catch (final Throwable _t) {
        if (_t instanceof OperationCanceledException) {
          final OperationCanceledException e = (OperationCanceledException)_t;
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
