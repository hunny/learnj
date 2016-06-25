package hh.learnj.poi.guide.read.usecase;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author huzexiong
 * 
 */
public class ReadExcelData {
	/**
	 * 一个对象只要实现了Serilizable接口，这个对象就可以被序列化，java的这种序列化模式为开发者提供了很多便利，
	 * 我们可以不必关系具体序列化的过程，只要这个类实现了Serilizable接口，这个类的所有属性和方法都会自动序列化。
	 * 然而在实际开发过程中，我们常常会遇到这样的问题，这个类的有些属性需要序列化，而其他属性不需要被序列化，
	 * 打个比方，如果一个用户有一些敏感信息（如密码
	 * ，银行卡号等），为了安全起见，不希望在网络操作（主要涉及到序列化操作，本地序列化缓存也适用）中被传输，
	 * 这些信息对应的变量就可以加上transient关键字。换句话说，这个字段的生命周期仅存于调用者的内存中而不会写到磁盘里持久化。
	 */
	private transient Collection data = null;

	public ReadExcelData(final InputStream excelInputStream) throws IOException {
		this.data = loadFromSpreadsheet(excelInputStream);
	}

	public Collection getData() {
		return data;
	}

	private Collection loadFromSpreadsheet(final InputStream excelFile) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(excelFile);

		data = new ArrayList();
		Sheet sheet = workbook.getSheetAt(0);

		int numberOfColumns = countNonEmptyColumns(sheet);
		List rows = new ArrayList();
		List rowData = new ArrayList();

		for (Row row : sheet) {
			if (isEmpty(row)) {
				break;
			} else {
				rowData.clear();
				for (int column = 0; column < numberOfColumns; column++) {
					Cell cell = row.getCell(column);
					rowData.add(objectFrom(workbook, cell));
				}
				rows.add(rowData.toArray());
			}
		}
		return rows;
	}

	private boolean isEmpty(final Row row) {
		Cell firstCell = row.getCell(0);
		boolean rowIsEmpty = (firstCell == null) || (firstCell.getCellType() == Cell.CELL_TYPE_BLANK);
		return rowIsEmpty;
	}

	private int countNonEmptyColumns(final Sheet sheet) {
		Row firstRow = sheet.getRow(0);
		return firstEmptyCellPosition(firstRow);
	}

	private int firstEmptyCellPosition(final Row cells) {
		int columnCount = 0;
		for (Cell cell : cells) {
			if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				break;
			}
			columnCount++;
		}
		return columnCount;
	}

	private Object objectFrom(final HSSFWorkbook workbook, final Cell cell) {
		Object cellValue = null;
		if (null == cell) {
			return cellValue;
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			cellValue = cell.getRichStringCellValue().getString();
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			cellValue = getNumericCellValue(cell);
		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			cellValue = cell.getBooleanCellValue();
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			cellValue = evaluateCellFormula(workbook, cell);
		}

		return cellValue;

	}

	private Object getNumericCellValue(final Cell cell) {
		Object cellValue;
		if (DateUtil.isCellDateFormatted(cell)) {
			cellValue = new Date(cell.getDateCellValue().getTime());
		} else {
			cellValue = BigDecimal.valueOf(cell.getNumericCellValue());
		}
		return cellValue;
	}

	private Object evaluateCellFormula(final HSSFWorkbook workbook, final Cell cell) {
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		CellValue cellValue = evaluator.evaluate(cell);
		Object result = null;

		if (cellValue.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			result = cellValue.getBooleanValue();
		} else if (cellValue.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			result = cellValue.getNumberValue();
		} else if (cellValue.getCellType() == Cell.CELL_TYPE_STRING) {
			result = cellValue.getStringValue();
		}

		return result;
	}
}
