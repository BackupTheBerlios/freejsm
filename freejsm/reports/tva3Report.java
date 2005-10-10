/*
 * Generated by JasperReports - 22/03/05 13:32
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
public class tva3Report extends JRCalculator
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

    private JRFillField field_AR_ID = null;
    private JRFillField field_TVA_ID = null;
    private JRFillField field_JEP_PRIXUNITAIRE = null;
    private JRFillField field_TVA_TX = null;

    private JRFillVariable variable_PAGE_NUMBER = null;
    private JRFillVariable variable_COLUMN_NUMBER = null;
    private JRFillVariable variable_REPORT_COUNT = null;
    private JRFillVariable variable_PAGE_COUNT = null;
    private JRFillVariable variable_COLUMN_COUNT = null;
    private JRFillVariable variable_base = null;
    private JRFillVariable variable_tvaTotal = null;
    private JRFillVariable variable_totalTTC = null;
    private JRFillVariable variable_tva_COUNT = null;


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

        field_AR_ID = (JRFillField)fldsm.get("AR_ID");
        field_TVA_ID = (JRFillField)fldsm.get("TVA_ID");
        field_JEP_PRIXUNITAIRE = (JRFillField)fldsm.get("JEP_PRIXUNITAIRE");
        field_TVA_TX = (JRFillField)fldsm.get("TVA_TX");

        variable_PAGE_NUMBER = (JRFillVariable)varsm.get("PAGE_NUMBER");
        variable_COLUMN_NUMBER = (JRFillVariable)varsm.get("COLUMN_NUMBER");
        variable_REPORT_COUNT = (JRFillVariable)varsm.get("REPORT_COUNT");
        variable_PAGE_COUNT = (JRFillVariable)varsm.get("PAGE_COUNT");
        variable_COLUMN_COUNT = (JRFillVariable)varsm.get("COLUMN_COUNT");
        variable_base = (JRFillVariable)varsm.get("base");
        variable_tvaTotal = (JRFillVariable)varsm.get("tvaTotal");
        variable_totalTTC = (JRFillVariable)varsm.get("totalTTC");
        variable_tva_COUNT = (JRFillVariable)varsm.get("tva_COUNT");
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
            case 765 : // variableInitialValue_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 760 : // variableInitialValue_PAGE_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 772 : // variable_totalTTC
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)variable_tvaTotal.getValue()).add(((java.math.BigDecimal)variable_base.getValue())).divide(new java.math.BigDecimal(1), 2, java.math.BigDecimal.ROUND_HALF_UP));
                break;
            }
            case 762 : // variable_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 776 : // textField_3
            {
                value = (java.lang.String)(((java.math.BigDecimal)field_TVA_TX.getValue()).divide(new java.math.BigDecimal(1), 2, java.math.BigDecimal.ROUND_HALF_UP) + "%");
                break;
            }
            case 778 : // textField_5
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_totalTTC.getValue()) + " �");
                break;
            }
            case 777 : // textField_4
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_base.getValue()).divide(new java.math.BigDecimal(1), 2, java.math.BigDecimal.ROUND_HALF_UP) +" �");
                break;
            }
            case 771 : // variable_tvaTotal
            {
                value = (java.math.BigDecimal)((((java.math.BigDecimal)variable_base.getValue())).multiply(((java.math.BigDecimal)field_TVA_TX.getValue()).multiply(new BigDecimal(0.01))).divide(new java.math.BigDecimal(1), 2, java.math.BigDecimal.ROUND_HALF_UP));
                break;
            }
            case 761 : // variableInitialValue_COLUMN_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 770 : // variable_base
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_JEP_PRIXUNITAIRE.getValue()));
                break;
            }
            case 766 : // variableInitialValue_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 769 : // parameterDefaultValue_ST_ID
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 780 : // variableInitialValue_tva_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 773 : // group_tva
            {
                value = (java.lang.Object)(((java.lang.Integer)field_TVA_ID.getValue()));
                break;
            }
            case 768 : // parameterDefaultValue_JEP_DATE
            {
                value = (java.lang.String)(new String("2005-03-21"));
                break;
            }
            case 764 : // variable_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 774 : // textField_1
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_tvaTotal.getValue()) + " �");
                break;
            }
            case 779 : // variable_tva_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 767 : // variableInitialValue_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 775 : // textField_2
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_TVA_ID.getValue()));
                break;
            }
            case 763 : // variableInitialValue_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
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
            case 765 : // variableInitialValue_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 760 : // variableInitialValue_PAGE_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 772 : // variable_totalTTC
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)variable_tvaTotal.getOldValue()).add(((java.math.BigDecimal)variable_base.getOldValue())).divide(new java.math.BigDecimal(1), 2, java.math.BigDecimal.ROUND_HALF_UP));
                break;
            }
            case 762 : // variable_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 776 : // textField_3
            {
                value = (java.lang.String)(((java.math.BigDecimal)field_TVA_TX.getOldValue()).divide(new java.math.BigDecimal(1), 2, java.math.BigDecimal.ROUND_HALF_UP) + "%");
                break;
            }
            case 778 : // textField_5
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_totalTTC.getOldValue()) + " �");
                break;
            }
            case 777 : // textField_4
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_base.getOldValue()).divide(new java.math.BigDecimal(1), 2, java.math.BigDecimal.ROUND_HALF_UP) +" �");
                break;
            }
            case 771 : // variable_tvaTotal
            {
                value = (java.math.BigDecimal)((((java.math.BigDecimal)variable_base.getOldValue())).multiply(((java.math.BigDecimal)field_TVA_TX.getOldValue()).multiply(new BigDecimal(0.01))).divide(new java.math.BigDecimal(1), 2, java.math.BigDecimal.ROUND_HALF_UP));
                break;
            }
            case 761 : // variableInitialValue_COLUMN_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 770 : // variable_base
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_JEP_PRIXUNITAIRE.getOldValue()));
                break;
            }
            case 766 : // variableInitialValue_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 769 : // parameterDefaultValue_ST_ID
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 780 : // variableInitialValue_tva_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 773 : // group_tva
            {
                value = (java.lang.Object)(((java.lang.Integer)field_TVA_ID.getOldValue()));
                break;
            }
            case 768 : // parameterDefaultValue_JEP_DATE
            {
                value = (java.lang.String)(new String("2005-03-21"));
                break;
            }
            case 764 : // variable_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 774 : // textField_1
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_tvaTotal.getOldValue()) + " �");
                break;
            }
            case 779 : // variable_tva_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 767 : // variableInitialValue_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 775 : // textField_2
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_TVA_ID.getOldValue()));
                break;
            }
            case 763 : // variableInitialValue_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
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
            case 765 : // variableInitialValue_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 760 : // variableInitialValue_PAGE_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 772 : // variable_totalTTC
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)variable_tvaTotal.getEstimatedValue()).add(((java.math.BigDecimal)variable_base.getEstimatedValue())).divide(new java.math.BigDecimal(1), 2, java.math.BigDecimal.ROUND_HALF_UP));
                break;
            }
            case 762 : // variable_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 776 : // textField_3
            {
                value = (java.lang.String)(((java.math.BigDecimal)field_TVA_TX.getValue()).divide(new java.math.BigDecimal(1), 2, java.math.BigDecimal.ROUND_HALF_UP) + "%");
                break;
            }
            case 778 : // textField_5
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_totalTTC.getEstimatedValue()) + " �");
                break;
            }
            case 777 : // textField_4
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_base.getEstimatedValue()).divide(new java.math.BigDecimal(1), 2, java.math.BigDecimal.ROUND_HALF_UP) +" �");
                break;
            }
            case 771 : // variable_tvaTotal
            {
                value = (java.math.BigDecimal)((((java.math.BigDecimal)variable_base.getEstimatedValue())).multiply(((java.math.BigDecimal)field_TVA_TX.getValue()).multiply(new BigDecimal(0.01))).divide(new java.math.BigDecimal(1), 2, java.math.BigDecimal.ROUND_HALF_UP));
                break;
            }
            case 761 : // variableInitialValue_COLUMN_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 770 : // variable_base
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_JEP_PRIXUNITAIRE.getValue()));
                break;
            }
            case 766 : // variableInitialValue_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 769 : // parameterDefaultValue_ST_ID
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 780 : // variableInitialValue_tva_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 773 : // group_tva
            {
                value = (java.lang.Object)(((java.lang.Integer)field_TVA_ID.getValue()));
                break;
            }
            case 768 : // parameterDefaultValue_JEP_DATE
            {
                value = (java.lang.String)(new String("2005-03-21"));
                break;
            }
            case 764 : // variable_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 774 : // textField_1
            {
                value = (java.lang.String)(((java.math.BigDecimal)variable_tvaTotal.getEstimatedValue()) + " �");
                break;
            }
            case 779 : // variable_tva_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 767 : // variableInitialValue_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 775 : // textField_2
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_TVA_ID.getValue()));
                break;
            }
            case 763 : // variableInitialValue_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


}
