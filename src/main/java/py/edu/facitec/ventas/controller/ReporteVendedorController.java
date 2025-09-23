/*package py.edu.facitec.ventas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import py.edu.facitec.ventas.service.ReporteVendedorService;

@RestController
@RequiredArgsConstructor
public class ReporteVendedorController {

    private final ReporteVendedorService reporteVendedorService;

    @GetMapping("/api/reportes/vendedor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> generarReporteVendedores(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {

        try {
            // Pasamos los filtros al servicio
            byte[] pdf = reporteVendedorService.generarReporteVendedores(nombre, fechaInicio, fechaFin);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendedores.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);

        } catch (Exception e) {
            // Podés loguear e imprimir el error para depuración
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}*/
