/*
 * Generated by JasperReports - 22/03/05 13:34
 */
import com.develog.utils.report.engine.*;
import com.develog.utils.report.engine.fill.*;

import java.util.*;
import java.math.*;
import java.text.*;
import java.io.*;
import java.net.*;


/**
 *
 */
public class untitled_report_1 extends JRCalculator
{


    /**
     *
     */
    private JRFillParameter parameter_REPORT_CONNECTION = null;
    private JRFillParameter parameter_REPORT_PARAMETERS_MAP = null;
    private JRFillParameter parameter_ST_ID = null;
    private JRFillParameter parameter_JEP_DATE = null;
    private JRFillParameter parameter_REPORT_DATA_SOURCE = null;
    private JRFillParameter parameter_REPORT_SCRIPTLET = null;

    private JRFillField field_ST_ID = null;
    private JRFillField field_JEP_DATE = null;
    private JRFillField field_TVA_ID = null;
    private JRFillField field_AR_UNIT = null;
    private JRFillField field_AR_DESIGN = null;
    private JRFillField field_TVA_TX = null;
    private JRFillField field_AR_CODE = null;
    private JRFillField field_JEP_PRIXUNITAIRE = null;
    private JRFillField field_JEP_QT = null;

    private JRFillVariable variable_PAGE_NUMBER = null;
    private JRFillVariable variable_COLUMN_NUMBER = null;
    private JRFillVariable variable_REPORT_COUNT = null;
    private JRFillVariable variable_PAGE_COUNT = null;
    private JRFillVariable variable_COLUMN_COUNT = null;
    private JRFillVariable variable_totalHT = null;
    private JRFillVariable variable_totalTVA = null;
    private JRFillVariable variable_totalTTC = null;


    /**
     *
     */
    public void customizedInit(
        Map pm,
        Map fm,
        Map vm
        ) throws JRException
    {
        parameter_REPORT_CONNECTION = (JRFillParameter)parsm.get("REPORT_CONNECTION");
        parameter_REPORT_PARAMETERS_MAP = (JRFillParameter)parsm.get("REPORT_PARAMETERS_MAP");
        parameter_ST_ID = (JRFillParameter)parsm.get("ST_ID");
        parameter_JEP_DATE = (JRFillParameter)parsm.get("JEP_DATE");
        parameter_REPORT_DATA_SOURCE = (JRFillParameter)parsm.get("REPORT_DATA_SOURCE");
        parameter_REPORT_SCRIPTLET = (JRFillParameter)parsm.get("REPORT_SCRIPTLET");

        field_ST_ID = (JRFillField)fldsm.get("ST_ID");
        field_JEP_DATE = (JRFillField)fldsm.get("JEP_DATE");
        field_TVA_ID = (JRFillField)fldsm.get("TVA_ID");
        field_AR_UNIT = (JRFillField)fldsm.get("AR_UNIT");
        field_AR_DESIGN = (JRFillField)fldsm.get("AR_DESIGN");
        field_TVA_TX = (JRFillField)fldsm.get("TVA_TX");
        field_AR_CODE = (JRFillField)fldsm.get("AR_CODE");
        field_JEP_PRIXUNITAIRE = (JRFillField)fldsm.get("JEP_PRIXUNITAIRE");
        field_JEP_QT = (JRFillField)fldsm.get("JEP_QT");

        variable_PAGE_NUMBER = (JRFillVariable)varsm.get("PAGE_NUMBER");
        variable_COLUMN_NUMBER = (JRFillVariable)varsm.get("COLUMN_NUMBER");
        variable_REPORT_COUNT = (JRFillVariable)varsm.get("REPORT_COUNT");
        variable_PAGE_COUNT = (JRFillVariable)varsm.get("PAGE_COUNT");
        variable_COLUMN_COUNT = (JRFillVariable)varsm.get("COLUMN_COUNT");
        variable_totalHT = (JRFillVariable)varsm.get("totalHT");
        variable_totalTVA = (JRFillVariable)varsm.get("totalTVA");
        variable_totalTTC = (JRFillVariable)varsm.get("totalTTC");
    }


    /**
     * Test method
     */
    public static void helloJasper()
    {
        System.out.println("------------------------------");
        System.out.println(" Hello, Jasper!...");
        System.out.println("------------------------------");
    }


