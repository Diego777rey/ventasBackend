package py.edu.facitec.ventas.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.dto.InputRegistrarHorario;
import py.edu.facitec.ventas.entity.RegistrarHorario;
import py.edu.facitec.ventas.enums.Horarios;
import py.edu.facitec.ventas.service.RegistrarHorarioService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class RegistrarHorarioController {

    private final RegistrarHorarioService registrarHorarioService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public RegistrarHorarioController(RegistrarHorarioService registrarHorarioService) {
        this.registrarHorarioService = registrarHorarioService;
    }

    // -------------------- QUERIES --------------------

    @QueryMapping
    public RegistrarHorario findRegistrarHorarioById(@Argument Long registrarHorarioId) {
        return registrarHorarioService.findRegistrarHorarioById(registrarHorarioId)
                .orElseThrow(() -> new IllegalArgumentException("Registro no encontrado"));
    }

    @QueryMapping
    public List<RegistrarHorario> findAllRegistrarHorarios() {
        return registrarHorarioService.findAllRegistrarHorarios();
    }

    @QueryMapping
    public List<RegistrarHorario> findRegistrarHorariosPorVendedor(@Argument Long vendedorId) {
        return registrarHorarioService.findByVendedorId(vendedorId);
    }

    // -------------------- MUTATIONS --------------------

    @MutationMapping
    public RegistrarHorario createRegistrarHorario(@Argument("inputRegistrarHorario") InputRegistrarHorario input) {
        LocalDateTime fechaHora = LocalDateTime.parse(input.getFechaHora(), FORMATTER);
        Horarios horario = Horarios.valueOf(input.getHorarios().toUpperCase());
        return registrarHorarioService.createRegistrarHorario(fechaHora, horario, input.getVendedorId());
    }

    @MutationMapping
    public RegistrarHorario updateRegistrarHorario(@Argument Long id,
                                                   @Argument("inputRegistrarHorario") InputRegistrarHorario input) {
        LocalDateTime fechaHora = LocalDateTime.parse(input.getFechaHora(), FORMATTER);
        Horarios horario = Horarios.valueOf(input.getHorarios().toUpperCase());
        return registrarHorarioService.updateRegistrarHorario(id, fechaHora, horario, input.getVendedorId());
    }

    @MutationMapping
    public RegistrarHorario deleteRegistrarHorario(@Argument Long id) {
        return registrarHorarioService.deleteRegistrarHorario(id);
    }
}
