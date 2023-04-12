package es.storeapp.business.entities;


import es.storeapp.business.exceptions.InvalidStateException;
import es.storeapp.business.utils.ExceptionGenerationUtils;
import es.storeapp.common.Constants;
import java.io.Serializable;
import java.text.MessageFormat;
import javax.persistence.Embeddable;
import org.springframework.beans.factory.annotation.Autowired;

@Embeddable
public class CreditCard implements Serializable {
    @Autowired
    ExceptionGenerationUtils exceptionGenerationUtils;
    
    protected String card;// la tarjeta va en un string
    
    private Integer cvv;// el cvv va en un num entero
    
    private Integer expirationMonth;// el mes de expiración va en un entero
    
    private Integer expirationYear;// el año de expiración va en un entero

    public String getCard() {// devuelve el string de la tarjeta
        return card;
    }

    public void setCard(String card) throws InvalidStateException {// cambia el string de la tarjeta
        
            if(!validarNumeroTarjeta(card)){
                throw exceptionGenerationUtils.toInvalidStateException(Constants.INVALID_STATE_EXCEPTION_MESSAGE);
                
            }else{
                this.card = card;
            }
        
    }

    public Integer getCvv() {// devuelve el cvv
        return cvv;
    }

    public void setCvv(Integer cvv) {// cambia el cvv
        this.cvv = cvv;
    }

    public Integer getExpirationMonth() {// devuelve el mes de expiración
        return expirationMonth;
    }

    public void setExpirationMonth(Integer expirationMonth) {// cambia el mes de expiración
        this.expirationMonth = expirationMonth;
    }

    public Integer getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
    }

    @Override
    public String toString() {// devuelve los argumentos en un string
        return MessageFormat.format("CreditCard{card={0}, cvv={1}, expirationMonth={2}, expirationYear={3}}", 
            card, cvv, expirationMonth, expirationYear);
    }
    public  boolean validarNumeroTarjeta(String numeroTarjeta) {
        // Paso 1: Verificar si el número de tarjeta contiene solo dígitos y no está vacío
        if (!numeroTarjeta.matches("\\d+") || numeroTarjeta.isEmpty()) {
            return false;
        }

        // Paso 2: Verificar si el número de tarjeta tiene una longitud válida
        int longitud = numeroTarjeta.length();
        if (longitud < 13 || longitud > 19) {
            return false;
        }

        // Paso 3: Aplicar el algoritmo de Luhn para validar el número de tarjeta
        int suma = 0;
        boolean alternar = false;
        for (int i = longitud - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(numeroTarjeta.charAt(i));
            if (alternar) {
                digito *= 2;
                if (digito > 9) {
                    digito -= 9;
                }
            }
            suma += digito;
            alternar = !alternar;
        }
        return suma % 10 == 0;
    }
    
}
