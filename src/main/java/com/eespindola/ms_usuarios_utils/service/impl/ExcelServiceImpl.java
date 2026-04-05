package com.eespindola.ms_usuarios_utils.service.impl;

import com.eespindola.ms_usuarios_utils.configuration.AppProperties;
import com.eespindola.ms_usuarios_utils.dao.UsuarioDao;
import com.eespindola.ms_usuarios_utils.exception.impl.Error500;
import com.eespindola.ms_usuarios_utils.model.Result;
import com.eespindola.ms_usuarios_utils.model.Usuario;
import com.eespindola.ms_usuarios_utils.service.ArchivoService;
import com.eespindola.ms_usuarios_utils.utils.ResultFactory;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service("excelServiceImpl")
public class ExcelServiceImpl implements ArchivoService {
  private static final Logger LOG = LoggerFactory.getLogger(ExcelServiceImpl.class);

  private final UsuarioDao usuarioDaoJdbc;
  private final AppProperties properties;

  public ExcelServiceImpl(
          @Qualifier("usuariosDaoJdbcImpl") UsuarioDao usuarioDaoJdbcImpl,
          AppProperties appProperties
  ) {
    this.usuarioDaoJdbc = usuarioDaoJdbcImpl;
    this.properties = appProperties;
  }

  @Override
  public Result<Void> crearArchivo() {
    try (FileOutputStream fileOutput = new FileOutputStream(createFile());
         Workbook workbook = new XSSFWorkbook()) {
      Sheet sheet = workbook.createSheet("Usuarios");
      crearHeader(workbook, sheet);
      crearFilas(workbook, sheet);
      ajustarColumnas(sheet);
      workbook.write(fileOutput);
      return ResultFactory.success();
    }
    catch (Throwable e) {
      LOG.error("Incidencia al crear archivo - excel");
      throw new Error500(List.of("Incidencia al crearArchivo - excel"), e);
    }
  }

  private void ajustarColumnas(Sheet sheet) {
    Class<Usuario> clazz = Usuario.class;
    Field[] properties = clazz.getDeclaredFields();

    for (int i = 0; i < properties.length; i++) {
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

    Class<Usuario> clazz = Usuario.class;
    Field[] properties = clazz.getDeclaredFields();

    int i = 0;
    for (Field propertie : properties) {
      Cell cell = row.createCell(i++);
      cell.setCellValue(propertie.getName());
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
    List<Usuario> usuarios = consultaUsuarios();

    for (Usuario usuario : usuarios) {
      Row row = sheet.createRow(rowNum++);
//      row.createCell(0).setCellValue(usuario.getIdUsuario());
      row.createCell(1).setCellValue(usuario.getFolioId());
      row.createCell(2).setCellValue(usuario.getNombre());
      row.createCell(3).setCellValue(usuario.getApellidoPaterno());
      row.createCell(4).setCellValue(usuario.getApellidoMaterno());
      row.createCell(5).setCellValue(usuario.getFechaNacimiento());
      row.createCell(6).setCellValue(usuario.getUsername());
      row.createCell(7).setCellValue(usuario.getEmail());
      row.createCell(8).setCellValue(usuario.getPassword());
      row.createCell(9).setCellValue(usuario.getStatus());
    }
  }

  private File createFile() {
    String fileName = String.format(properties.localExcelNamePattern(), getFechaHora("yyyy-MM-dd"));
    return new File(Paths.get(properties.localPath(), fileName).toString());
  }

  private String getFechaHora(String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDate.now().format(formatter);
  }

  private List<Usuario> consultaUsuarios() {
    return usuarioDaoJdbc.consultarUsuarios();
  }

}
