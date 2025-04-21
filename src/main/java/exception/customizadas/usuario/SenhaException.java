package exception.customizadas.usuario;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SenhaException extends RuntimeException {
  private final HttpStatus status;

  public SenhaException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }
}
