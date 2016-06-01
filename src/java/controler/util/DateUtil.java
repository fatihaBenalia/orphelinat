/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.util;

/**
 *
 * @author samia
 */
public class DateUtil {
  
     
     public static java.util.Date convertUtilToSql(java.util.Date utilDate) {

        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        return sqlDate;
    }
    
}
