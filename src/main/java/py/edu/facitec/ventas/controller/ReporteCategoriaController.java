package py.edu.facitec.ventas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import py.edu.facitec.ventas.service.ReporteCategoriaService;

@RestController
@RequiredArgsConstructor
public class ReporteCategoriaController {

    private final ReporteCategoriaService reporteCategoriaService;

    @GetMapping("/api/reportes/categoria")
    public ResponseEntity<byte[]> generarReporteCategorias(
            @RequestParam(required = false) String descripcion) {

        try {
            // Pasamos los filtros al servicio
            byte[] pdf = reporteCategoriaService.generarReporteCategorias(descripcion);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Categorias.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);

        } catch (Exception e) {
            // Podés loguear e imprimir el error para depuración
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