    /**
     *
     */
    public Object evaluate(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 956 : // textField_10
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_totalTVA.getValue()) + " �");
                break;
            }
            case 955 : // textField_9
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_totalHT.getValue()) + " �");
                break;
            }
            case 938 : // variableInitialValue_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 961 : // subreport_2
            {
                value = (java.lang.String)("reports/tva3Report.jasper");
                break;
            }
            case 953 : // textField_7
            {
                value = (java.lang.String)(new BigDecimal(((java.math.BigDecimal)field_JEP_PRIXUNITAIRE.getValue()).floatValue()).multiply(new BigDecimal(((java.math.BigDecimal)field_JEP_QT.getValue()).floatValue())).divide(new BigDecimal (1),2,java.math.BigDecimal.ROUND_HALF_EVEN ) + " �");
                break;
            }
            case 949 : // textField_3
            {
                value = (java.lang.String)(((java.math.BigDecimal)field_JEP_QT.getValue()).divide(new BigDecimal(1),2,java.math.BigDecimal.ROUND_HALF_EVEN ) + " " + ((java.lang.String)field_AR_UNIT.getValue()));
                break;
            }
            case 937 : // variableInitialValue_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 951 : // textField_5
            {
                value = (java.lang.String)(((java.lang.String)field_AR_CODE.getValue()));
                break;
            }
            case 934 : // variableInitialValue_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 940 : // parameterDefaultValue_ST_ID
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 945 : // connection_1
            {
                value = (java.sql.Connection)(((java.sql.Connection)parameter_REPORT_CONNECTION.getValue()));
                break;
            }
            case 944 : // subreportParameter_ST_ID_1
            {
                value = (java.lang.Object)(((java.lang.Integer)parameter_ST_ID.getValue()));
                break;
            }
            case 958 : // subreportParameter_JEP_DATE_2
            {
                value = (java.lang.Object)(((java.util.Date)field_JEP_DATE.getValue())+"");
                break;
            }
            case 932 : // variableInitialValue_COLUMN_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 957 : // textField_11
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_totalTTC.getValue()) + " �");
                break;
            }
            case 960 : // connection_2
            {
                value = (java.sql.Connection)(((java.sql.Connection)parameter_REPORT_CONNECTION.getValue()));
                break;
            }
            case 959 : // subreportParameter_ST_ID_3
            {
                value = (java.lang.Object)(((java.lang.Integer)parameter_ST_ID.getValue()));
                break;
            }
            case 948 : // textField_2
            {
                value = (java.lang.String)("Journal du " + ((java.util.Date)field_JEP_DATE.getValue()).toString().substring(8,10) + "/" + ((java.util.Date)field_JEP_DATE.getValue()).toString().substring(5,7) + "/" + ((java.util.Date)field_JEP_DATE.getValue()).toString().substring(0,4));
                break;
            }
            case 941 : // variable_totalHT
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_JEP_PRIXUNITAIRE.getValue()).multiply(((java.math.BigDecimal)field_JEP_QT.getValue())).divide(new BigDecimal(1),2,java.math.BigDecimal.ROUND_HALF_EVEN ));
                break;
            }
            case 933 : // variable_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 952 : // textField_6
            {
                value = (java.lang.String)(((java.lang.String)field_AR_DESIGN.getValue()));
                break;
            }
            case 946 : // subreport_1
            {
                value = (java.lang.String)("reports/nomstock.jasper");
                break;
            }
            case 935 : // variable_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 954 : // textField_8
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getValue()));
                break;
            }
            case 947 : // textField_1
            {
                value = (java.lang.String)("JOURNAL DES ECARTS POSITIFS");
                break;
            }
            case 931 : // variableInitialValue_PAGE_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 939 : // parameterDefaultValue_JEP_DATE
            {
                value = (java.lang.String)("2005-03-21");
                break;
            }
            case 936 : // variableInitialValue_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 943 : // variable_totalTTC
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)variable_totalHT.getValue()).add(((java.math.BigDecimal)variable_totalTVA.getValue())).divide(new BigDecimal(1),2,java.math.BigDecimal.ROUND_HALF_EVEN ));
                break;
            }
            case 942 : // variable_totalTVA
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_JEP_PRIXUNITAIRE.getValue()).multiply(((java.math.BigDecimal)field_JEP_QT.getValue())).multiply(new BigDecimal(((java.math.BigDecimal)field_TVA_TX.getValue()).floatValue()/100)).divide(new BigDecimal(1),2,java.math.BigDecimal.ROUND_HALF_EVEN ));
                break;
            }
            case 950 : // textField_4
            {
                value = (java.lang.String)(((java.math.BigDecimal)field_JEP_PRIXUNITAIRE.getValue()).divide(new BigDecimal(1),2,java.math.BigDecimal.ROUND_HALF_EVEN ) + " �");
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


    /**
     *
     */
    public Object evaluateOld(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 956 : // textField_10
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_totalTVA.getOldValue()) + " �");
                break;
            }
            case 955 : // textField_9
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_totalHT.getOldValue()) + " �");
                break;
            }
            case 938 : // variableInitialValue_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 961 : // subreport_2
            {
                value = (java.lang.String)("reports/tva3Report.jasper");
                break;
            }
            case 953 : // textField_7
            {
                value = (java.lang.String)(new BigDecimal(((java.math.BigDecimal)field_JEP_PRIXUNITAIRE.getOldValue()).floatValue()).multiply(new BigDecimal(((java.math.BigDecimal)field_JEP_QT.getOldValue()).floatValue())).divide(new BigDecimal (1),2,java.math.BigDecimal.ROUND_HALF_EVEN ) + " �");
                break;
            }
            case 949 : // textField_3
            {
                value = (java.lang.String)(((java.math.BigDecimal)field_JEP_QT.getOldValue()).divide(new BigDecimal(1),2,java.math.BigDecimal.ROUND_HALF_EVEN ) + " " + ((java.lang.String)field_AR_UNIT.getOldValue()));
                break;
            }
            case 937 : // variableInitialValue_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 951 : // textField_5
            {
                value = (java.lang.String)(((java.lang.String)field_AR_CODE.getOldValue()));
                break;
            }
            case 934 : // variableInitialValue_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 940 : // parameterDefaultValue_ST_ID
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 945 : // connection_1
            {
                value = (java.sql.Connection)(((java.sql.Connection)parameter_REPORT_CONNECTION.getValue()));
                break;
            }
            case 944 : // subreportParameter_ST_ID_1
            {
                value = (java.lang.Object)(((java.lang.Integer)parameter_ST_ID.getValue()));
                break;
            }
            case 958 : // subreportParameter_JEP_DATE_2
            {
                value = (java.lang.Object)(((java.util.Date)field_JEP_DATE.getOldValue())+"");
                break;
            }
            case 932 : // variableInitialValue_COLUMN_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 957 : // textField_11
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_totalTTC.getOldValue()) + " �");
                break;
            }
            case 960 : // connection_2
            {
                value = (java.sql.Connection)(((java.sql.Connection)parameter_REPORT_CONNECTION.getValue()));
                break;
            }
            case 959 : // subreportParameter_ST_ID_3
            {
                value = (java.lang.Object)(((java.lang.Integer)parameter_ST_ID.getValue()));
                break;
            }
            case 948 : // textField_2
            {
                value = (java.lang.String)("Journal du " + ((java.util.Date)field_JEP_DATE.getOldValue()).toString().substring(8,10) + "/" + ((java.util.Date)field_JEP_DATE.getOldValue()).toString().substring(5,7) + "/" + ((java.util.Date)field_JEP_DATE.getOldValue()).toString().substring(0,4));
                break;
            }
            case 941 : // variable_totalHT
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_JEP_PRIXUNITAIRE.getOldValue()).multiply(((java.math.BigDecimal)field_JEP_QT.getOldValue())).divide(new BigDecimal(1),2,java.math.BigDecimal.ROUND_HALF_EVEN ));
                break;
            }
            case 933 : // variable_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 952 : // textField_6
            {
                value = (java.lang.String)(((java.lang.String)field_AR_DESIGN.getOldValue()));
                break;
            }
            case 946 : // subreport_1
            {
                value = (java.lang.String)("reports/nomstock.jasper");
                break;
            }
            case 935 : // variable_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 954 : // textField_8
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue()));
                break;
            }
            case 947 : // textField_1
            {
                value = (java.lang.String)("JOURNAL DES ECARTS POSITIFS");
                break;
            }
            case 931 : // variableInitialValue_PAGE_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 939 : // parameterDefaultValue_JEP_DATE
            {
                value = (java.lang.String)("2005-03-21");
                break;
            }
            case 936 : // variableInitialValue_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 943 : // variable_totalTTC
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)variable_totalHT.getOldValue()).add(((java.math.BigDecimal)variable_totalTVA.getOldValue())).divide(new BigDecimal(1),2,java.math.BigDecimal.ROUND_HALF_EVEN ));
                break;
            }
            case 942 : // variable_totalTVA
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_JEP_PRIXUNITAIRE.getOldValue()).multiply(((java.math.BigDecimal)field_JEP_QT.getOldValue())).multiply(new BigDecimal(((java.math.BigDecimal)field_TVA_TX.getOldValue()).floatValue()/100)).divide(new BigDecimal(1),2,java.math.BigDecimal.ROUND_HALF_EVEN ));
                break;
            }
            case 950 : // textField_4
            {
                value = (java.lang.String)(((java.math.BigDecimal)field_JEP_PRIXUNITAIRE.getOldValue()).divide(new BigDecimal(1),2,java.math.BigDecimal.ROUND_HALF_EVEN ) + " �");
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


    /**
     *
     */
    public Object evaluateEstimated(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 956 : // textField_10
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_totalTVA.getEstimatedValue()) + " �");
                break;
            }
            case 955 : // textField_9
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_totalHT.getEstimatedValue()) + " �");
                break;
            }
            case 938 : // variableInitialValue_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 961 : // subreport_2
            {
                value = (java.lang.String)("reports/tva3Report.jasper");
                break;
            }
            case 953 : // textField_7
            {
                value = (java.lang.String)(new BigDecimal(((java.math.BigDecimal)field_JEP_PRIXUNITAIRE.getValue()).floatValue()).multiply(new BigDecimal(((java.math.BigDecimal)field_JEP_QT.getValue()).floatValue())).divide(new BigDecimal (1),2,java.math.BigDecimal.ROUND_HALF_EVEN ) + " �");
                break;
            }
            case 949 : // textField_3
            {
                value = (java.lang.String)(((java.math.BigDecimal)field_JEP_QT.getValue()).divide(new BigDecimal(1),2,java.math.BigDecimal.ROUND_HALF_EVEN ) + " " + ((java.lang.String)field_AR_UNIT.getValue()));
                break;
            }
            case 937 : // variableInitialValue_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 951 : // textField_5
            {
                value = (java.lang.String)(((java.lang.String)field_AR_CODE.getValue()));
                break;
            }
            case 934 : // variableInitialValue_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 940 : // parameterDefaultValue_ST_ID
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 945 : // connection_1
            {
                value = (java.sql.Connection)(((java.sql.Connection)parameter_REPORT_CONNECTION.getValue()));
                break;
            }
            case 944 : // subreportParameter_ST_ID_1
            {
                value = (java.lang.Object)(((java.lang.Integer)parameter_ST_ID.getValue()));
                break;
            }
            case 958 : // subreportParameter_JEP_DATE_2
            {
                value = (java.lang.Object)(((java.util.Date)field_JEP_DATE.getValue())+"");
                break;
            }
            case 932 : // variableInitialValue_COLUMN_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 957 : // textField_11
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_totalTTC.getEstimatedValue()) + " �");
                break;
            }
            case 960 : // connection_2
            {
                value = (java.sql.Connection)(((java.sql.Connection)parameter_REPORT_CONNECTION.getValue()));
                break;
            }
            case 959 : // subreportParameter_ST_ID_3
            {
                value = (java.lang.Object)(((java.lang.Integer)parameter_ST_ID.getValue()));
                break;
            }
            case 948 : // textField_2
            {
                value = (java.lang.String)("Journal du " + ((java.util.Date)field_JEP_DATE.getValue()).toString().substring(8,10) + "/" + ((java.util.Date)field_JEP_DATE.getValue()).toString().substring(5,7) + "/" + ((java.util.Date)field_JEP_DATE.getValue()).toString().substring(0,4));
                break;
            }
            case 941 : // variable_totalHT
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_JEP_PRIXUNITAIRE.getValue()).multiply(((java.math.BigDecimal)field_JEP_QT.getValue())).divide(new BigDecimal(1),2,java.math.BigDecimal.ROUND_HALF_EVEN ));
                break;
            }
            case 933 : // variable_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 952 : // textField_6
            {
                value = (java.lang.String)(((java.lang.String)field_AR_DESIGN.getValue()));
                break;
            }
            case 946 : // subreport_1
            {
                value = (java.lang.String)("reports/nomstock.jasper");
                break;
            }
            case 935 : // variable_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 954 : // textField_8
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue()));
                break;
            }
            case 947 : // textField_1
            {
                value = (java.lang.String)("JOURNAL DES ECARTS POSITIFS");
                break;
            }
            case 931 : // variableInitialValue_PAGE_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 939 : // parameterDefaultValue_JEP_DATE
            {
                value = (java.lang.String)("2005-03-21");
                break;
            }
            case 936 : // variableInitialValue_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 943 : // variable_totalTTC
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)variable_totalHT.getEstimatedValue()).add(((java.math.BigDecimal)variable_totalTVA.getEstimatedValue())).divide(new BigDecimal(1),2,java.math.BigDecimal.ROUND_HALF_EVEN ));
                break;
            }
            case 942 : // variable_totalTVA
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_JEP_PRIXUNITAIRE.getValue()).multiply(((java.math.BigDecimal)field_JEP_QT.getValue())).multiply(new BigDecimal(((java.math.BigDecimal)field_TVA_TX.getValue()).floatValue()/100)).divide(new BigDecimal(1),2,java.math.BigDecimal.ROUND_HALF_EVEN ));
                break;
            }
            case 950 : // textField_4
            {
                value = (java.lang.String)(((java.math.BigDecimal)field_JEP_PRIXUNITAIRE.getValue()).divide(new BigDecimal(1),2,java.math.BigDecimal.ROUND_HALF_EVEN ) + " �");
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


}
