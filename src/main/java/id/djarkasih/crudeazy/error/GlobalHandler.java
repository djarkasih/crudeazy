/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.error;

import static id.djarkasih.crudeazy.error.RestifierError.COLLECTION_NOT_FOUND;
import id.djarkasih.crudeazy.model.ErrorEnvelope;
import id.djarkasih.crudeazy.model.ErrorPayload;
import id.djarkasih.crudeazy.model.FieldViolation;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
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
    
    @ExceptionHandler({RestifierException.class})
    public final ResponseEntity<ErrorEnvelope> handleRestifierException(RestifierException ex, HttpServletRequest req) {
        
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        switch (ex.getErrorType()) {
            case MULTI_ROW_EDIT_DISABLED :
            case INVALID_SQL_SYNTAX :
            case INVALID_FORMAT :
            case DATA_INCOMPLETE :
                status = HttpStatus.BAD_REQUEST;
                break;
            case DATABASE_NOT_FOUND :
            case COLLECTION_NOT_FOUND :
            case DATA_NOT_FOUND :
                status = HttpStatus.NOT_FOUND;
                break;
            case INVALID_DATABASE_SETTING : 
            case DATABASE_ERROR :
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
        }
        
        ErrorEnvelope env = new ErrorEnvelope(status.value());
        
        ErrorPayload payload = new ErrorPayload(req.getRequestURI(),req.getMethod(),ex.getClass().getCanonicalName(),ex.getMessage());
        if (ex.getCause() != null) {
            payload.setCause(ex.getCause().getMessage());
        }
        
        env.setError(payload);
        
        return new ResponseEntity<>(env,status);
        
    }

    @ExceptionHandler({DataAccessException.class})
    public final ResponseEntity<ErrorEnvelope> handleDataAccessException(DataAccessException ex, HttpServletRequest req) {
        
        ErrorEnvelope env = new ErrorEnvelope(HttpStatus.BAD_REQUEST.value());
        
        ErrorPayload payload = new ErrorPayload(req.getRequestURI(),req.getMethod(),ex.getClass().getCanonicalName(),ex.getMessage());
        if (ex.getCause() != null) {
            payload.setCause(ex.getCause().getMessage());
        }
        
        env.setError(payload);
        
        return new ResponseEntity<>(env,HttpStatus.valueOf(env.getCode()));
        
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public final ResponseEntity<ErrorEnvelope> handleDataValidationException(ConstraintViolationException ex, HttpServletRequest req) {
        ErrorEnvelope env = new ErrorEnvelope(HttpStatus.BAD_REQUEST.value());
        
        ErrorPayload payload = new ErrorPayload(req.getRequestURI(),req.getMethod(),ex.getClass().getCanonicalName());
        
        List<FieldViolation> violations = ex.getConstraintViolations().stream().map(FieldViolation::of).collect(Collectors.toList());
        payload.setViolations(violations);
        
        if (ex.getCause() != null) {
            payload.setCause(ex.getCause().getMessage());
        }
        
        env.setError(payload);
        
        return new ResponseEntity<>(env,HttpStatus.valueOf(env.getCode()));
        
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public final ResponseEntity<ErrorEnvelope> handleBadInputData(HttpMessageNotReadableException ex, HttpServletRequest req) {
        ErrorEnvelope env = new ErrorEnvelope(HttpStatus.BAD_REQUEST.value());
        
        ErrorPayload payload = new ErrorPayload(req.getRequestURI(),req.getMethod(),ex.getClass().getCanonicalName(),ex.getMessage());
        if (ex.getCause() != null) {
            payload.setCause(ex.getCause().getMessage());
        }
        
        env.setError(payload);
        
        return new ResponseEntity<>(env,HttpStatus.valueOf(env.getCode()));
        
    }

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<ErrorEnvelope> handleOtherExceptions(Exception ex, HttpServletRequest req) {
        
        ErrorEnvelope env = new ErrorEnvelope(HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        ErrorPayload payload = new ErrorPayload(req.getRequestURI(),req.getMethod(),ex.getClass().getCanonicalName(),ex.getMessage());
        if (ex.getCause() != null) {
            payload.setCause(ex.getCause().getMessage());
        }
        
        env.setError(payload);
        
        return new ResponseEntity<>(env,HttpStatus.INTERNAL_SERVER_ERROR);
        
    }

}
