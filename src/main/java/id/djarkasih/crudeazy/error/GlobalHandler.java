/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.error;

import id.djarkasih.crudeazy.model.ErrorEnvelope;
import id.djarkasih.crudeazy.model.ErrorPayload;
import id.djarkasih.crudeazy.model.FieldViolation;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author ahmad
 */
@ControllerAdvice
public class GlobalHandler {
    
    @ExceptionHandler({DataNotFound.class})
    public final ResponseEntity<ErrorEnvelope> handleDataNotFound(DataNotFound ex, HttpServletRequest req) {
        
        ErrorEnvelope env = new ErrorEnvelope(HttpStatus.NOT_FOUND.value(), "Invalid Request : Data not found.");
        
        ErrorPayload payload = new ErrorPayload(req.getRequestURI(),req.getMethod(),ex.getClass().getCanonicalName(),ex.getMessage());
        if (ex.getCause() != null) {
            payload.setCause(ex.getCause().getMessage());
        }
        
        env.setExceptions(payload);
        
        return new ResponseEntity<>(env,HttpStatus.valueOf(env.getCode()));
        
    }

    @ExceptionHandler({DataIncomplete.class})
    public final ResponseEntity<ErrorEnvelope> handleIncompleteData(DataIncomplete ex, HttpServletRequest req) {
        
        ErrorEnvelope env = new ErrorEnvelope(HttpStatus.BAD_REQUEST.value(), "Invalid Request : Incomplete input data.");
        
        ErrorPayload payload = new ErrorPayload(req.getRequestURI(),req.getMethod(),ex.getClass().getCanonicalName(),ex.getMessage());
        if (ex.getCause() != null) {
            payload.setCause(ex.getCause().getMessage());
        }
        
        env.setExceptions(payload);
        
        return new ResponseEntity<>(env,HttpStatus.valueOf(env.getCode()));
        
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public final ResponseEntity<ErrorEnvelope> handleDataValidationException(ConstraintViolationException ex, HttpServletRequest req) {
        ErrorEnvelope env = new ErrorEnvelope(HttpStatus.BAD_REQUEST.value(),"Invalid Request : Data Validation Error.");
        
        ErrorPayload payload = new ErrorPayload(req.getRequestURI(),req.getMethod(),ex.getClass().getCanonicalName());
        
        List<FieldViolation> violations = ex.getConstraintViolations().stream().map(FieldViolation::of).collect(Collectors.toList());
        payload.setViolations(violations);
        
        if (ex.getCause() != null) {
            payload.setCause(ex.getCause().getMessage());
        }
        
        env.setExceptions(payload);
        
        return new ResponseEntity<>(env,HttpStatus.valueOf(env.getCode()));
        
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public final ResponseEntity<ErrorEnvelope> handleBadInputData(HttpMessageNotReadableException ex, HttpServletRequest req) {
        ErrorEnvelope env = new ErrorEnvelope(HttpStatus.BAD_REQUEST.value(), "Invalid Request : Invalid input data.");
        
        ErrorPayload payload = new ErrorPayload(req.getRequestURI(),req.getMethod(),ex.getClass().getCanonicalName(),ex.getMessage());
        if (ex.getCause() != null) {
            payload.setCause(ex.getCause().getMessage());
        }
        
        env.setExceptions(payload);
        
        return new ResponseEntity<>(env,HttpStatus.valueOf(env.getCode()));
        
    }

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<ErrorEnvelope> handleOtherExceptions(Exception ex, HttpServletRequest req) {
        ErrorEnvelope env = new ErrorEnvelope(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error");
        
        ErrorPayload payload = new ErrorPayload(req.getRequestURI(),req.getMethod(),ex.getClass().getCanonicalName(),ex.getMessage());
        if (ex.getCause() != null) {
            payload.setCause(ex.getCause().getMessage());
        }
        
        env.setExceptions(payload);
        
        return new ResponseEntity<>(env,HttpStatus.INTERNAL_SERVER_ERROR);
        
    }

}
