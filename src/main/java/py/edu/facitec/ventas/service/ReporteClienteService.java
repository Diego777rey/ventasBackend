package py.edu.facitec.ventas.service;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import py.edu.facitec.ventas.entity.Cliente;
import py.edu.facitec.ventas.repository.ClienteRepository;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReporteClienteService {

    private final ClienteRepository clienteRepository;

    public byte[] generarReporteClientes() throws JRException {
        List<Cliente> clientes = clienteRepository.findAll();

        // Cargar la plantilla .jrxml desde resources
        InputStream reporteStream = getClass().getResourceAsStream("/reportes/cliente.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(reporteStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(clientes);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("titulo", "Listado de Clientes");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        // Exportar a PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
