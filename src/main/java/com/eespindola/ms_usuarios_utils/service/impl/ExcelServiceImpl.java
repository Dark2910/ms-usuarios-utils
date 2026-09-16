package com.eespindola.ms_usuarios_utils.service.impl;

import com.eespindola.ms_usuarios_utils.configuration.AppProperties;
import com.eespindola.ms_usuarios_utils.dao.UsuarioDao;
import com.eespindola.ms_usuarios_utils.exception.impl.Error500;
import com.eespindola.ms_usuarios_utils.model.Result;
import com.eespindola.ms_usuarios_utils.model.UsuarioExportDto;
import com.eespindola.ms_usuarios_utils.utils.ResultFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.List;

@Service("excelServiceImpl")
public class ExcelServiceImpl extends AbstractDocumentService {

  public ExcelServiceImpl(@Qualifier("usuariosDaoJdbcImpl") UsuarioDao usuarioDaoJdbcImpl,
                          AppProperties appProperties) {
    super(usuarioDaoJdbcImpl, appProperties);
  }

  @Override
  public Result<Void> crearArchivo() {
    LOG.info("Creando archivo Excel");
    try (FileOutputStream fileOutput = new FileOutputStream(createFile(properties.localExcelNamePattern()));
         Workbook workbook = new XSSFWorkbook()) {
      Sheet sheet = workbook.createSheet("Usuarios");
      crearHeader(workbook, sheet);
      crearFilas(workbook, sheet);
      ajustarColumnas(sheet);
      workbook.write(fileOutput);
      LOG.info("Archivo Excel creado exitosamente");
      return ResultFactory.success();
    }
    catch (Throwable e) {
      LOG.error("Error al crear archivo Excel", e);
      throw new Error500(List.of("Incidencia al crearArchivo - excel"), e);
    }
  }

  private void ajustarColumnas(Sheet sheet) {
    Class<UsuarioExportDto> clazz = UsuarioExportDto.class;
    Field[] fields = clazz.getDeclaredFields();

    for (int i = 0; i < fields.length; i++) {
      sheet.autoSizeColumn(i);
      sheet.setColumnWidth(i, (sheet.getColumnWidth(i) + 1000));
    }
  }

  private void crearHeader(Workbook workbook, Sheet sheet) {
    Font font = workbook.createFont();
    font.setFontName("Arial");
    font.setFontHeightInPoints((short) 12);
    font.setBold(true);

    CellStyle style = workbook.createCellStyle();
    style.setBorderBottom(BorderStyle.DASHED);
    style.setBorderRight(BorderStyle.DASHED);
    style.setBorderLeft(BorderStyle.DASHED);
    style.setAlignment(HorizontalAlignment.CENTER);
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    style.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setFont(font);

    Row row = sheet.createRow(0);

    Class<UsuarioExportDto> clazz = UsuarioExportDto.class;
    Field[] fields = clazz.getDeclaredFields();

    int i = 0;
    for (Field field : fields) {
      Cell cell = row.createCell(i++);
      cell.setCellValue(field.getName());
      cell.setCellStyle(style);
    }
  }

  private void crearFilas(Workbook workbook, Sheet sheet) {
    Font font = workbook.createFont();
    font.setFontName("Arial");
    font.setFontHeightInPoints((short) 10);
    font.setBold(false);

    CellStyle style = workbook.createCellStyle();
    style.setAlignment(HorizontalAlignment.LEFT);
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    style.setFont(font);

    int rowNum = 1;
    List<UsuarioExportDto> usuarios = consultarUsuariosExport();

    for (UsuarioExportDto usuario : usuarios) {
      Row row = sheet.createRow(rowNum++);
      row.createCell(0).setCellValue(usuario.getIdUsuario() != null ? usuario.getIdUsuario().toString() : "");
      row.createCell(1).setCellValue(usuario.getFolioId() != null ? usuario.getFolioId() : "");
      row.createCell(2).setCellValue(usuario.getNombre() != null ? usuario.getNombre() : "");
      row.createCell(3).setCellValue(usuario.getApellidoPaterno() != null ? usuario.getApellidoPaterno() : "");
      row.createCell(4).setCellValue(usuario.getApellidoMaterno() != null ? usuario.getApellidoMaterno() : "");
      row.createCell(5).setCellValue(usuario.getFechaNacimiento() != null ? usuario.getFechaNacimiento() : "");
      row.createCell(6).setCellValue(usuario.getUsername() != null ? usuario.getUsername() : "");
      row.createCell(7).setCellValue(usuario.getEmail() != null ? usuario.getEmail() : "");
      row.createCell(8).setCellValue(usuario.getStatus() != null ? usuario.getStatus() : "");
    }
  }

}
