package py.edu.facitec.ventas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import py.edu.facitec.ventas.service.ReporteClienteService;

@RestController
@RequiredArgsConstructor
public class ReporteClienteController {

    private final ReporteClienteService reporteClienteService;

    @GetMapping("/api/reportes/cliente")
    public ResponseEntity<byte[]> generarReporteClientes() {
        try {
            byte[] pdf = reporteClienteService.generarReporteClientes();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cliente.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
