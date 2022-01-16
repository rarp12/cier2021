/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.bean.importacion;

import co.stackpointer.cier.modelo.excepcion.ErrorExcel;
import co.stackpointer.cier.modelo.excepcion.ErrorGeneral;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author user
 */
public class ImpUtil {
    //Cuando es una fórmula

    public static String getValorCelda(Workbook wb, Cell cell) {
        String value = null;
        try {
            FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
            CellValue cellValue = evaluator.evaluate(cell);
            if (evaluator.evaluate(cell) == null) {
                return "";
            }
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    value = "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    break;
            }
        } catch (Exception e) {
            throw new ErrorExcel(e.getMessage());
        }
        return value;
    }

    public static String getValorCelda(Cell cell) {
        String value = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    value = "";
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    switch (cell.getCachedFormulaResultType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            value = Double.toString(cell.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_STRING:
                            value = cell.getRichStringCellValue().getString();
                            break;
                        default:
                            value = cell.getRichStringCellValue().getString();
                            break;

                    }
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                default:
            }
        } else {
            value = "";
        }
        return value;
    }
}
