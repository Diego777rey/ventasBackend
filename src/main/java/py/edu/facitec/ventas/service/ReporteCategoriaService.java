package py.edu.facitec.ventas.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import py.edu.facitec.ventas.entity.Categoria;
import py.edu.facitec.ventas.repository.CategoriaRepository;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporteCategoriaService {

    private final CategoriaRepository categoriaRepository;

    public ReporteCategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public byte[] generarReporteCategorias(String descripcion) throws Exception {
        //Obtener datos filtrados de la base
        List<Categoria> categorias = categoriaRepository.findByFiltros(descripcion);

        //Crear DataSource para Jasper
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(categorias);

        //Parámetros opcionales del reporte
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("DESCRIPCION_FILTRO", descripcion != null ? descripcion : "Todos");

        //Cargar archivo jrxml
        InputStream reporteStream = getClass().getResourceAsStream("/reportes/categoria.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reporteStream);

        //Llenar el reporte con datos
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        //Exportar a PDF y devolver bytes
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
