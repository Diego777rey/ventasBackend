package py.edu.facitec.ventas.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import py.edu.facitec.ventas.entity.Cliente;
import py.edu.facitec.ventas.entity.Vendedor;
import py.edu.facitec.ventas.repository.ClienteRepository;
import py.edu.facitec.ventas.repository.VendedorRepository;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporteVendedorService {

    private final VendedorRepository vendedorRepository;

    public ReporteVendedorService(VendedorRepository vendedorRepository) {
        this.vendedorRepository = vendedorRepository;
    }

    public byte[] generarReporteVendedores(String nombre, String fechaInicio, String fechaFin) throws Exception {
        //Obtener datos filtrados de la base
        List<Vendedor> vendedores = vendedorRepository.findByFiltros(nombre, fechaInicio, fechaFin);

        //Crear DataSource para Jasper
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vendedores);

        //Parámetros opcionales del reporte
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("NOMBRE_FILTRO", nombre != null ? nombre : "Todos");
        parametros.put("FECHA_INICIO", fechaInicio != null ? fechaInicio : "-");
        parametros.put("FECHA_FIN", fechaFin != null ? fechaFin : "-");

        //Cargar archivo jrxml
        InputStream reporteStream = getClass().getResourceAsStream("/reportes/vendedor.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reporteStream);

        //Llenar el reporte con datos
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        //Exportar a PDF y devolver bytes
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
