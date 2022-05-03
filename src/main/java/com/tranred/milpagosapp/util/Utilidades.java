package com.tranred.milpagosapp.util;

import com.tranred.milpagosapp.domain.Parametro;
import com.tranred.milpagosapp.repository.JPAParametroDAO;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  Clase que proporciona metodos comunes de utilidad para todas las clases
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */
@Component
public class Utilidades {
    
    @Autowired
    private JPAParametroDAO parametroDao;       
    
    /**
     * Convierte un valor tipo String en una fecha con formato SQL    
     * @param fecha
     * @return fechaSql
     * @throws java.text.ParseException
    */     
    public static java.sql.Timestamp convierteFechaSql(String fecha) throws ParseException{
     
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");                
        Date fechaUtil = df.parse(fecha);
        
        java.sql.Timestamp fechaSql = new java.sql.Timestamp(fechaUtil.getTime());
        
        return fechaSql;
    }
    
    /**
     * Convierte un valor tipo String en una fecha con formato SQL    
     * @param fecha
     * @return fechaSql
     * @throws java.text.ParseException
    */     
    public static java.sql.Date convierteFechaSqlsinHora(String fecha) throws ParseException{
     
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");                
        Date fechaUtil = df.parse(fecha);
        
        java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
        
        return fechaSql;
    }
        
    /**
     * Convierte un valor tipo String en una fecha con formato SQL    
     * @param fecha
     * @return fechaSql
     * @throws java.text.ParseException
    */     
    public static java.sql.Date convierteFechaHoraSql(String fecha) throws ParseException{
     
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy H:m");                
        Date fechaUtil = df.parse(fecha);
        
        java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
        
        return fechaSql;
    }
    
    /**
     * Convierte un valor tipo SQL Date en un String
     * @param fecha
     * @return fechaSql
     * @throws java.text.ParseException
    */     
    public static String convierteFechaHoraString(Date fecha) throws ParseException{
                
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy H:m");                
        String text = df.format(fecha);                
        
        return text;
    }    
    
    /**
     * Convierte el formato de una fecha dd-MM-yyyy a yyyy-MM-dd
     * @param fecha
     * @return fechaString
     * @throws java.text.ParseException
    */ 
    public static String cambiaFormatoFecha(String fecha) throws ParseException{
        
        Date fechaDate; 
        fechaDate = new SimpleDateFormat("dd-MM-yyyy").parse(fecha);
        String fechaString;
        fechaString = new SimpleDateFormat("yyyy-MM-dd").format(fechaDate);
        
        return fechaString;
    }
    
    /**
     * Convierte el formato de una fecha yyyy-MM-dd HH:mm:ss a dd-MM-yyyy
     * @param fecha
     * @return fechaString
     * @throws java.text.ParseException
    */ 
    public static String cambiaFormatoFecha2(String fecha) throws ParseException{
        
        Date fechaDate; 
        fechaDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fecha);
        String fechaString;
        fechaString = new SimpleDateFormat("dd-MM-yyyy").format(fechaDate);
        
