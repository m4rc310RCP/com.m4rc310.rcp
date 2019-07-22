/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.utils;

import java.util.Collection;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRRewindableDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author marcelo
 */
public class SubReportUtils {
    
//    reports.utils.SubReportUtils.getSubReportFor
    public static JasperReport getSubReportFor(String subReport){
        return ReportUtils.getReport(subReport);
    }
    
    public static JRRewindableDataSource getJRBeanDataSource(Collection<?> listValue){
        try {
            return new JRBeanCollectionDataSource(listValue);
        } catch (Exception e) {
            return new JREmptyDataSource();
        }
    }
    
}