        return fechaString;
    }
    
    /**
     * Convierte el formato de una fecha yyyy-MM-dd a dd-MM-yyyy
     * @param fecha
     * @return fechaString
     * @throws java.text.ParseException
    */ 
    public static String cambiaFormatoFecha3(String fecha) throws ParseException{
        
        Date fechaDate; 
        fechaDate = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        String fechaString;
        fechaString = new SimpleDateFormat("dd-MM-yyyy").format(fechaDate);
        
        return fechaString;
    }
    
    /**
     * Metodo usado para obtener la fecha actual
     * @return Retorna un <b>STRING</b> con la fecha actual formato "dd-MM-yyyy"
    */ 
    public static String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(ahora);
    }
    
    /**
     * Metodo usado para obtener la fecha actual en formato sql
     * @return Retorna un <b>sql date</b> con la fecha actual
    */ 
    public static java.sql.Timestamp getFechaActualSql() {
        Date ahora = new Date();       
        java.sql.Timestamp fechaSQL = new java.sql.Timestamp(ahora.getTime());
        return fechaSQL;
    }

    /**
     * Metodo usado para obtener la hora actual del sistema
     * @return Retorna un <b>STRING</b> con la hora actual formato "hh:mm:ss"
    */
    public static String getHoraActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("hh:mm:ss");
        return formateador.format(ahora);
    }

    /**
     * Sumarle dias a una fecha determinada
     * @param fecha La fecha para sumarle los dias
     * @param dias Numero de dias a agregar
     * @return La fecha agregando los dias
    */ 
    public static java.sql.Date sumarFechasDias(java.sql.Timestamp fecha, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fecha.getTime());
        cal.add(Calendar.DATE, dias);
        return new java.sql.Date(cal.getTimeInMillis());
    }

    /**
     * Restarle dias a una fecha determinada
     * @param fecha La fecha
     * @param dias Dias a restar    
     * @return La fecha restando los dias
    */ 
    public static synchronized java.sql.Date restarFechasDias(java.sql.Date fecha, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fecha.getTime());
        cal.add(Calendar.DATE, -dias);
        return new java.sql.Date(cal.getTimeInMillis());
    }
    
    /**
     * Diferencias entre dos fechas
     * @param fechaInicial La fecha de inicio
     * @param fechaFinal  La fecha de fin
     * @return Retorna el numero de dias entre dos fechas
    */    
    public static int diferenciasDeFechas(Date fechaInicial, Date fechaFinal) {

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String fechaInicioString = df.format(fechaInicial);
        try {
            fechaInicial = df.parse(fechaInicioString);
        } catch (ParseException ex) {
        }

        String fechaFinalString = df.format(fechaFinal);
        try {
            fechaFinal = df.parse(fechaFinalString);
        } catch (ParseException ex) {
        }

        long fechaInicialMs = fechaInicial.getTime();
        long fechaFinalMs = fechaFinal.getTime();
        long diferencia = fechaFinalMs - fechaInicialMs;
        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        return ((int) dias);
    }

    /**
     * Devuele un java.util.Date desde un String en formato dd-MM-yyyy
     * @param fecha La fecha a convertir a formato date
     * @return Retorna la fecha en formato Date
    */
    public static synchronized java.util.Date deStringToDate(String fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaEnviar = null;
        try {
            fechaEnviar = formatoDelTexto.parse(fecha);
            return fechaEnviar;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * Lee un archivo cargado por el usuario
     * @param file
     * @return Retorna un String
    */   
    public String readFile(File file) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null)
                builder.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(reader);
        }
        return builder.toString();
    }

    private void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ignored) {}
        }
    }     
    
    /** 
     * Devuele un parametro segun el codigo
     * @param codigo clave del parametro a consultar en bd
     * @return valor del parametro
    */    
    public List<Parametro> parametro(String codigo) {
        return parametroDao.getByCodigo(codigo);
    }
    
    /**
     * Encripta un valor
     * @param texto valor a encriptar
     * @return Valor encriptado
    */
    public static String Encriptar(String texto) {

        String secretKey = "tranred"; //llave para encriptar datos
        String base64EncryptedString = "";

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);

        } catch (UnsupportedEncodingException ex) {
        } catch (InvalidKeyException ex) {
        } catch (NoSuchAlgorithmException ex) {
        } catch (BadPaddingException ex) {
        } catch (IllegalBlockSizeException ex) {
        } catch (NoSuchPaddingException ex) {
        }
        return base64EncryptedString;
    }
    
    /**
     * Desencripta un valor
     * @param textoEncriptado
     * @return valor desencriptado
     * @throws Exception 
     */
    public static String Desencriptar(String textoEncriptado) throws Exception {

        String secretKey = "tranred"; //llave para encriptar datos
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (UnsupportedEncodingException ex) {
        } catch (InvalidKeyException ex) {
        } catch (NoSuchAlgorithmException ex) {
        } catch (BadPaddingException ex) {
        } catch (IllegalBlockSizeException ex) {
        } catch (NoSuchPaddingException ex) {
        }
        return base64EncryptedString;
    }   
        
    //Fecha en juliano        
    // Gregorian Calendar adopted Oct. 15, 1582 (2299161)
    public static int JGREG= 15 + 31*(10+12*1582);
    public static double HALFSECOND = 0.5;
    
    /**
     * Retorna la fecha en Juliano
     * @param ymd
     * @return Fecha en Juliano
    */
    public static double toJulian(int[] ymd) {
        int year=ymd[0];
        int month=ymd[1]; // jan=1, feb=2,...
        int day=ymd[2];
        int julianYear = year;
        if (year < 0) julianYear++;
        int julianMonth = month;
        if (month > 2) {
         julianMonth++;
        }
        else {
         julianYear--;
         julianMonth += 13;
        }

        double julian = (java.lang.Math.floor(365.25 * julianYear)
            + java.lang.Math.floor(30.6001*julianMonth) + day + 1720995.0);
        if (day + 31 * (month + 12 * year) >= JGREG) {
         // change over to Gregorian calendar
         int ja = (int)(0.01 * julianYear);
         julian += 2 - ja + (0.25 * ja);
        }
        return java.lang.Math.floor(julian);
    }
    
    
    
    
    /**
     * Calcula el ultimo dia del mes y año seleccionado
     * @param mes mes seleccionado
     * @param ano ano seleccionado
     * @return Retorna el ultimo dia del mes y año seleccionado
    */    
    public static String ultimoDiaMes(String mes, String ano) {
        
        Date fecha;        
        Calendar calFin = Calendar.getInstance();		
        calFin.set(Integer.parseInt(ano), Integer.parseInt(mes), 1);
        calFin.set(Integer.parseInt(ano), Integer.parseInt(mes), calFin.getActualMaximum(Calendar.DAY_OF_MONTH));
        fecha = calFin.getTime();
        
        DateFormat fechaHora = new SimpleDateFormat("dd-MM-yyyy");
        String fechaFin = fechaHora.format(fecha);
        
        return fechaFin;
    }        
    
    /**
     * Formatea un monto #.###,##
     * @param numero el monto a formatear
     * @return Retorna el monto con formato #.###,##
    */    
    public static String FormatearNumero(String numero) {
        
        DecimalFormat df;
        DecimalFormatSymbols simb;
        String numeroConvertido;
        
        simb = new DecimalFormatSymbols();
        simb.setDecimalSeparator(',');
        simb.setGroupingSeparator('.');
        df = new DecimalFormat("#,###.##", simb);
        
        numeroConvertido = df.format(Double.valueOf(numero));
        
        if(!numeroConvertido.contains(",")){
            numeroConvertido = numeroConvertido + ",00";
        }
        return numeroConvertido;
        
    }
    
    /**
     * Formatea un monto 6 decimales #.###,######
     * @param numero el monto a formatear
     * @return Retorna el monto con formato #.###,######
    */    
    public static String FormatearNumeroPetro(String numero) {
        
        DecimalFormat df;
        DecimalFormatSymbols simb;
        String numeroConvertido;
        
        simb = new DecimalFormatSymbols();
        simb.setDecimalSeparator(',');
        simb.setGroupingSeparator('.');
        df = new DecimalFormat("0.000000", simb);
        
        numeroConvertido = df.format(Double.valueOf(numero));
        
        if(!numeroConvertido.contains(",")){
            numeroConvertido = numeroConvertido + ",000000";
        }
        return numeroConvertido;
        
    }
    
    /**
     * Formatea un monto ###.##
     * @param numero el monto a formatear
     * @return Retorna el monto con formato #.##
    */    
    public static String FormatearNumeroToBD(String numero) {                
        
        String numeroNuevo;
        numeroNuevo = numero.replace(".", "");
        numeroNuevo = numeroNuevo.replace(",", ".");
       
        return numeroNuevo;
        
    }
    
    /**
     * Lista los meses del año 
     * @return Retorna los meses del año
    */    
    public static Map<Integer, String> mesesAno() {   
        
        Locale español = new Locale("es");        
        Calendar mes = Calendar.getInstance(español);        
        
        Map<String, Integer> meses = mes.getDisplayNames(Calendar.MONTH, Calendar.LONG, español);                         
        
        Map<Integer, String> mesesOrdenados = new HashMap();
        
        meses = sortByValue(meses);
        
        for (Map.Entry<String, Integer> entry : meses.entrySet()) {            
            mesesOrdenados.put(entry.getValue(),entry.getKey().toUpperCase() );             
        }                  
        
        return mesesOrdenados;
    }    
        
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet()
              .stream()
              .sorted(Map.Entry.comparingByValue(/*Collections.reverseOrder()*/))
              .collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (e1, e2) -> e1, 
                LinkedHashMap::new
              ));
    }
}
